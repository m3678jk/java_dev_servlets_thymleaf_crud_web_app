package thymeleaf;

import model.ServiceDB;
import model.commandsDB.entity.Developer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/test")
public class DeveloperServletThym extends HttpServlet {
    private TemplateEngine engine;

    private ServiceDB service;
    private Developer developer;

    @Override
    public void init() throws ServletException {
        try {
            service = new ServiceDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        //TODO change the path
        resolver.setPrefix("C:\\Java\\jm\\maven-test\\servlets-hw-6\\servlets-hw-6\\src\\main\\resources\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String action = req.getParameter("action") != null ? req.getParameter("action") : "none";
        System.out.println(action);
        switch (action) {
            case "new":
                showNewForm(req, resp);
                break;
            case "insert":
                insertDeveloper(req, resp);
                break;
            case "delete":
                deleteDeveloper(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
            case "update":
                updateDeveloper(req, resp);
            default:
                listUser(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingDev", "rr"));
        engine.process("dev-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        Developer existingDeveloper = (Developer) service.getCommandsDevelopers().selectData(id);
        System.out.println(existingDeveloper+ "in showEdit");
        String firstName = existingDeveloper.getFirstName();
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingDev", existingDeveloper));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("test", ctx, resp.getWriter());
        resp.getWriter().close();


    }

    private void insertDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Context ctx = new Context(req.getLocale(), Map.of("name", "some name"));
        resp.setContentType("text/html");
        engine.process("test", ctx, resp.getWriter());
        resp.getWriter().close();

        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        int age = Integer.parseInt(req.getParameter("age"));
        Developer.Sex sex = Developer.Sex.valueOf(req.getParameter("sex"));
        int salary = Integer.parseInt(req.getParameter("salary"));
        developer = new Developer(firstName, secondName, age, sex, salary);
        service.getCommandsDevelopers().insertData(developer);
        resp.sendRedirect("/developers");
    }

    private void updateDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString =  req.getParameter("id");
        int id = Integer.parseInt(idString);
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String ageString = req.getParameter("age");
        int age = Integer.parseInt(ageString);
        Developer.Sex sex = Developer.Sex.valueOf(req.getParameter("sex"));
        int salary = Integer.parseInt(req.getParameter("salary"));
        Developer dev = new Developer(firstName, secondName, age, sex, salary);
        System.out.println(dev+ "update dev" );
        service.getCommandsDevelopers().updateData(id, dev);
        listUser(req,resp);

    }

    private void deleteDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.getCommandsDevelopers().delete(id);
        listUser(req,resp);

    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map listDevelopers = service.getCommandsDevelopers().selectAllData("id");
        System.out.println(listDevelopers);
        Context ctx = new Context(req.getLocale(), Map.of("list", listDevelopers));
        resp.setContentType("text/html");
        engine.process("dev-list", ctx, resp.getWriter());
        resp.getWriter().close();

    }
}
