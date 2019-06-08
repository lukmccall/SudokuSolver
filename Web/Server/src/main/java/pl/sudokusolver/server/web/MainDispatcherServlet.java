package pl.sudokusolver.server.web;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

/**
 * Main dispatcher class.
 */
public class MainDispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * @return config class.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    /**
     * @return null - unused.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // loading default config class
        return null; //new Class[] {WebConfig.class};
    }

    /**
     * Just listen on "/" path.
     * @return web entries points.
     */
    @Override
    protected String[] getServletMappings() {
        // map all entries points
        return new String[] {"/"};
    }

    /**
     * @return filter which is changing response to be in UTF-8.
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        return new Filter[] { characterEncodingFilter};
    }

    /**
     * Page not found handler.
     * @param registration global registration
     */
    @Override
    public void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}