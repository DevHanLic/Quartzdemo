package amp.demo.enums;


import amp.demo.utils.AlertCapable;

/**
 * 图片上传下载结果
 *
 * Created on 2020/7/28
 *
 * @author: qinpo
 */
public enum MsgCodeEnum implements AlertCapable {

    /**
     * 返回码:返回信息
     */
    SUCCESSFUL("SUB00000", "交易成功"),
    IMAGE_PATH_LENGTH_ERROR("SUB30001", "图片路径长度不能大于128"),
    SERVICE_EXCEPTION("SUB30002", "系统内异常"),
    IMAGE_UPLOAD_ERROR("SUB30003", "图片上传失败"),
    IMAGE_NOT_EMPTY("SUB30004", "图片不能为空"),
    IMAGE_SIZE_PASS_2M("SUB30005", "图片大小不能超过2M"),
    FILE_PATH_NOT_EXIST("SUB30006", "图片路径错误"),
    IMAGE_DOWNLOAD_ERROR("SUB30007", "图片下载失败"),
    IMAGE_FORMAT_ERROR("SUB30008", "图片格式错误"),
    IMAGE_TYPE_ERROR("SUB30009", "图片类型错误"),
    IMAGE_PATH_NOT_EMPTY("SUB30010", "图片路径不能为空"),
    CONVERSION_FAILURE("SUB30011", "数据转换失败"),
    INFORMATION_FAILURE("SUB30012", "数据不能为空"),
    QUERYUSER_FAILURE("SUB30013", "查询失败,{1}"),
    QUERYLEVEL_FAILURE("SUB30014", "查询不到套餐档次信息"),
    QUERY_PRODUCT_INFORMATION_FAILURE("SUB30015", "查询不到对应的产品信息"),
    PRODUCT_INFORMATION_FAILURE("SUB30016", "产品信息数据错误"),


    ;

    MsgCodeEnum(String msgCd, String msgInfo) {
        this.msgCd = msgCd;
        this.msgInfo = msgInfo;
    }

    private final String msgCd;

    private final String msgInfo;

    @Override
    public String getMsgCd() {
        return msgCd;
    }

    @Override
    public String getMsgInfo() {
        return msgInfo;
    }
}
