package amp.demo.dto;

import lombok.Data;

/**
 * @author han_lic
 * @date 2025/11/21 10:16
 * @desc app退款接口 退款包含的商品列表信息
 */
@Data
public class RefundGoodsDetailReqDTO {

    /**
     * 商品编号
     * 可选
     */
    private String goodsId;

    /**
     * 该商品的退款总金额，单位为元
     * 可选
     */
    private String refundAmount;

    /**
     * 外部商品凭证编号列表
     * 可选
     */
    private String outCertificateNoList;

    /**
     * 商家侧小程序商品ID
     * 可选
     */
    private String outItemId;

    /**
     * 商家侧小程序商品sku ID
     * 可选
     */
    private String outSkuId;

}
