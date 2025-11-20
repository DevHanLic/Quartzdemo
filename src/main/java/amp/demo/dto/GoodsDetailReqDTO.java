package amp.demo.dto;

import lombok.Data;

/**
 * @author han_lic
 * @date 202511/21 10:16
 * @desc app支付接口2.0 请求类
 */
@Data
public class GoodsDetailReqDTO {

    /**
     * 支付宝定义的统一商品编号
     * 可选
     */
    private String alipayGoodsId;

    /**
     * 商品类目树，从商品类目根节点到叶子节点的类目id组成，类目id值使用|分割
     * 可选
     */
    private String categoriesTree;

    /**
     * 商品类目
     * 可选
     */
    private String goodsCategory;

    /**
     * 商品的编号
     * 可选
     */
    private String goodsId;

    /**
     * 商品名称
     * 必选
     */
    private String goodsName;


    /**
     * 商品单价，单位为元
     * 必选
     */
    private String price;

    /**
     * 商品数量
     * 必选
     */
    private Long quantity;

    /**
     * 商品的展示地址
     * 可选
     */
    private String showUrl;

}
