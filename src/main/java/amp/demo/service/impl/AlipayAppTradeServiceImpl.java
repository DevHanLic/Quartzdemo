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
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AlipayAppTradeServiceImpl implements AlipayAppTradeService {


    @Override
    public String AlipayAppPay() throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        /**业务请求参数**/
        // 设置商户订单号
        model.setOutTradeNo("daniel88AAAA000032333389");
        // 设置订单总金额
        model.setTotalAmount("1");
        // 设置订单标题
        model.setSubject("测试商品");
        // 设置产品码
        model.setProductCode("QUICK_MSECURITY_PAY");
        // 设置订单绝对超时时间
        model.setTimeExpire("2016-12-31 10:05:00");

        /**公共请求参数**/
        request.setBizModel(model);
        request.setApiVersion("1.0");
        request.setNotifyUrl("");
        request.setReturnUrl("");
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        String orderStr = response.getBody();
        System.out.println(orderStr);
        String convertToHtml = convertToHtml(orderStr);
        System.out.println(convertToHtml);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
        return convertToHtml;
    }

    @Override
    public String AlipayAppRefund() throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();

        // 设置商户订单号
        model.setOutTradeNo("20150320010101001");

        // 设置支付宝交易号
        model.setTradeNo("2014112611001004680073956707");

        // 设置退款金额
        model.setRefundAmount("200.12");

        // 设置退款原因说明
        model.setRefundReason("正常退款");

        // 设置退款请求号
        model.setOutRequestNo("HZ01RF001");

        // 设置退款包含的商品列表信息
        List<RefundGoodsDetail> refundGoodsDetail = new ArrayList<RefundGoodsDetail>();
        RefundGoodsDetail refundGoodsDetail0 = new RefundGoodsDetail();
        refundGoodsDetail0.setOutSkuId("outSku_01");
        refundGoodsDetail0.setOutItemId("outItem_01");
        refundGoodsDetail0.setGoodsId("apple-01");
        refundGoodsDetail0.setRefundAmount("19.50");
        List<String> outCertificateNoList = new ArrayList<String>();
        outCertificateNoList.add("202407013232143241231243243423");
        refundGoodsDetail0.setOutCertificateNoList(outCertificateNoList);
        refundGoodsDetail.add(refundGoodsDetail0);
        model.setRefundGoodsDetail(refundGoodsDetail);

        // 设置退分账明细信息
        List<OpenApiRoyaltyDetailInfoPojo> refundRoyaltyParameters = new ArrayList<OpenApiRoyaltyDetailInfoPojo>();
        OpenApiRoyaltyDetailInfoPojo refundRoyaltyParameters0 = new OpenApiRoyaltyDetailInfoPojo();
        refundRoyaltyParameters0.setAmount("0.1");
        refundRoyaltyParameters0.setTransIn("2088101126708402");
        refundRoyaltyParameters0.setRoyaltyType("transfer");
        refundRoyaltyParameters0.setTransOut("2088101126765726");
        refundRoyaltyParameters0.setTransOutType("userId");
        refundRoyaltyParameters0.setRoyaltyScene("达人佣金");
        refundRoyaltyParameters0.setTransInType("userId");
        refundRoyaltyParameters0.setTransInName("张三");
        refundRoyaltyParameters0.setDesc("分账给2088101126708402");
        refundRoyaltyParameters.add(refundRoyaltyParameters0);
        model.setRefundRoyaltyParameters(refundRoyaltyParameters);

        // 设置查询选项
        List<String> queryOptions = new ArrayList<String>();
        queryOptions.add("refund_detail_item_list");
        model.setQueryOptions(queryOptions);

        // 设置针对账期交易
        model.setRelatedSettleConfirmNo("2024041122001495000530302869");
        request.setNotifyUrl("");
        request.setBizModel(model);
        // 第三方代调用模式下请设置app_auth_token
        // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

        AlipayTradeRefundResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
        return "0";
    }

    @Override
    public String AlipayAppClose() throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();

        // 设置该交易在支付宝系统中的交易流水号
        model.setTradeNo("2013112611001004680073956707");

        // 设置订单支付时传入的商户订单号
        model.setOutTradeNo("HZ0120131127001");

        // 设置商家操作员编号 id
        model.setOperatorId("YX01");

        request.setBizModel(model);
        // 第三方代调用模式下请设置app_auth_token
        // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

        AlipayTradeCloseResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
        return "0";
    }

    @Override
    public String refundQuery() throws AlipayApiException {

        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();

        // 设置支付宝交易号
        model.setTradeNo("2021081722001419121412730660");

        // 设置商户订单号
        model.setOutTradeNo("2014112611001004680073956707");

        // 设置退款请求号
        model.setOutRequestNo("HZ01RF001");


        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
        return "0";
    }

    @Override
    public String payQuery() throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();

        // 设置订单支付时传入的商户订单号
        model.setOutTradeNo("20150320010101001");

        // 设置支付宝交易号
        model.setTradeNo("2014112611001004680073956707");

        // 设置查询选项
        List<String> queryOptions = new ArrayList<String>();
        queryOptions.add("trade_settle_info");
        model.setQueryOptions(queryOptions);

        request.setBizModel(model);
        // 第三方代调用模式下请设置app_auth_token
        // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
        return "0";
    }

    @Override
    public boolean processRefundNotify(Map<String, String> params) {
        // 1. 解析请求参数
        AliRefundNotifyRequest notifyRequest = parseNotifyRequest(params);
        // 2. 验证基本参数
        validateBasicParams(notifyRequest);
        // 3. 解析业务内容
        AliRefundNotifyBizContent bizContent = parseBizContent(notifyRequest.getBizContent());
        if (bizContent == null) {
            log.info("BIZ_CONTENT_PARSE_ERROR", "业务内容解析失败");
            return false;
        }
        // 4. 处理业务逻辑
        return processRefundNotifyBusiness(bizContent);
    }

    @Override
    public void processReturnNotify(Map<String, String> params) {
        // 获取通知参数
        String tradeStatus = params.get("trade_status");
        String outTradeNo = params.get("out_trade_no");
        String tradeNo = params.get("trade_no");
        String totalAmount = params.get("total_amount");
        String appId = params.get("app_id");

        log.info("交易状态: {}, 商户订单号: {}, 支付宝交易号: {}",
                tradeStatus, outTradeNo, tradeNo);
    }

    private boolean processRefundNotifyBusiness(AliRefundNotifyBizContent bizContent) {
        if (StringUtils.isBlank(bizContent.getOutRequestNo())) {
            log.info("APP_ID_EMPTY", "app_id不能为空");
            throw new RuntimeException();
        }

        return true;
    }

    private void validateBasicParams(AliRefundNotifyRequest notifyRequest) {
        if (StringUtils.isBlank(notifyRequest.getAppId())) {
            log.info("APP_ID_EMPTY", "app_id不能为空");
            throw new RuntimeException();
        }

        if (!"9021000157681961".equals(notifyRequest.getAppId())) {
            log.info("APP_ID_MISMATCH", "app_id不匹配");
            throw new RuntimeException();
        }

        if (StringUtils.isBlank(notifyRequest.getMsgMethod())) {
            log.info("MSG_METHOD_EMPTY", "msg_method不能为空");
            throw new RuntimeException();
        }

        if (!"alipay.trade.refund.depositback.completed".equals(notifyRequest.getMsgMethod())) {
            log.info("MSG_METHOD_INVALID", "msg_method不正确");
            throw new RuntimeException();
        }

        if (StringUtils.isBlank(notifyRequest.getBizContent())) {
            log.info("BIZ_CONTENT_EMPTY", "biz_content不能为空");
            throw new RuntimeException();
        }
    }

    private static AlipayConfig getAlipayConfig() {
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJsOwAPZwn0/2hU+pcJO/OpXoBPEi8aFiX8ZHYK5v4BUnt/f4Y7iO14WAwxUbpv5ljJanBF+G6pXVuHvSmfKUPJal/coKMMFO21/leOZvL+y2NRl+mDiff8e1h3+KYU68KmrIczZoenz1gCDHOQ7P5b+J9+m/KdqcgbN5/VOwMe4PVIiUfmiCkKpyo9Q5Q+MyZ7oiR/1MYy/hlJV5vpgUSJT8BKmP02BroRcelQeSwnf4c/9h4XhX8wKnBbHUzW8u7L05KN1Iy5KPTpBU/VaH/Ia48ittGH4Lc+Vl+SQ9cTvm5mQePpYPypchQcLnDp3TKISjdDoIiX+iNpq2e5isDAgMBAAECggEAT6mR0U3T8+MdweIIHMmuCPQKU36qYn9IiphUFiqc8XE15cDm60uUU52ShoUM0x5sQ0W+Ih6MFe22tECqsUw6Pzo+B6ObzjoDpSztdMuFzQ2EvFDz8+IKxfF1swPSWOlJw6ve0vVNbtwYqV/0WO8oyG8oLlTfqtbdONBkJm1eqJVHWy9ura/nFWaLfumO8eQK57wumy7hW7XKz47ebMU+U2jgJnV3AuRQGluRIQRWL9gRqLvq1UFEaUgERKpo/ViOm8iEqMRN8z2JpBojCF+116enO8OhxJWlesQmhRUH/RMK5wQKEZ7+ysD3ELJU7NIfHzfOVPz5YVj8vZLoYfGDcQKBgQD6M5zSbb9aY7lrg2ReSZPNNw8guevcwlnxojGboG8nnFRcw4sAU5YRvo+h2jPL/uzHz26UQF2tNPBp45XykLZUGEAZ9YXXnEW0Lrxd2mrr1Hjop7pUmHamJIQaRe/EyhYYfuWb0syQXJN7PfaGb95pMSvjOTvK8Qyn7OxFUG+OWwKBgQCM4c+T8rMd/JBuqgR6c7XZaPgBkYn0q19lL9gPL5NCimS4MkFq2glgYdb2vMdwkJgerHvw7hPo/wngmNa/ubFNnL4JTsrp/GQYyLXXa5iMz4lWT/MCdeU8Be9asNV8feEi5RWYWr8XEwLjbq5dpudhbZtL8MhLn8rPGlOfHe1GeQKBgQCVYStIF24fsVqK7i+Ge7da1nyZtWc0rnS053lmySqUI6namITs9PqsLXHj2DsEmHcV9Kq4qJVFTE4dalukLvIs0y2yUfxO9mZHZX/hKP6dPoXRohKFX2G3YCXe6dJ1U5JNnbXIitrHdf43no8JRNEtTMY/K3SffbtopvOJjrSL8QKBgEbJx+WNE5dDqscQQsPMlFkztzoCiREZcwuC6MtomlPa+hTbm6c2+3MkdkDwRj43SVhQTs6WsgZm7+2cnnsjsQVQduRyZN8BZMJXR5eAhtxqA5YNAgwAdTidutEU/ZBaJ9/CwAEXx+CuOstQd+iyzpxT2K5lxB7LobpCfjX7cZfxAoGAQm5EM8DIg5edDr6voc5zPpVlNGpPCON+RdcpOZIlAd7Cn88U0tsYj3ARtMn0OztR4XESmjBRJuGb7IZ2TG6cNrU0AGElGvId1VwLK/yLyC1K3Tvmxmwx8PwxUiWpI0CdoAOvS1OKOUyrvW2vrK2VezyW3AjS5VduVlPNaIm7H6A=";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuMQ3i0zun3eVNsAB429cuQLCkRgXuC31Qcke3gvn7tEcC+DJNmZLC6370Zc7x7YVMa2l/YcdBKPIWR9VK0fN1fzucLGDca8LO7WVUcoDymDzOHhpiGWQHqGndY0Iv0AkzI/UYNZ9XRKojCHPCPZxdBbLdAnYp/r7x3ImOahMYPUvpv8MUcrCvP0No2E6c3L32pd2n/5YjZP3BP96zVJ+sNMrPKstutAq+Y2Bs3/QWED8s8XZ70/hAU/Z+GuKSFqmHr99DT/lR3sgNzDbzrMJqbjixqW70TZtPdhWDCZJ36fckRCKu9kPRZ9PHi/kd6cWfq2y3XcHZ+GXDIwBzYwxCwIDAQAB";
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId("9021000157681961");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }

    public static String convertToHtml(String alipayParams) {
        try {
            // 解析参数字符串
            Map<String, String> params = parseParams(alipayParams);

            // 构建 HTML 表单
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n")
                    .append("<html lang=\"zh-CN\">\n")
                    .append("<head>\n")
                    .append("    <meta charset=\"UTF-8\">\n")
                    .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                    .append("    <title>支付宝支付</title>\n")
                    .append("</head>\n")
                    .append("<body>\n")
                    .append("    <form id=\"alipayForm\" action=\"https://openapi.alipay.com/gateway.do\" method=\"post\">\n");

            // 添加所有隐藏字段
            for (Map.Entry<String, String> entry : params.entrySet()) {
                html.append("        <input type=\"hidden\" name=\"")
                        .append(entry.getKey())
                        .append("\" value=\"")
                        .append(escapeHtml(entry.getValue()))
                        .append("\">\n");
            }

            html.append("    </form>\n")
                    .append("    <script>\n")
                    .append("        // 页面加载后自动提交表单\n")
                    .append("        document.addEventListener('DOMContentLoaded', function() {\n")
                    .append("            document.getElementById('alipayForm').submit();\n")
                    .append("        });\n")
                    .append("    </script>\n")
                    .append("</body>\n")
                    .append("</html>");

            return html.toString();

        } catch (Exception e) {
            throw new RuntimeException("转换支付宝参数为HTML失败", e);
        }
    }

    private static Map<String, String> parseParams(String paramsStr) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();

        // URL 解码
        String decodedStr = URLDecoder.decode(paramsStr, StandardCharsets.UTF_8.name());

        // 解析参数
        String[] pairs = decodedStr.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx > 0) {
                String key = pair.substring(0, idx);
                String value = pair.substring(idx + 1);
                params.put(key, value);
            }
        }

        return params;
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    /**
     * 解析通知请求
     */
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

    /**
     * 获取请求参数
     */
    private String getParameter(Map<String, String> params, String paramName) {
        String value = params.get(paramName);
        return value != null ? value.trim() : null;
    }

    /**
     * 解析业务内容
     */
    private AliRefundNotifyBizContent parseBizContent(String bizContent) {
        try {
            AliRefundNotifyBizContent content = JSON.parseObject(bizContent, AliRefundNotifyBizContent.class);
            log.info("解析业务内容成功: {}", JSON.toJSONString(content));
            return content;
        } catch (Exception e) {
            log.error("解析业务内容失败: {}", bizContent, e);
            throw new RuntimeException();
        }
    }
}
