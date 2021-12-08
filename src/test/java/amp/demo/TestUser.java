package amp.demo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author han_lic
 * @date 2021/4/8 18:51
 */
@Data
public class TestUser {
    private Long a;

    private String b;

    private BigDecimal merchantBonusAmount;

    private String couponAmt;

    private String mobileNo;

    private User user;
}
