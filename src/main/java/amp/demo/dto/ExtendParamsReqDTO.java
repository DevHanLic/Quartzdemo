package amp.demo.dto;

import lombok.Data;

/**
 * @author han_lic
 * @date 2025/11/21 10:16
 * @desc app支付接口2.0 设置业务扩展参数
 */
@Data
public class ExtendParamsReqDTO {

    /**
     * 系统商编号
     * 可选
     */
    private String sysServiceProviderId;

    /**
     * 使用花呗分期要进行的分期数
     * 可选
     */
    private String hbFqNum;

    /**
     * 使用花呗分期需要卖家承担的手续费比例的百分值，传入100代表100%
     * 可选
     */
    private String hbFqSellerPercent;

    /**
     * 行业数据回流信息, 详见：地铁支付接口参数补充说明
     * 可选
     */
    private String industryRefluxInfo;

    /**
     * 卡类型
     * 可选
     */
    private String cardType;

    /**
     * 卡类型
     * 可选
     */
    private String royaltyFreeze;

}
