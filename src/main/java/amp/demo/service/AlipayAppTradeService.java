package amp.demo.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface AlipayAppTradeService {
    String appPay() throws AlipayApiException;

    String appRefund() throws AlipayApiException;

    String appClose() throws AlipayApiException;

    String refundQuery() throws AlipayApiException;

    String payQuery() throws AlipayApiException;

    boolean processRefundNotify(Map<String, String> params);

    void processReturnNotify(Map<String, String> params);

    void processAsyncNotify(Map<String, String> params);
}

