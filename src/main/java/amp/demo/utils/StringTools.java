package amp.demo.utils;



/**
 * @author dev_shouqiang
 * @version V1.0
 * @desc 字符串工具类
 * @date 2020-12-14 15:32:09
 */
public class StringTools {

    /**
     * 按字符截取字符串(主要针对中文)
     * @param str
     * @param begin
     * @param end
     * @return
     */
    public static String subString(String str, int begin, int end) {
        if (begin >= end) {
            return "";
        }
        String result = "";
        try {
            result = new String(str.getBytes("GB2312"), begin, end - begin);
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * 按字符截取字符串，指定编码格式
     * @param str
     * @param encodingFormat
     * @param begin
     * @param end
     * @return
     */
    public static String subStringByFormat(String str,String encodingFormat, int begin, int end) {
        if (begin >= end) {
            return "";
        }
        String result = "";
        try {
            result = new String(str.getBytes(encodingFormat), begin, end - begin);
        } catch (Exception e) {

        }
        return result;
    }
}
