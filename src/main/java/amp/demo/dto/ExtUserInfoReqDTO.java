package amp.demo.dto;

import lombok.Data;

/**
 * @author han_lic
 * @date 2025/11/21 10:16
 * @desc app支付接口2.0 外部指定买家
 */
@Data
public class ExtUserInfoReqDTO {

    /**
     * 买家证件号
     * 可选
     */
    private String certNo;

    /**
     * 买家证件号
     * 可选
     */
    private String minAge;

    /**
     * 指定买家姓名
     * 可选
     */
    private String name;

    /**
     * 指定买家手机号
     * 可选
     */
    private String mobile;

    /**
     * 指定买家证件类型
     * 可选
     */
    private String certType;

    /**
     * 是否强制校验买家信息
     * 可选
     */
    private String needCheckInfo;

    /**
     * 买家加密身份信息。当指定了此参数且指定need_check_info=T时，支付宝会对买家身份进行校验，
     * 校验逻辑为买家姓名、买家证件号拼接后的字符串，以sha256算法utf-8编码计算hash，若与传入的值不匹配则会拦截本次支付。
     * 注意：如果同时指定了用户明文身份信息（name，cert_type，cert_no中任意一个），则忽略identity_hash以明文参数校验。
     * 可选
     */
    private String identityHash;
}
