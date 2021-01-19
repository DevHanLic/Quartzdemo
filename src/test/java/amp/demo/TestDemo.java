package amp.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
