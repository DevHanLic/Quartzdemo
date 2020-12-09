package amp.demo.entity;

import amp.demo.annotation.Check;
import amp.demo.annotation.isNotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author han_lic
 * @date 2020/11/12 10:57
 */
@Data
public class UserTest {
    /**
     * 姓名
     * */
    private String name;

    /**
     * 性别 man or women
     * */
//    @Check(paramValues = {"man", "woman"})
    private String sex;

//    @isNotNull(message = "id 不能为空")
    private String id;

    private Long userNo;

    private BigDecimal txAmt;

    private String tmSmp;
}
