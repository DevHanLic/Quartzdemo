package amp.demo;

import lombok.Data;

/**
 * @ClassName AutoGetMobileCache
 * @Date 2021/4/15 14:43
 * @Author zou_hl
 * @Description TODO
 */

@Data
public class AutoGetMobileCacheBO {

    /**
     * 手机号
     */
    private String mobileNo;
    /**
     * 令牌号
     */
    private String sessionId ;
    /**
     * 自动取号标识
     */
    private boolean autoGetMobileNoFlag ;

}
