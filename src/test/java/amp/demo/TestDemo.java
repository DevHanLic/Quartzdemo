package amp.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author han_lic
 * @date 2020/12/25 9:43
 */
public class TestDemo {

    public static void main(String[] args) {
//        String str = "B202012220248|2020122906000000001082671|CNY11.11|0110|00|C1010611003612|C1010211000112|00|1901007019200173392|2427611923997824|130001|9999||00#CNY1.0000#1-01#CNY0.0200#1|";
//        String[] s = str.split("\\|");
//        System.out.println(s.length);
//        System.out.println(s[1].substring(8, 25));
        String names = "8G+256G";
        List<String> nameList = Stream.of(names.split(",")).collect(Collectors.toList());
        System.out.println(nameList.size());
        String str1 = "B202012220248|06000000001082671|CNY11.11||";
        char FILE_PARTING = '|';
        System.out.println(characterComparison(str1,FILE_PARTING));
        String[] ss = str1.split("\\|");
        System.out.println(ss.length);
        for (String s1: ss) {
            System.out.println(s1);
        }
        // 定义俩个字符串
        String shortStr = "CNY";
        String longStr = "00#CNY1.0000#1-01#CNY0.0200#1";
        // 调用searchCount方法
        int count = searchCount(shortStr, longStr);
        // 输出字符串出现的次数
        System.out.println("java出现的次数是：" + count);

        int[] arr = {1,2,3};
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(list);

        String str2 = "1|1^18817146643|";
        String appUser = str2.split("\\|")[1].substring(2);
        System.out.println(appUser);
        String fileName = ".JPG";
        System.out.println("------------"+fileName.equalsIgnoreCase(".jpg"));

        String a = "2018-04-03 21:59:09|wx2b029c08a6232582|1900008721|203682850||4200000100201804031566594966|204811300000006414|oBmItsw-0H0Y7_N8BUYjImdbyhr4|NATIVE|SUCCESS|CFT|CNY|0.01|0.00|0|0|0.00|0.00|||测试body|bank_mch_name=大时代科技有限公司&bank_mch_id=990581053996001&|0.05|0.20%|0.01|0.00|";
        System.out.println("1111"+characterComparison(a,FILE_PARTING));
    }

    // 定义searchCount方法，来返回字符串出现的个数
    public static int searchCount(String shortStr, String longStr) {
        // 定义一个count来存放字符串出现的次数
        int count = 0;
        // 调用String类的indexOf(String str)方法，返回第一个相同字符串出现的下标
        while (longStr.contains(shortStr)) {
            // 如果存在相同字符串则次数加1
            count++;
            // 调用String类的substring(int beginIndex)方法，获得第一个相同字符出现后的字符串
            longStr = longStr.substring(longStr.indexOf(shortStr) + shortStr.length());
        }
        // 返回次数
        return count;
    }

    private static int characterComparison(String str, char c) {
        int count = 0;
        char[] charArray = str.toCharArray();
        for (char item : charArray) {
            if (item == c) {
                count++;
            }
        }
        return count;
    }
}
