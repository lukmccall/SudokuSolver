package pl.sudokusolver.server.web;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.sudokusolver.server.bean.DigitRecognizer;
import pl.sudokusolver.solver.BrutalSolver;
import pl.sudokusolver.solver.ISolver;

@Configuration
@EnableWebMvc
@ComponentScan("pl.sudokusolver.server") //default packet for this project
@PropertySource("classpath:config.properties")
public class WebConfig implements WebMvcConfigurer{

    @Value("${openCVUrl}")
    private String openCVUrl;

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

    @Bean
    public DigitRecognizer digitRecognizer(){
        return new DigitRecognizer(openCVUrl);
    }

    @Bean
    public ISolver solver(){
        return new BrutalSolver();
    }


    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
        return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}