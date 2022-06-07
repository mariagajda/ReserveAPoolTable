package pl.coderslab.reserveapooltable.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/errors/403").setViewName("403");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/images/**", "/resources/js/**", "/resources/css/**")
                .addResourceLocations("/resources/images/", "/resources/js/", "/resources/css/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver())
                .addResolver(new PathResourceResolver());
    }
}
