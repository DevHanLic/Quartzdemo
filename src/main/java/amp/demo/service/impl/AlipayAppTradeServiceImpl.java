package amp.demo.service.impl;

import amp.demo.dto.AliRefundNotifyBizContent;
import amp.demo.dto.AliRefundNotifyRequest;
import amp.demo.service.AlipayAppTradeService;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AlipayAppTradeServiceImpl implements AlipayAppTradeService {

    @Value("${alipay.app-id}")

    private String appId;
    @Value("${alipay.private-key}")
    private String privateKey;
    @Value("${alipay.server-url}")
    private String serverUrl;

    @Override
    public String appPay() throws AlipayApiException {
        log.info("发起支付宝APP支付");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

            model.setOutTradeNo(generateOrderNo());
            model.setTotalAmount("1");
            model.setSubject("测试商品");
            model.setProductCode("QUICK_MSECURITY_PAY");
            model.setTimeExpire(getExpireTime());

            request.setBizModel(model);
            request.setApiVersion("1.0");

            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            
            if (response.isSuccess()) {
                String orderStr = response.getBody();
                log.info("支付宝APP支付请求成功: {}", orderStr);
                return orderStr;
            } else {
                log.error("支付宝APP支付请求失败: {}", response.getSubMsg());
                throw new AlipayApiException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("支付宝APP支付异常", e);
            throw e;
        }
    }

    @Override
    public String appRefund() throws AlipayApiException {
        log.info("发起支付宝退款");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();

            model.setOutTradeNo("20150320010101001");
            model.setTradeNo("2014112611001004680073956707");
            model.setRefundAmount("200.12");
            model.setRefundReason("正常退款");
            model.setOutRequestNo("HZ01RF001");

            request.setBizModel(model);

            AlipayTradeRefundResponse response = alipayClient.execute(request);
            
            if (response.isSuccess()) {
                log.info("支付宝退款成功");
                return response.getBody();
            } else {
                log.error("支付宝退款失败: {}", response.getSubMsg());
                throw new AlipayApiException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("支付宝退款异常", e);
            throw e;
        }
    }

    @Override
    public String appClose() throws AlipayApiException {
        log.info("关闭支付宝订单");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();

            model.setTradeNo("2013112611001004680073956707");
            model.setOutTradeNo("HZ0120131127001");
            model.setOperatorId("YX01");

            request.setBizModel(model);

            AlipayTradeCloseResponse response = alipayClient.execute(request);
            
            if (response.isSuccess()) {
                log.info("关闭支付宝订单成功");
                return response.getBody();
            } else {
                log.error("关闭支付宝订单失败: {}", response.getSubMsg());
                throw new AlipayApiException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("关闭支付宝订单异常", e);
            throw e;
        }
    }

    @Override
    public String refundQuery() throws AlipayApiException {
        log.info("查询支付宝退款状态");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
            AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();

            model.setTradeNo("2021081722001419121412730660");
            model.setOutTradeNo("2014112611001004680073956707");
            model.setOutRequestNo("HZ01RF001");

            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
            
            if (response.isSuccess()) {
                log.info("查询支付宝退款状态成功");
                return response.getBody();
            } else {
                log.error("查询支付宝退款状态失败: {}", response.getSubMsg());
                throw new AlipayApiException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("查询支付宝退款状态异常", e);
            throw e;
        }
    }

    @Override
    public String payQuery() throws AlipayApiException {
        log.info("查询支付宝订单状态");
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();

            model.setOutTradeNo("20150320010101001");
            model.setTradeNo("2014112611001004680073956707");

            AlipayTradeQueryResponse response = alipayClient.execute(request);
            
            if (response.isSuccess()) {
                log.info("查询支付宝订单状态成功");
                return response.getBody();
            } else {
                log.error("查询支付宝订单状态失败: {}", response.getSubMsg());
                throw new AlipayApiException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("查询支付宝订单状态异常", e);
            throw e;
        }
    }

    @Override
    public boolean processRefundNotify(Map<String, String> params) {
        log.info("处理支付宝退款通知");
        try {
            AliRefundNotifyRequest notifyRequest = parseNotifyRequest(params);
            validateBasicParams(notifyRequest);
            AliRefundNotifyBizContent bizContent = parseBizContent(notifyRequest.getBizContent());
            
            if (bizContent == null) {
                log.error("业务内容解析失败");
                return false;
            }
            
            return processRefundNotifyBusiness(bizContent);
        } catch (Exception e) {
            log.error("处理支付宝退款通知失败", e);
            return false;
        }
    }

    @Override
    public void processReturnNotify(Map<String, String> params) {
        log.info("处理支付宝同步通知");
        try {
            String tradeStatus = params.get("trade_status");
            String outTradeNo = params.get("out_trade_no");
            String tradeNo = params.get("trade_no");
            String totalAmount = params.get("total_amount");
            String appId = params.get("app_id");

            log.info("交易状态: {}, 商户订单号: {}, 支付宝交易号: {}, 金额: {}",
                    tradeStatus, outTradeNo, tradeNo, totalAmount);
            
        } catch (Exception e) {
            log.error("处理支付宝同步通知失败", e);
        }
    }

    @Override
    public void processAsyncNotify(Map<String, String> params) {
        log.info("处理支付宝异步通知");
        try {
            processReturnNotify(params);
        } catch (Exception e) {
            log.error("处理支付宝异步通知失败", e);
        }
    }

    private boolean processRefundNotifyBusiness(AliRefundNotifyBizContent bizContent) {
        if (StringUtils.isBlank(bizContent.getOutRequestNo())) {
            log.error("退款请求号不能为空");
            return false;
        }

        log.info("退款业务处理成功，退款请求号: {}", bizContent.getOutRequestNo());
        return true;
    }

    private void validateBasicParams(AliRefundNotifyRequest notifyRequest) {
        if (StringUtils.isBlank(notifyRequest.getAppId())) {
            throw new IllegalArgumentException("app_id不能为空");
        }

        if (!appId.equals(notifyRequest.getAppId())) {
            throw new IllegalArgumentException("app_id不匹配");
        }

        if (StringUtils.isBlank(notifyRequest.getMsgMethod())) {
            throw new IllegalArgumentException("msg_method不能为空");
        }

        if (!"alipay.trade.refund.depositback.completed".equals(notifyRequest.getMsgMethod())) {
            throw new IllegalArgumentException("msg_method不正确");
        }

        if (StringUtils.isBlank(notifyRequest.getBizContent())) {
            throw new IllegalArgumentException("biz_content不能为空");
        }
    }

    private AlipayConfig getAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(serverUrl);
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuMQ3i0zun3eVNsAB429cuQLCkRgXuC31Qcke3gvn7tEcC+DJNmZLC6370Zc7x7YVMa2l/YcdBKPIWR9VK0fN1fzucLGDca8LO7WVUcoDymDzOHhpiGWQHqGndY0Iv0AkzI/UYNZ9XRKojCHPCPZxdBbLdAnYp/r7x3ImOahMYPUvpv8MUcrCvP0No2E6c3L32pd2n/5YjZP3BP96zVJ+sNMrPKstutAq+Y2Bs3/QWED8s8XZ70/hAU/Z+GuKSFqmHr99DT/lR3sgNzDbzrMJqbjixqW70TZtPdhWDCZJ36fckRCKu9kPRZ9PHi/kd6cWfq2y3XcHZ+GXDIwBzYwxCwIDAQAB");
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }

    private String generateOrderNo() {
        return "ORDER" + System.currentTimeMillis();
    }

    private String getExpireTime() {
        return "2030-12-31 23:59:59";
    }

    private AliRefundNotifyRequest parseNotifyRequest(Map<String, String> params) {
        AliRefundNotifyRequest notifyRequest = new AliRefundNotifyRequest();

        notifyRequest.setCharset(getParameter(params, "charset"));
        notifyRequest.setBizContent(getParameter(params, "biz_content"));
        notifyRequest.setUtcTimestamp(getParameter(params, "utc_timestamp"));
        notifyRequest.setSign(getParameter(params, "sign"));
        notifyRequest.setAppId(getParameter(params, "app_id"));
        notifyRequest.setVersion(getParameter(params, "version"));
        notifyRequest.setSignType(getParameter(params, "sign_type"));
        notifyRequest.setNotifyId(getParameter(params, "notify_id"));
        notifyRequest.setMsgMethod(getParameter(params, "msg_method"));

        log.info("解析支付宝冲退通知请求: {}", JSON.toJSONString(notifyRequest));
        return notifyRequest;
    }

    private String getParameter(Map<String, String> params, String paramName) {
        String value = params.get(paramName);
        return value != null ? value.trim() : null;
    }

    private AliRefundNotifyBizContent parseBizContent(String bizContent) {
        try {
            AliRefundNotifyBizContent content = JSON.parseObject(bizContent, AliRefundNotifyBizContent.class);
            log.info("解析业务内容成功: {}", JSON.toJSONString(content));
            return content;
        } catch (Exception e) {
            log.error("解析业务内容失败: {}", bizContent, e);
            return null;
        }
    }
}
