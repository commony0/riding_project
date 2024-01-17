package dev.mvc.riding_v1sbm3c;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.detail.Detail;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows: path = "C:/kd/deploy/resort_v3sbm3c_blog/detail/storage";
        // ▶ file:///C:/kd/deploy/resort_v3sbm3c_blog/detail/storage
      
        // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/detail/storage";
        // ▶ file:////home/ubuntu/deploy/resort_v2sbm3c_blog/detail/storage
      
        // JSP 인식되는 경로: http://localhost:9091/detail/storage";
        registry.addResourceHandler("/detail/storage/**").addResourceLocations("file:///" +  Detail.getUploadDir());
        
        // JSP 인식되는 경로: http://localhost:9091/attachfile/storage";
        // registry.addResourceHandler("/detail/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
        
        // JSP 인식되는 경로: http://localhost:9091/member/storage";
        // registry.addResourceHandler("/detail/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
    }
 
}