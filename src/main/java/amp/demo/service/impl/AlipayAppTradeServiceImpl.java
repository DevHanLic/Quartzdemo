package amp.demo.service.impl;

import amp.demo.service.AlipayAppTradeService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.ExtUserInfo;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlipayAppTradeServiceImpl implements AlipayAppTradeService {


    @Override
    public String AlipayAppPay() throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        // 设置商户订单号
        model.setOutTradeNo("70501111111S001111119");

        // 设置订单总金额
        model.setTotalAmount("9.00");

        // 设置订单标题
        model.setSubject("大乐透");

        // 设置产品码
        model.setProductCode("QUICK_MSECURITY_PAY");

        // 设置订单包含的商品列表信息
        List<GoodsDetail> goodsDetail = new ArrayList<GoodsDetail>();
        GoodsDetail goodsDetail0 = new GoodsDetail();
        goodsDetail0.setGoodsName("ipad");
        goodsDetail0.setAlipayGoodsId("20010001");
        goodsDetail0.setQuantity(1L);
        goodsDetail0.setPrice("2000");
        goodsDetail0.setGoodsId("apple-01");
        goodsDetail0.setGoodsCategory("34543238");
        goodsDetail0.setCategoriesTree("124868003|126232002|126252004");
        goodsDetail0.setShowUrl("http://www.alipay.com/xxx.jpg");
        goodsDetail.add(goodsDetail0);

        model.setGoodsDetail(goodsDetail);

        // 设置订单绝对超时时间
        model.setTimeExpire("2016-12-31 10:05:00");

        // 设置业务扩展参数
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088511833207846");
        extendParams.setHbFqSellerPercent("100");
        extendParams.setHbFqNum("3");
        extendParams.setIndustryRefluxInfo("{\"scene_code\":\"metro_tradeorder\",\"channel\":\"xxxx\",\"scene_data\":{\"asset_name\":\"ALIPAY\"}}");
        extendParams.setRoyaltyFreeze("true");
        extendParams.setCardType("S0JP0000");
        model.setExtendParams(extendParams);

        // 设置公用回传参数
        model.setPassbackParams("merchantBizType%3d3C%26merchantBizNo%3d2016010101111");

        // 设置商户的原始订单号
        model.setMerchantOrderNo("20161008001");

        // 设置外部指定买家
        ExtUserInfo extUserInfo = new ExtUserInfo();
        extUserInfo.setCertType("IDENTITY_CARD");
        extUserInfo.setCertNo("362334768769238881");
        extUserInfo.setName("李明");
        extUserInfo.setMobile("16587658765");
        extUserInfo.setMinAge("18");
        extUserInfo.setNeedCheckInfo("F");
        extUserInfo.setIdentityHash("27bfcd1dee4f22c8fe8a2374af9b660419d1361b1c207e9b41a754a113f38fcc");
        model.setExtUserInfo(extUserInfo);

        // 设置通知参数选项
        List<String> queryOptions = new ArrayList<String>();
        queryOptions.add("hyb_amount");
        queryOptions.add("enterprise_pay_info");
        model.setQueryOptions(queryOptions);

        request.setBizModel(model);
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
}
