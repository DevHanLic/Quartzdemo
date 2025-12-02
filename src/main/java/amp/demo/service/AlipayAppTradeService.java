package amp.demo.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface AlipayAppTradeService {

    String AlipayAppPay() throws AlipayApiException;

    String AlipayAppRefund() throws AlipayApiException;

    String AlipayAppClose() throws AlipayApiException;

    String refundQuery() throws AlipayApiException;

    String payQuery() throws AlipayApiException;

    boolean processRefundNotify(Map<String, String> params);

    void processReturnNotify(Map<String, String> params);
}
