package amp.demo.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author devzl[zliangchn@126.com]
 * @version V1.0
 * @apiNote 数字工具类
 * @date 2020/04/07 21:24 星期二
 */
public class NumberUtil {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");
    public static final String PAD_STR = "*";
    public static final String AT_STR = "@";
    public static final String EMPTY_STRING = "";
    public static final String EMPTY_STR = EMPTY_STRING;

    /**
     * 正则表达式判断字符串是否为纯数字
     *
     * @param number 需判断的字符串
     * @return 判断结果 true:纯数字,false:不是纯数字
     */
    public static boolean isNumberString(String number) {
        if (JudgeUtils.isBlank(number)) {
            return false;
        }
        Matcher matcher = NUMBER_PATTERN.matcher(number);
        return matcher.matches();
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138****1234>
     */
    public static String mobileNo(final String num) {
        if (StringUtils.isBlank(num)) {
            return EMPTY_STR;
        }
        return StringUtils.left(num, 3).concat(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num) - 3, PAD_STR));
    }

    /**
     * 中间脱敏，保留两端
     *
     * @param sensitiveStr
     * @return
     */
    public static String middle(final String sensitiveStr) {
        if (StringUtils.isBlank(sensitiveStr)) {
            return EMPTY_STR;
        }
        int length = sensitiveStr.length();
        if (length < 3) {
            return StringUtils.leftPad(EMPTY_STR, length, PAD_STR);
        }
        char firstChar = sensitiveStr.charAt(0);
        char lastChar = sensitiveStr.charAt(sensitiveStr.length() - 1);
        return StringUtils.rightPad(StringUtils.rightPad(String.valueOf(firstChar), length - 1, PAD_STR), length, lastChar);
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return EMPTY_STR;
        }
        return StringUtils.rightPad(StringUtils.left(fullName, 1), StringUtils.length(fullName), PAD_STR);
    }

    /**
     * 姓名脱敏支持生僻字
     * 张𪷿𪷿 --> **𪷿
     * @param input
     * @return
     */
    public static String userNameDesensitize(String input) {
        if (StringUtils.isBlank(input)) {
            return StringUtils.EMPTY;
        }
        // 取最后一个字符
        String lastOne = input.codePoints()
                .skip(Math.max(0, input.codePointCount(0, input.length()) - 1))
                .mapToObj(c -> new StringBuilder().appendCodePoint(c).toString())
                .findFirst()
                .orElse("");
        // 填充指定长度*号
        String desensitize = StringUtils.repeat("*", (int) input.codePoints().count() - 1);
        return desensitize + lastOne;
    }
}
