package amp.demo.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author han_lic
 * @date 2021/5/7 20:16
 */
@Data
public class GuaranteeQueryReqDTO {

    @ApiModelProperty(value = "项目代码")
    private  String projectCode;

    @ApiModelProperty(value = "手机IMEI号")
    private  String productSerialNo;

}
