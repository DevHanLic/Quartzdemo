package amp.demo.service;

import com.alipay.api.AlipayApiException;

public interface AlipayAppTradeService {

    String AlipayAppPay() throws AlipayApiException;

    String AlipayAppRefund() throws AlipayApiException;

    String AlipayAppClose() throws AlipayApiException;
}
