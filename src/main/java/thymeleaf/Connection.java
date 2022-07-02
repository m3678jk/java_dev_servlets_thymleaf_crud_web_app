//package thymeleaf;
//
//import lombok.SneakyThrows;
//import model.ServiceDB;
//import model.commandsDB.entity.Developer;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import org.thymeleaf.templateresolver.FileTemplateResolver;
//
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Map;
//
//@WebServlet("/thymeleaf")
//public class Connection extends HttpServlet {
//    private TemplateEngine engine;
//
//    @Override
//    public void init() throws ServletException {
//        engine = new TemplateEngine();
//        FileTemplateResolver resolver = new FileTemplateResolver();
//        //TODO change the path
//        resolver.setPrefix("C:\\Java\\jm\\maven-test\\servlets-hw-6\\servlets-hw-6\\src\\main\\resources\\templates\\");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML5");
//        resolver.setOrder(engine.getTemplateResolvers().size());
//        resolver.setCacheable(false);
//        engine.addTemplateResolver(resolver);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.doGet(req, resp);
//    }
//
//    @SneakyThrows
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ServiceDB service = new ServiceDB();
//        Map listDevelopers = service.getCommandsDevelopers().selectAllData("id");
//        Context ctx = new Context(req.getLocale(), Map.of("list", listDevelopers));
//        resp.setContentType("text/html");
//        engine.process("dev-list", ctx, resp.getWriter());
//        resp.getWriter().close();
//    }
//
//    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
////        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
////        WebContext context = new WebContext(request, response, request.getServletContext());
////        context.setVariable("recipient", "World");
////        engine.process("index.html", context, response.getWriter());
////        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
////        Map listDevelopers = service.getCommandsDevelopers().selectAllData("id");
////        System.out.println(listDevelopers);
////        WebContext ctx = new WebContext(req, resp, req.getServletContext());
////        ctx.setVariable("listDevelopers", listDevelopers);
//        // resp.setContentType("text/html");
//
////        engine.process("dev-list", ctx, resp.getWriter());
////        resp.getWriter().close();
//
//    }
//    private void insertDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
//
//
//        String firstName = req.getParameter("firstName");
//        String secondName = req.getParameter("secondName");
//        int age = Integer.parseInt(req.getParameter("age"));
//        Developer.Sex sex = Developer.Sex.valueOf(req.getParameter("sex"));
//        int salary = Integer.parseInt(req.getParameter("salary"));
//        Developer developer = new Developer(firstName, secondName, age, sex, salary);
//        ServiceDB service = new ServiceDB();
//        service.getCommandsDevelopers().insertData(developer);
//        resp.sendRedirect("/developers");
//
//        Context ctx = new Context(req.getLocale(), Map.of("dev", developer));
//        resp.setContentType("text/html");
//        engine.process("test", ctx, resp.getWriter());
//        resp.getWriter().close();
//    }
//}
