package amp.demo.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author han_lic
 * @date 2021/5/8 8:43
 */
@Data
public class UpdateProductSerialRespDTO {

    @ApiModelProperty(value = "是否成功")
    private String isSuccess;

    @ApiModelProperty(value = "错误码")
    private String bizErrorCode;

    @ApiModelProperty(value = "错误描述")
    private String bizErrorMsg;
}
