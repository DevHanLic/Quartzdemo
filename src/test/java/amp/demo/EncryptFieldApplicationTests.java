package amp.demo;

import amp.demo.aop.TestRequest;
import amp.demo.aop.TestResponse;
import amp.demo.aop.TestService;
import amp.demo.utils.JudgeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.zetting.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuartzdemoApplication.class)
//@SpringBootApplication(exclude = SecureFieldApplication.class)
public class EncryptFieldApplicationTests {

    private static final Log log = LogFactory.getLog(EncryptFieldApplicationTests.class);

    @Autowired
    private TestService testService;

    /**
     * 测试
     *
     * @throws Exception
     */
    @Test
    public void testDecryptAndEncrypt() throws Exception {
        String bankCardNo = "6222024616723052519";
        String idCard = "420101196207212033";

        TestRequest request = new TestRequest();
        request.setBankCardNo(bankCardNo);
        request.setIdCard(idCard);
        request.setName("张三");
        request.setSex("男");
        TestResponse response = testService.testDecrypt(request);

        Assert.isTrue(response.getBankCardNo().equals(bankCardNo), "银行卡号不一致");
        Assert.isTrue(response.getIdCard().equals(idCard), "身份证号不一致");

        log.error("response :" + response.toString());

    }

    /**
     * 测试断言
     */
    @Test
    public void testAssertion() {
        String str = "1";
        Assertions.assertEquals("1", str, "此方法可能出现异常");


        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 如果用户没有输入结束日期,默认当天日期
        String current = format.format(calendar.getTime());
        System.out.println("===============current:" + current);
        //获取当前月第一天：
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(calendar.getTime());
        System.out.println("===============first:" + first);

    }

}
