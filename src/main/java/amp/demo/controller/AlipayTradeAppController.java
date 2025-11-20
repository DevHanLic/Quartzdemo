package amp.demo.controller;

import amp.demo.service.AlipayAppTradeService;
import com.alipay.api.AlipayApiException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HLC
 */
@RestController
@RequestMapping("/v1")
public class AlipayTradeAppController {

    @Resource
    AlipayAppTradeService alipayAppTradeService;

    @PostMapping("/alipayApp/pay")
    String appPay() throws AlipayApiException {
        String alipayAppPay = alipayAppTradeService.AlipayAppPay();
        return alipayAppPay;
    }

}
