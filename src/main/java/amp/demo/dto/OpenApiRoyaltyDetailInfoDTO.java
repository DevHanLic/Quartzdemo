package amp.demo.dto;

import lombok.Data;

/**
 * @author han_lic
 * @date 2025/11/21 10:16
 * @desc app退款 退分账明细信息
 */
@Data
public class OpenApiRoyaltyDetailInfoDTO {

    /**
     * 分账类型
     * 可选
     */
    private String royaltyType;

    /**
     * 支出方账户
     * 可选
     */
    private String transOut;

    /**
     * 支出方账户类型
     * 可选
     */
    private String transOutType;

    /**
     * 收入方账户类型
     * 可选
     */
    private String transInType;

    /**
     * 收入方账户
     * 可选
     */
    private String transIn;

    /**
     * 分账的金额，单位为元
     * 可选
     */
    private String amount;

    /**
     * 分账描述
     * 可选
     */
    private String desc;

    /**
     * 可选值
     * 可选
     */
    private String royaltyScene;

    /**
     * 分账收款方姓名
     * 可选
     */
    private String transInName;

}
