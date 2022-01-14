package amp.demo.DTO;

import amp.demo.entity.UserTest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author han_lic
 * @date 2021/5/7 20:22
 */
@Data
public class ResultData {

    @ApiModelProperty(value = "保单号")
    private  String policyNo;

    @ApiModelProperty(value = "保单状态")
    private  String policyStatus;

    @ApiModelProperty(value = "终止原因")
    private  String voidReason;

    @ApiModelProperty(value = "起保时间")
    private  String insuranceStartDate;

    @ApiModelProperty(value = "服务终止时间 ")
    private  String insuranceEndDate;

    @ApiModelProperty(value = "退保时间")
    private  String surrenderDate;

    @ApiModelProperty(value = "订单号")
    private  String channelOrderNo;

    List<UserTest> userTestList;
}
