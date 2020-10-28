package amp.demo.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 测试服务实现类
 *
 * @author: zetting
 * @date:2018/12/27
 */
@Service
public class TestServiceImpl implements TestService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @EncryptMethod
    @Override
    public TestResponse testEncrypt(TestRequest request) {
        logger.info("testEncrypt request:{}", request.toString());
        return null;
    }

    @Override
    @EncryptMethod
    public TestResponse testDecrypt(TestRequest request) {
        logger.info("testDecrypt request:{}", request.toString());

        TestResponse response = new TestResponse();
        BeanUtils.copyProperties(request,response);
        return response;
    }
}

