package pl.sudokusolver.server.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MainDispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // loading default config class
        return new Class[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // map all entries points
        return new String[] {"/"};
    }
}
