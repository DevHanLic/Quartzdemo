package amp.demo.controller;

import amp.demo.service.AlipayAppTradeService;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HLC
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class AlipayTradeAppController {

    @Resource
    AlipayAppTradeService alipayAppTradeService;

    @PostMapping("/alipayApp/pay")
    String appPay() throws AlipayApiException {
        String alipayAppPay = alipayAppTradeService.AlipayAppPay();
        return alipayAppPay;
    }

    @PostMapping("/alipayApp/refund")
    String refund() throws AlipayApiException {
        String alipayAppPay = alipayAppTradeService.AlipayAppRefund();
        return alipayAppPay;
    }

    @PostMapping("/alipayApp/close")
    String close() throws AlipayApiException {
        String alipayAppPay = alipayAppTradeService.AlipayAppClose();
        return alipayAppPay;
    }

    @PostMapping("/alipayApp/refund/query")
    String refundQuery() throws AlipayApiException {
        String alipayAppPay = alipayAppTradeService.refundQuery();
        return alipayAppPay;
    }

    @PostMapping("/alipayApp/pay/query")
    String payQuery() throws AlipayApiException {
        String alipayAppPay = alipayAppTradeService.payQuery();
        return alipayAppPay;
    }
    /**
     * 同步通知接收接口
     */
    @PostMapping("/alipayApp/return/notify")
    public String handleReturnNotify(HttpServletRequest request) {
        log.info("收到支付宝同步通知");
        try {
            // 将请求参数转换为Map
            Map<String, String> params = convertRequestParamsToMap(request);
            log.info("异步通知参数: {}", params);

            // 验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuMQ3i0zun3eVNsAB429cuQLCkRgXuC31Qcke3gvn7tEcC+DJNmZLC6370Zc7x7YVMa2l/YcdBKPIWR9VK0fN1fzucLGDca8LO7WVUcoDymDzOHhpiGWQHqGndY0Iv0AkzI/UYNZ9XRKojCHPCPZxdBbLdAnYp/r7x3ImOahMYPUvpv8MUcrCvP0No2E6c3L32pd2n/5YjZP3BP96zVJ+sNMrPKstutAq+Y2Bs3/QWED8s8XZ70/hAU/Z+GuKSFqmHr99DT/lR3sgNzDbzrMJqbjixqW70TZtPdhWDCZJ36fckRCKu9kPRZ9PHi/kd6cWfq2y3XcHZ+GXDIwBzYwxCwIDAQAB", // 支付宝公钥
                    "UTF-8",
                    "RSA2"
            );
            if (!signVerified) {
                log.error("支付宝同步通知签名验证失败");
                return "fail";
            }
            alipayAppTradeService.processReturnNotify(params);
        } catch (Exception e) {
            log.error("处理支付宝同步通知异常", e);
            return "fail";
        }
        return "success";
    }

    /**
     * 异步通知接收接口
     */
    @PostMapping("/alipayApp/async/notify")
    public String handleAsyncNotify(HttpServletRequest request) {

        return "success";
    }


    /**
     * 退款冲退完成通知接收接口
     */
    @PostMapping("/alipayApp/refund/notify")
    public String handleRefundNotify(HttpServletRequest request) {
        try {
            // 将请求参数转换为Map
            Map<String, String> params = convertRequestParamsToMap(request);

            log.info("收到支付宝退款冲退通知: {}", JSONObject.toJSONString(params));

            // 验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuMQ3i0zun3eVNsAB429cuQLCkRgXuC31Qcke3gvn7tEcC+DJNmZLC6370Zc7x7YVMa2l/YcdBKPIWR9VK0fN1fzucLGDca8LO7WVUcoDymDzOHhpiGWQHqGndY0Iv0AkzI/UYNZ9XRKojCHPCPZxdBbLdAnYp/r7x3ImOahMYPUvpv8MUcrCvP0No2E6c3L32pd2n/5YjZP3BP96zVJ+sNMrPKstutAq+Y2Bs3/QWED8s8XZ70/hAU/Z+GuKSFqmHr99DT/lR3sgNzDbzrMJqbjixqW70TZtPdhWDCZJ36fckRCKu9kPRZ9PHi/kd6cWfq2y3XcHZ+GXDIwBzYwxCwIDAQAB", // 支付宝公钥
                    "UTF-8",
                    "RSA2"
            );

            if (!signVerified) {
                log.error("支付宝退款通知签名验证失败");
                return "fail";
            }

            // 处理业务逻辑
            boolean processResult = alipayAppTradeService.processRefundNotify(params);

            return processResult ? "success" : "fail";

        } catch (Exception e) {
            log.error("处理支付宝退款通知异常", e);
            return "fail";
        }

    }






    /**
     * 将HttpServletRequest参数转换为Map
     */
    private Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();

        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

}
