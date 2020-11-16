package amp.demo.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author han_lic
 * @date 2020/11/12 14:55
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //权限验证拦截
        registry.addInterceptor(new PermissionCheckInterceptor()).addPathPatterns("/api/test2");

    }

}
