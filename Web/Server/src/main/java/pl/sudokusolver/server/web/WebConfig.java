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
import pl.sudokusolver.solver.ISolver;
import pl.sudokusolver.solver.SmartSolver;

/**
 * Main config file.<br>
 * Components are located in <code>pl.sudokusolver.server</code>.<br>
 * Have one properties file.
 */
@Configuration
@EnableWebMvc
@ComponentScan("pl.sudokusolver.server") //default packet for this project
@PropertySource("classpath:config.properties")
public class WebConfig implements WebMvcConfigurer{

    /**
     * openCV path when you are using windows. Loaded form properties file.
     */
    @Value("${openCVUrlWin}")
    private String openCVUrlWin;

    /**
     * openCV path when you are using linux. Loaded form properties file.
     */
    @Value("${openCVUrlLinux}")
    private String openCVUrlLinux;

    /**
     * Adding jsp support.
     * @param registry global registry
     */
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // directory to views folder
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * Adding assets (css and js).
     * @param registry global registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }

    /**
     * Accept <= 4 MB files.
     * @return bean which will be able to process multi part request.
     */
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multi = new CommonsMultipartResolver();
        multi.setMaxUploadSize(4000000); //4 MB
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
        return new SmartSolver();
    }


    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
        return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}