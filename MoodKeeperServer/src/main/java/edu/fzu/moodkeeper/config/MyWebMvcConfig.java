//package edu.fzu.moodkeeper.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration // 表示这是个配置类
//public class MyWebMvcConfig implements WebMvcConfigurer {
////    /**
////     * ·
////     * 静态资源映射
////     */
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
////                .addResourceLocations("classpath:/resources/").addResourceLocations("classpath:/static/")
////                .addResourceLocations("classpath:/public/");
//////        super(registry);
////    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
//                .addResourceLocations("classpath:/resources/").addResourceLocations("classpath:/static/")
//                .addResourceLocations("classpath:/public/");
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }
//
//
//    //    @Override
////    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
////        handlers.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
////                .addResourceLocations("classpath:/resources/").addResourceLocations("classpath:/static/")
////                .addResourceLocations("classpath:/public/");
////        WebMvcConfigurer.super.addReturnValueHandlers(handlers);
////    }
//}