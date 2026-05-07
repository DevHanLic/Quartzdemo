package amp.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新产品序列号响应DTO")
public class UpdateProductSerialRespDTO {

    @Schema(description = "是否成功")
    private String isSuccess;

    @Schema(description = "错误码")
    private String bizErrorCode;

    @Schema(description = "错误描述")
    private String bizErrorMsg;
}
