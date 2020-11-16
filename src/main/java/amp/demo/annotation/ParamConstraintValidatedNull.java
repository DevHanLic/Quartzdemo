package amp.demo.annotation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author han_lic
 * @date 2020/11/12 17:17
 */
public class ParamConstraintValidatedNull  implements ConstraintValidator<isNotNull, Object> {

    @Override
    public void initialize(isNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) {
            return false;
        }
        //不在指定的参数列表中
        return true;
    }
}
