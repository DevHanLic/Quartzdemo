package amp.demo.utils;

import amp.demo.test.CommonUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author han_lic
 * @date 2020/12/3 18:07
 */
public class JudgeUtils extends CommonUtils {
    /**
     * 判断消息是否成功
     * @param msgCd
     * @return
     */
    public static boolean isSuccess(String msgCd) throws Exception {
        if(StringUtils.isEmpty(msgCd)) {
            throw new Exception("MsgCd can't be empty.");
        }
        return StringUtils.equals(msgCd.substring(msgCd.length() - 5),    "00000");
    }


}
