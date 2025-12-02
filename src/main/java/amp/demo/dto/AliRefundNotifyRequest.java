package amp.demo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AliRefundNotifyRequest {

    @JSONField(name = "charset")
    private String charset;

    @JSONField(name = "biz_content")
    private String bizContent;

    @JSONField(name = "utc_timestamp")
    private String utcTimestamp;

    @JSONField(name = "sign")
    private String sign;

    @JSONField(name = "app_id")
    private String appId;

    @JSONField(name = "version")
    private String version;

    @JSONField(name = "sign_type")
    private String signType;

    @JSONField(name = "notify_id")
    private String notifyId;

    @JSONField(name = "msg_method")
    private String msgMethod;

}
