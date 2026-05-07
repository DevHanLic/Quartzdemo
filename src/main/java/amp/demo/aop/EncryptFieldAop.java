package amp.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Aspect
@Component
@Order(1)
public class EncryptFieldAop {
    private static final Logger logger = LoggerFactory.getLogger(EncryptFieldAop.class);

    @Value("${secretkey}")
    private String secretKey;

    @Around("@annotation(encryptMethod)")
    public Object around(ProceedingJoinPoint joinPoint, EncryptMethod encryptMethod) {
        logger.debug("开始执行加密字段切面");

        Object responseObj = null;
        try {
            Object requestObj = joinPoint.getArgs()[0];
            handleEncrypt(requestObj);
            responseObj = joinPoint.proceed();
            handleDecrypt(responseObj);
        } catch (Throwable throwable) {
            logger.error("加密字段处理失败", throwable);
            throw new RuntimeException("加密字段处理失败", throwable);
        }

        return responseObj;
    }

    private void handleEncrypt(Object requestObj) throws IllegalAccessException {
        if (Objects.isNull(requestObj)) {
            return;
        }

        Field[] fields = requestObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EncryptField.class)) {
                field.setAccessible(true);
                String plaintextValue = (String) field.get(requestObj);
                if (plaintextValue != null) {
                    String encryptValue = AseUtil.encrypt(plaintextValue, secretKey);
                    field.set(requestObj, encryptValue);
                    logger.debug("字段 {} 加密成功", field.getName());
                }
            }
        }
    }

    private Object handleDecrypt(Object responseObj) throws IllegalAccessException {
        if (Objects.isNull(responseObj)) {
            return null;
        }

        Field[] fields = responseObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EncryptField.class)) {
                field.setAccessible(true);
                String encryptValue = (String) field.get(responseObj);
                if (encryptValue != null) {
                    String plaintextValue = AseUtil.decrypt(encryptValue, secretKey);
                    field.set(responseObj, plaintextValue);
                    logger.debug("字段 {} 解密成功", field.getName());
                }
            }
        }
        return responseObj;
    }
}
