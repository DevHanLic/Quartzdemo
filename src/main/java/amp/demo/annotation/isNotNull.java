package amp.demo.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author han_lic
 * @date 2020/11/12 17:15
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ParamConstraintValidatedNull.class)
public @interface isNotNull {

    String message() default "参数不为指定值11";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
