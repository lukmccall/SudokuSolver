package pl.sudokusolver.server.web;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.server.bean.DigitRecognizer;
import pl.sudokusolver.solver.BrutalSolver;
import pl.sudokusolver.solver.ISolver;

@Configuration
@EnableWebMvc
@ComponentScan("pl.sudokusolver.server") //default packet for this project
@PropertySource("classpath:config.properties")
public class WebConfig implements WebMvcConfigurer{

    @Value("${openCVUrlWin}")
    private String openCVUrlWin;

    @Value("${openCVUrlLinux}")
    private String openCVUrlLinux;

    public void configureViewResolvers(ViewResolverRegistry registry) {
        // directory to views folder
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Register resource handler for CSS and JS
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
               // .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
                // todo: adding this later
    }

    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multi = new CommonsMultipartResolver();
        multi.setMaxUploadSize(10000000); //todo: change it
        return multi;
    }

    @Bean
    public DigitRecognizer digitRecognizer(){
        if(Init.getOperatingSystemType() == Init.OSType.Linux)
            return new DigitRecognizer(openCVUrlLinux);
        else
            return new DigitRecognizer(openCVUrlWin);
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