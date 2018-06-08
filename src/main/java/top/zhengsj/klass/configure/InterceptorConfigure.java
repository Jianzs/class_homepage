package top.zhengsj.klass.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.zhengsj.klass.web.interceptor.AuthorizationInterceptor;
import top.zhengsj.klass.web.interceptor.ParseJWTInterceptor;

@Configuration
public class InterceptorConfigure implements WebMvcConfigurer {
    private ParseJWTInterceptor parseJWTInterceptor;
    private AuthorizationInterceptor authorizationInterceptor;

    public InterceptorConfigure(ParseJWTInterceptor parseJWTInterceptor,
                                AuthorizationInterceptor authorizationInterceptor) {
        this.parseJWTInterceptor = parseJWTInterceptor;
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(parseJWTInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(authorizationInterceptor)
//                .addPathPatterns("/admin/**");
    }
}
