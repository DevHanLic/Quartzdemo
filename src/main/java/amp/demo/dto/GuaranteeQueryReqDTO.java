package amp.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "保修查询请求DTO")
public class GuaranteeQueryReqDTO {

    @Schema(description = "项目代码")
    private String projectCode;

    @Schema(description = "手机IMEI号")
    private String productSerialNo;
}
