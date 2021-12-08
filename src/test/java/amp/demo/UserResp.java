package amp.demo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author han_lic
 * @date 2021/7/16 16:00
 */
@Data
public class UserResp {
    private String bankUserName;

    private String password;

    private int flag;

    private BigDecimal merchantBonusAmount;

    private Long a;

    private String b;
}
