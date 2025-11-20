package amp.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author han_lic
 * @date 2025/11/21 10:16
 * @desc app支付接口2.0 请求类
 */
@Data
public class AliPayAppClientReqDTO {

    /**
     * 商户订单号
     * 必选
     */
    private String outTradeNo;

    /**
     * 订单总金额 单位为元，精确到小数点后两位，取值范围[0.01,100000000]，金额不能为0
     * 必选
     */
    private String totalAmount;

    /**
     * 订单标题
     * 必选
     */
    private String subject;

    /**
     * 产品码
     * 可选
     */
    private String productCode;

    /**
     * 设置订单包含的商品列表信息
     * 可选
     */
    private List<GoodsDetailReqDTO> goodsDetailReqDTOList;

    /**
     * 绝对超时时间，格式为yyyy-MM-dd HH:mm:ss
     * 可选
     */
    private String timeExpire;

    /**
     * 业务扩展参数
     * 可选
     */
    private ExtendParamsReqDTO extendParamsReqDTO;

    /**
     * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）
     * 和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
     * 可选
     */
    private String passBackParams;

    /**
     * 商户原始订单号，最大长度限制32位
     *
     * 可选
     */
    private String merchantOrderNo;

    /**
     * 外部指定买家
     *
     * 可选
     */
    private ExtUserInfoReqDTO extUserInfoReqDTO;

    /**
     * 返回参数选项。 商户通过传递该参数来定制同步需要额外返回的信息字段，数组格式
     * 可选
     */
    private List<String> queryOptionsList;

}
