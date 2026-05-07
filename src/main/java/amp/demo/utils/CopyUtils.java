package amp.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Constructor;

public class CopyUtils {
    private static final Logger logger = LoggerFactory.getLogger(CopyUtils.class);

    public static String[] getNullPropertyNames(Object source) {
        if (source == null) {
            return new String[0];
        }

        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

    public static void copyProperties(Object src, Object target) {
        if (src == null || target == null) {
            logger.error("源对象或目标对象不能为空");
            throw new IllegalArgumentException("源对象和目标对象不能为空");
        }

        try {
            org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        } catch (BeansException e) {
            logger.error("属性拷贝失败", e);
            throw new RuntimeException("属性拷贝失败", e);
        }
    }

    public static <T> T convertObject(Object src, Class<T> targetClass) {
        if (src == null) {
            return null;
        }

        if (targetClass == null) {
            throw new IllegalArgumentException("目标类不能为空");
        }

        try {
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T target = constructor.newInstance();
            copyProperties(src, target);
            return target;
        } catch (Exception e) {
            logger.error("对象转换失败: source={}, target={}", src.getClass().getName(), targetClass.getName(), e);
            throw new RuntimeException("对象转换失败", e);
        }
    }
}
