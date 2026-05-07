package amp.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新产品序列号请求DTO")
public class UpdateProductSerialReqDTO {

    @Schema(description = "项目代码")
    private String projectCode;

    @Schema(description = "手机IMEI号")
    private String productSerialNo;

    @Schema(description = "新手机IMEI号")
    private String newProductSerialNo;

    @Schema(description = "换机时间")
    private String changeTime;

    @Schema(description = "业务扩展信息")
    private String extraInfo;
}
