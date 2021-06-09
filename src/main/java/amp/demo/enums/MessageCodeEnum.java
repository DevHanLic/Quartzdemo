package amp.demo.enums;

/**
 * @author han_lic
 * @date 2021/5/6 15:35
 */
public enum MessageCodeEnum {

    ELSE_EX_ERROR("-100000","其他异常错误，具体错误信息见返回的错误信息字段"),
    ON_PERMISSION("-100001","没有权限（项目代号不存在或IP地址不在白名单内）"),
    MUST_PARAMETER_NOT_NULL("-100002","必填参数不能为空"),
    ORDER_IS_EXIST("-100003","订单号已存在"),
    PAY_IS_IMEI("-100004","串号已购买此产品"),
    NOT_FIND_PRODUCT("-100005","没有找到对应的延保产品"),
    IMEI_IS_ILLEGAL("-100006","串号不合法"),
    IMEI_FORMAT_ERROR("-100007","时间格式有误"),
    IMEI_GUARANTEE_EXIT("-100008","此串号对应的保单已退保"),
    IMEI_WITH_ORDER_NOT_MATCH("-100009","订单号与串号不匹配"),
    EXCEED_SURRENDER_TIME("-100010","已超出可退保时间"),
    SURRENDER_FAILED_GUARANTEE_REPORT("-100011","退保失败，保单已有报案"),
    NOT_FIND_ORDER("-100012","没有找到对应的订单"),
    NEW_IMEI_EXIST_EFFECTIVE_GUARANTEE("-100013","新串号存在有效保单"),
    GUARANTEE_WITH_IMEI_NOT_MATCH("-100014","保单号与串号不匹配"),
    GUARANTEE_IS_FINISH_NOT_REPAIR("-100015","该保单已终止，不能报修"),
    GUARANTEE_NOT_FINISH_REPAIR("-100016","该保单存在未完成的报修，不能重复报修"),
    CASE_NUMBER_IS_NOT_EXIST("-100017","报案号不存在"),
    REPORT_STATUS_NOT_UPDATE("-100018","该报案状态不允许修改资料"),
    ;

    private String msgCd;
    private String msgInfo;
    public String getMsgCd() {
        return msgCd;
    }
    public String getMsgInfo() {
        return msgInfo;
    }

    MessageCodeEnum(String msgCd, String msgInfo) {
        this.msgCd = msgCd;
        this.msgInfo = msgInfo;
    }
}
