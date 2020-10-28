package amp.demo.aop;


/**
 * 测试TestService切面服务类
 *
 * @author: zetting
 * @date:2018/12/27
 */
public interface TestService {

    /**
     * 测试加密
     *
     * @param request
     * @return
     */
    TestResponse testEncrypt(TestRequest request);

    /**
     * 测试解密
     *
     * @param request
     * @return
     */
    TestResponse testDecrypt(TestRequest request);
}
