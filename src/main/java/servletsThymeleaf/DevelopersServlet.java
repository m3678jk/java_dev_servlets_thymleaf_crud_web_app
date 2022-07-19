package servletsThymeleaf;

import model.serviceDAO.dao.DeveloperDAO;
import model.serviceDAO.entity.Developer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static servletsThymeleaf.Setting.PATH_TO_TEMPLATES;

@WebServlet("/developers")
public class DevelopersServlet extends HttpServlet {
    private TemplateEngine engine;
    private DeveloperDAO service;

    @Override
    public void init() {
        service = new DeveloperDAO();

        engine = new TemplateEngine();
        
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(PATH_TO_TEMPLATES);
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
                break;
            case "update":
                updateDeveloper(req, resp);
                break;
            default:
                listUser(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingDev", new Developer()));
        resp.setContentType("text/html");
        engine.process("dev-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        model.serviceDAO.entity.Developer existingDeveloper = service.getById(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingDev", existingDeveloper));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("dev-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insertDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String ageString = req.getParameter("age");
        int age = Integer.parseInt(ageString);
        Developer.Sex sex = Developer.Sex.valueOf(req.getParameter("sex"));
        int salary = Integer.parseInt(req.getParameter("salary"));
        Developer dev = new Developer();
                dev.setFirstName(firstName);
        dev.setAge(age);
        dev.setSalary(salary);
        dev.setSex(sex);
        dev.setSecondName(secondName);
        service.inset(dev);
        listUser(req, resp);
    }

    private void updateDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        long id = Long.parseLong(idString);
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String ageString = req.getParameter("age");
        int age = Integer.parseInt(ageString);
        Developer.Sex sex = Developer.Sex.valueOf(req.getParameter("sex"));
        int salary = Integer.parseInt(req.getParameter("salary"));
        Developer dev = new Developer();
        dev.setFirstName(firstName);
        dev.setAge(age);
        dev.setSalary(salary);
        dev.setSex(sex);
        dev.setSecondName(secondName);
        service.update(id, dev);
        listUser(req, resp);
    }

    private void deleteDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long id = Long.parseLong(req.getParameter("id"));
        service.delete(id);
        listUser(req, resp);
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Developer> listDevelopers = service.getList();
        Context ctx = new Context(req.getLocale(), Map.of("list", listDevelopers));
        resp.setContentType("text/html");
        engine.process("dev-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
