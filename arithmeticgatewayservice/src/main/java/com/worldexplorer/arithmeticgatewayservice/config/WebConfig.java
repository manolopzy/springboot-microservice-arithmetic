package com.worldexplorer.arithmeticgatewayservice.config;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods:
            - GET
            - POST
 * @author tanku
 *
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
    	registry.addMapping("/**");
    }
    
    
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//        registry.addMapping("/api/**")
//            .allowedOrigins("https://domain2.com")
//            .allowedMethods("PUT", "DELETE")
//            .allowedHeaders("header1", "header2", "header3")
//            .exposedHeaders("header1", "header2")
//            .allowCredentials(true).maxAge(3600);
//
//        // Add more mappings...
//    }
}