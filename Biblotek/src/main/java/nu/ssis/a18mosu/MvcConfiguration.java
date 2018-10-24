package nu.ssis.a18mosu;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry
          .addResourceHandler("/images/**")
          .addResourceLocations("classpath:/src/main/resources/static/images/");
    	registry
          .addResourceHandler("/webjars/**")
          .addResourceLocations("/webjars/");
    	registry
    		.addResourceHandler("/js/")
    		.addResourceLocations("classpath:/src/main/resources/static/js/");
    	registry
    		.addResourceHandler("/css/")
    		.addResourceLocations("classpath:/src/main/resources/static/css/");
    }
    
}