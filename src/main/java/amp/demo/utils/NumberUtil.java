package amp.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author devzl[zliangchn@126.com]
 * @version V1.0
 * @apiNote 数字工具类
 * @date 2020/04/07 21:24 星期二
 */
public class NumberUtil  {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    /**
     * 正则表达式判断字符串是否为纯数字
     *
     * @param number 需判断的字符串
     *
     * @return 判断结果 true:纯数字,false:不是纯数字
     */
    public static boolean isNumberString(String number) {
        if (JudgeUtils.isBlank(number)) {
            return false;
        }
        Matcher matcher = NUMBER_PATTERN.matcher(number);
        return matcher.matches();
    }

}
