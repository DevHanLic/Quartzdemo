package amp.demo.controller;

import amp.demo.dto.ResultData;
import amp.demo.service.AlipayAppTradeService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@Slf4j
public class AlipayTradeAppController {

    @Resource
    private AlipayAppTradeService alipayAppTradeService;

    @Value("${alipay.public-key}")
    private String alipayPublicKey;

    @Value("${alipay.charset:UTF-8}")
    private String charset;

    @Value("${alipay.sign-type:RSA2}")
    private String signType;

    @PostConstruct
    public void init() {
        log.info("支付宝配置加载完成");
    }

    @PostMapping("/alipayApp/pay")
    public ResultData<String> appPay() throws AlipayApiException {
        log.info("发起支付宝支付");
        String payResponse = alipayAppTradeService.appPay();
        return ResultData.success(payResponse);
    }

    @PostMapping("/alipayApp/refund")
    public ResultData<String> refund() throws AlipayApiException {
        log.info("发起支付宝退款");
        String refundResponse = alipayAppTradeService.appRefund();
        return ResultData.success(refundResponse);
    }

    @PostMapping("/alipayApp/close")
    public ResultData<String> close() throws AlipayApiException {
        log.info("关闭支付宝订单");
        String closeResponse = alipayAppTradeService.appClose();
        return ResultData.success(closeResponse);
    }

    @PostMapping("/alipayApp/refund/query")
    public ResultData<String> refundQuery() throws AlipayApiException {
        log.info("查询支付宝退款状态");
        String queryResponse = alipayAppTradeService.refundQuery();
        return ResultData.success(queryResponse);
    }

    @PostMapping("/alipayApp/pay/query")
    public ResultData<String> payQuery() throws AlipayApiException {
        log.info("查询支付宝订单状态");
        String queryResponse = alipayAppTradeService.payQuery();
        return ResultData.success(queryResponse);
    }

    @PostMapping("/alipayApp/return/notify")
    public String handleReturnNotify(HttpServletRequest request) {
        log.info("收到支付宝同步通知");
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            log.info("同步通知参数: {}", params);

            if (!verifySign(params)) {
                log.error("支付宝同步通知签名验证失败");
                return "fail";
            }

            alipayAppTradeService.processReturnNotify(params);
            log.info("支付宝同步通知处理成功");
            return "success";
        } catch (Exception e) {
            log.error("处理支付宝同步通知异常", e);
            return "fail";
        }
    }

    @PostMapping("/alipayApp/async/notify")
    public String handleAsyncNotify(HttpServletRequest request) {
        log.info("收到支付宝异步通知");
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            log.debug("异步通知参数: {}", params);

            if (!verifySign(params)) {
                log.error("支付宝异步通知签名验证失败");
                return "fail";
            }

            alipayAppTradeService.processAsyncNotify(params);
            log.info("支付宝异步通知处理成功");
            return "success";
        } catch (Exception e) {
            log.error("处理支付宝异步通知异常", e);
            return "fail";
        }
    }

    @PostMapping("/alipayApp/refund/notify")
    public String handleRefundNotify(HttpServletRequest request) {
        log.info("收到支付宝退款冲退通知");
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            log.info("退款通知参数: {}", params);

            if (!verifySign(params)) {
                log.error("支付宝退款通知签名验证失败");
                return "fail";
            }

            boolean processResult = alipayAppTradeService.processRefundNotify(params);
            return processResult ? "success" : "fail";
        } catch (Exception e) {
            log.error("处理支付宝退款通知异常", e);
            return "fail";
        }
    }

    private boolean verifySign(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, signType);
        } catch (AlipayApiException e) {
            log.error("签名验证异常", e);
            return false;
        }
    }

    private Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();

        requestParams.forEach((name, values) -> {
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append(values[i]);
                if (i < values.length - 1) {
                    valueStr.append(",");
                }
            }
            params.put(name, valueStr.toString());
        });

        return params;
    }
}
