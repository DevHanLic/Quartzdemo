package amp.demo;

import lombok.Builder;
import lombok.Data;

/**
 * @ProjectName: quickpayment
 * @Package: com.cmpay.quickpayment.controller
 * @ClassName: User
 * @Author: HLC
 * @Description: 测试类
 * @Date: 2020/8/27 16:13
 */
@Data
@Builder
public class User  {

    private String bankUserName;

    private String password;

    private int flag;
}
