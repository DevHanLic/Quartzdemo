package amp.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class QuartzdemoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("333333333333="+isNumber("12.301"));
        System.out.println("222222222222="+isNumber("12.3"));
        System.out.println("333333333="+isNumber("1288888888888888888888"));
        System.out.println("333333333="+isNumber("012222"));
    }
    private static boolean isNumber(String str) {
        String regex = "^([0-9]+(.[0-9]{1,2})?)|(-[0-9]+(.[0-9]{1,2})?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        // match.find()
        return match.matches();
    }


}
