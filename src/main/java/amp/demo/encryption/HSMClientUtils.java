package amp.demo.encryption;

import amp.demo.utils.JudgeUtils;
import com.hisun.client.HSMClientUtil;
import com.hisun.cmd.Constants;
import com.hisun.cmd.LogUtil;
import com.hisun.domain.Decrypt33;
import com.hisun.domain.U1;

/**
 * 调加密机业务处理
 *
 */
public class HSMClientUtils {

	/**
	 * 非数值型加解密分隔符
	 */
	private static final String DE_CHARACTER = "65538";

	/**
	 * 离散数据
	 */
	private static String disdata = "00215000999900010000000033333333";


	/**
	 * 非纯数字转换为ASCII码加密数据
	 * @param data   需加密数据
	 * @return  加密后数据  返回空值表示失败
	 */
	public static String encrtyptASCII(String data){
		String endData = data;
		if (Constant.IS_HIHSM) {
			if (JudgeUtils.isNull(data)) return "";
			char[] chars = data.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (char c : chars) {
				String dateAscii = ((int) c) + "";
				sb.append(dateAscii);
				sb.append(DE_CHARACTER);
			}
			endData = encrypt(sb.toString());
			com.hisun.cmd.LogUtil.HSMInfo("encrtyptASCII|【加密前未转码数据】 " + data + "【转成ASCII码数据】" + sb.toString() + " 【加密后数据】" + endData);
		}
		return endData;
	}

	/**
	 * ASCII码转换成非纯数字的解密数据
	 * @param data   需解密数据
	 * @return  解密后数据  返回空值表示失败
	 */
	public static String decryptASCII(String data){
		String endData = data;
		if (Constant.IS_HIHSM) {
			if (JudgeUtils.isNull(data)) return "";
			String dataAscii = decrypt(data);
			int last = dataAscii.lastIndexOf(DE_CHARACTER);
			String dataAscii1 = dataAscii;
			if (last != -1) {
				dataAscii1 = dataAscii.substring(0, last);
			}
			String[] chars = dataAscii1.split(DE_CHARACTER);
			StringBuffer sb = new StringBuffer();
			for (String str : chars) {
				sb.append((char) Integer.parseInt(str));
			}
			com.hisun.cmd.LogUtil.HSMInfo("decryptASCII|【解密前数据】 " + data + "【解密后数据】" + dataAscii + " 【ASCII转换数据】" + sb.toString());
			endData = sb.toString();
		}
		return endData;
	}

	/**
	 * (纯数字)加密数据
	 * @param data   需加密数据
	 * @return  加密后数据  返回空值表示失败
	 */
	public static String encrypt(String data){
		if(JudgeUtils.isNull(data)) return "";
		U1 u1 = new U1();
		u1.setDisdata(disdata);
		u1.setData(data);
		String encryptData = HSMClientUtil.encrypt(u1);
		com.hisun.cmd.LogUtil.HSMInfo("encrypt|【加密前数据】 " + data + "【加密后数据】" + encryptData);
		if(encryptData.equals(Constants.TIMEOUT_CODE)) {
			LogUtil.HSMInfo("【encrypt】调加密机超时，请联系管理员！！！！！！！");
			return "";
		}
		return encryptData;
	}

	/**
	 * (纯数字)解密数据
	 * @param data   需解密数据
	 * @param len    原明文数据长度
	 * @return  解密后数据  返回空值表示失败
	 */
	public static String decrypt(String data, int len){
		String decryptData = decrypt(data);
		return JudgeUtils.isNotNull(decryptData) ? decryptData.substring(0, len) : decryptData;
	}

	/**
	 * (纯数字)解密数据,用于ASCII解密
	 * @param data   需解密数据
	 * @return  解密后数据  返回空值表示失败
	 */
	public static String decrypt(String data){
		if(JudgeUtils.isNull(data)) return "";
		U1 u1 = new U1();
		u1.setDisdata(disdata);
		u1.setData(data);
		String decryptData = HSMClientUtil.decrypt(u1);
		com.hisun.cmd.LogUtil.HSMInfo("encrypt|【解密前数据】 " + data + "【解密后数据】" + decryptData);
		if(decryptData.equals(Constants.TIMEOUT_CODE)) {
			LogUtil.HSMInfo("【decrypt】调加密机超时，请联系管理员！！！！！！！");
			return "";
		}
		return decryptData;
	}

	/**
	 * (纯数字)私钥解密33指令（插件采用公钥进行加密）
	 * @param data    需解密数据
	 * @param len     原明文数据长度
	 * @return  解密后数据 返回空值表示失败
	 */
	public static String decrypt33(String data, int len){
		if(JudgeUtils.isNull(data)) return "";
		Decrypt33 decrypt33 = new Decrypt33();
		decrypt33.setData(data.trim());
		String decrypt33Data = HSMClientUtil.decrypt33(decrypt33);
		if(decrypt33Data.equals(Constants.TIMEOUT_CODE)) {
			LogUtil.HSMInfo("【decrypt33】调加密机超时，请联系管理员！！！！！！！");
			return "";
		}
		return JudgeUtils.isNotNull(decrypt33Data) ? decrypt33Data.substring(0, len) : decrypt33Data;
	}

}
