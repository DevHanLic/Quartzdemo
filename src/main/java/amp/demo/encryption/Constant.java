package amp.demo.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量
 *
 * @author yangj
 */
@Component
public class Constant {
    public static boolean IS_HIHSM=true;

    @Value("${ytx.encryptor.encEnable:true}")
    public void setEncEnable(boolean encEnable){
        Constant.IS_HIHSM = encEnable;
    }

}
