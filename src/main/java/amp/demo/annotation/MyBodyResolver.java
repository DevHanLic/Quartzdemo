package amp.demo.annotation;

import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author han_lic
 * @date 2020/11/12 15:51
 */
public class MyBodyResolver extends AbstractMessageConverterMethodProcessor {
    public MyBodyResolver(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MyBody.class);
    }

    // 类上或者方法上标注了@ResponseBody注解都行
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), MyBody.class) || returnType.hasMethodAnnotation(MyBody.class));
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 它是支持`Optional`容器的
        parameter = parameter.nestedIfOptional();
        // 使用消息转换器HttpInputMessage把request请求转换出来，拿到值~~~
        // 此处注意：比如本例入参是Person类，所以经过这里处理会生成一个空的Person对象出来（反射）
        Object arg = readWithMessageConverters(webRequest, parameter, parameter.getNestedGenericParameterType());

        // 获取到入参的名称,其实不叫形参名字，应该叫objectName给校验时用的
        // 请注意：这里的名称是类名首字母小写，并不是你方法里写的名字。比如本利若形参名写为personAAA，但是name的值还是person
        // 但是注意：`parameter.getParameterName()`的值可是personAAA
        String name = Conventions.getVariableNameForParameter(parameter);

        // 只有存在binderFactory才会去完成自动的绑定、校验~
        // 此处web环境为：ServletRequestDataBinderFactory
        if (binderFactory != null) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);

            // 显然传了参数才需要去绑定校验嘛
            if (arg != null) {

                // 这里完成数据绑定+数据校验~~~~~（绑定的错误和校验的错误都会放进Errors里）
                // Applicable：适合
                validateIfApplicable(binder, parameter);

                // 若有错误消息hasErrors()，并且仅跟着的一个参数不是Errors类型，Spring MVC会主动给你抛出MethodArgumentNotValidException异常
                // 否则，调用者自行处理
                if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                    throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
                }
            }

            // 把错误消息放进去 证明已经校验出错误了~~~
            // 后续逻辑会判断MODEL_KEY_PREFIX这个key的~~~~
            if (mavContainer != null) {
                mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
            }
        }

        return adaptArgumentIfNecessary(arg, parameter);
    }

    // 校验，如果合适的话。使用WebDataBinder，失败信息最终也都是放在它身上~  本方法是本文关注的焦点
    // 入参：MethodParameter parameter
    @Override
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        // 拿到标注在此参数上的所有注解们（比如此处有@Valid和@RequestBody两个注解）
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation ann : annotations) {
            // 先看看有木有@Validated
            Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);

            // 这个里的判断是关键：可以看到标注了@Validated注解 或者注解名是以Valid打头的 都会有效哦
            //注意：这里可没说必须是@Valid注解。实际上你自定义注解，名称只要一Valid开头都成~~~~~
            if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                // 拿到分组group后，调用binder的validate()进行校验~~~~
                // 可以看到：拿到一个合适的注解后，立马就break了~~~
                // 所以若你两个主机都标注@Validated和@Valid，效果是一样滴~
                Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
                Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
                binder.validate(validationHints);
                break;
            }
        }
    }
}
