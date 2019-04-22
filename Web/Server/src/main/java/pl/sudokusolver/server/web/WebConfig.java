package pl.sudokusolver.server.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("pl.sudokusolver.server") //default packet for this project
public class WebConfig implements WebMvcConfigurer{

    public void configureViewResolvers(ViewResolverRegistry registry) {
        // directory to views folder
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multi = new CommonsMultipartResolver();
        multi.setMaxUploadSize(10000000); //todo: change it
        return multi;
    }
}