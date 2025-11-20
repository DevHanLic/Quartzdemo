package amp.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author han_lic
 * @date 202511/21 10:16
 * @desc app支付接口2.0 请求类
 */
@Data
public class AlipayAppClientReqDTO {
    /**
     * 商户订单号
     * 必选
     */
    private String OutTradeNo;

    /**
     * 订单总金额
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

}
