package amp.demo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AliRefundNotifyBizContent {

    @JSONField(name = "bank_ack_time")
    private String bankAckTime;

    @JSONField(name = "est_bank_receipt_time")
    private String estBankReceiptTime;

    @JSONField(name = "dback_status")
    private String dbackStatus;

    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @JSONField(name = "trade_no")
    private String tradeNo;

    @JSONField(name = "out_request_no")
    private String outRequestNo;

    @JSONField(name = "dback_amount")
    private String dbackAmount;

    /**
     * 冲退状态描述
     */
    public String getDbackStatusDesc() {
        switch (dbackStatus) {
            case "S":
                return "成功";
            case "F":
                return "失败";
            default:
                return "未知";
        }
    }
    /**
     * 获取冲退金额（BigDecimal格式）
     */
    public BigDecimal getDbackAmountDecimal() {
        try {
            return new BigDecimal(dbackAmount);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
