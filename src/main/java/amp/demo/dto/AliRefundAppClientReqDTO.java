package amp.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author han_lic
 * @date 2025/11/21 10:16
 * @desc app退款接口 请求类
 */
@Data
public class AliRefundAppClientReqDTO {

    /**
     * 退款金额。 需要退款的金额，该金额不能大于订单金额，单位为元，支持两位小数
     * 必选
     */
    private String refundAmount;

    /**
     * 商户订单号。 订单支付时传入的商户订单号，商家自定义且保证商家系统中唯一。与支付宝交易号 trade_no 不能同时为空
     * 必选
     */
    private String outTradeNo;

    /**
     * 支付宝交易号。 和商户订单号 out_trade_no 不能同时为空，两者同时存在时，优先取值trade_no
     *
     */
    private String tradeNo;

    /**
     * 退款原因说明
     * 可选
     */
    private String refundReason;

    /**
     * 退款请求号
     * 可选
     */
    private String outRequestNo;

    /**
     * 退款包含的商品列表信息
     * 可选
     */
    private List<RefundGoodsDetailReqDTO> refundGoodsDetailReqDTOList;

    /**
     * 退分账明细信息
     * 可选
     */
    private List<OpenApiRoyaltyDetailInfoDTO> openApiRoyaltyDetailInfoDTOList;

    /**
     * 返回参数选项。 商户通过传递该参数来定制同步需要额外返回的信息字段，数组格式
     * 可选
     */
    private List<String> queryOptionsList;

    /**
     * 针对账期交易，在确认结算后退款的话，需要指定确认结算时的结算单号。
     * 可选
     */
    private String relatedSettleConfirmNo;

}
