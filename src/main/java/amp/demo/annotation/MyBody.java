package amp.demo.annotation;

import java.lang.annotation.*;

/**
 * @author han_lic
 * @date 2020/11/10 11:15
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyBody {

      boolean required() default true;
}
