package SogangSolutionShare.BE.config;

import SogangSolutionShare.BE.Intercepter.LoginCheckInterceptor;
import SogangSolutionShare.BE.argumentResolver.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOrigins("http://localhost:3000", "http://localhost:3001")
                .allowedOriginPatterns("*")
                //.allowedOrigins("https://www.sogangsolutionshare.com",
                //        "https://capstone-front-esvu-git-fix-testfix-heejoon99s-projects.vercel.app/")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/join", "/api/login", "/api/logout", "/api/email/**", "/api/oauth/**", "/api/session");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}
