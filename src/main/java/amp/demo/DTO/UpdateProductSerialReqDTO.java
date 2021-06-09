package amp.demo.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author han_lic
 * @date 2021/5/8 8:38
 */
@Data
public class UpdateProductSerialReqDTO {

    @ApiModelProperty(value = "项目代码")
    private  String projectCode;

    @ApiModelProperty(value = "手机IMEI号")
    private  String productSerialNo;

    @ApiModelProperty(value = "新手机IMEI号")
    private  String newProductSerialNo;

    @ApiModelProperty(value = "换机时间")
    private  String changeTime;

    @ApiModelProperty(value = "业务扩展信息")
    private  String extraInfo;

}
