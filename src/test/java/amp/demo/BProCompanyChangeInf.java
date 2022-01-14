package amp.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 企业变更信息对象 b_pro_company_change_inf
 * 
 * @author postLoan
 * @date 2021-12-10
 */
@Data
public class BProCompanyChangeInf {
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long pkId;

    /** 变更项ID */
    private String altId;

    /** 主体身份代码 */
    private String pripId;

    /** 变更事项*/
    private String altitem;

    /** 变更事项(中文) */
    private String altitemCn;

    /** 变更前内容 */
    private String altbe;

    /** 变更后内容 */
    private String altaf;

    /** 变更日期 */
    private Date altdate;

}
