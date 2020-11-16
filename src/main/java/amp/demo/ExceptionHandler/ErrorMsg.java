package amp.demo.ExceptionHandler;

/**
 * @author han_lic
 * @date 2020/11/12 17:01
 */

import lombok.Data;

@Data
public class ErrorMsg {
    private String Field;
    private String ObjectName;
    private String Message;
}
