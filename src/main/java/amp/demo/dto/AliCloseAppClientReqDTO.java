package amp.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author han_lic
 * @date 2025/11/21 10:16
 * @desc app接口 交易关闭请求类
 */
@Data
public class AliCloseAppClientReqDTO {

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
     * 商家操作员编号 id
     * 可选
     */
    private String operatorId;

}
