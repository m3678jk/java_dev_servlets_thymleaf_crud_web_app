package thymeleaf.conf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
/**
 * Thymeleaf configuration.
 */
@WebListener
public class ThymeleafConfig implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        TemplateEngine engine = templateEngine(sce.getServletContext());
        TemplateEngineUtil.storeTemplateEngine(sce.getServletContext(), engine);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    private TemplateEngine templateEngine(ServletContext servletContext) {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver(servletContext));
        return engine;
    }

    private ITemplateResolver templateResolver(ServletContext servletContext) {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
        resolver.setPrefix("C:\\Java\\jm\\maven-test\\servlets-hw-6\\servlets-hw-6\\src\\main\\resources\\templates\\");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

}
