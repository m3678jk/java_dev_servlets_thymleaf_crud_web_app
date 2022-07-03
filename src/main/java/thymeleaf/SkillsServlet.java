package thymeleaf;

import model.ServiceDB;
import model.commandsDB.entity.Project;
import model.commandsDB.entity.Skills;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static model.commandsDB.entity.Skills.Technology.Java;

@WebServlet("/skills")
public class SkillsServlet extends HttpServlet {
    private TemplateEngine engine;

    private ServiceDB service;

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
                insert(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
            case "update":
                update(req, resp);
            default:
                list(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingSkills", new Skills(0,Java, "Junior")));
        resp.setContentType("text/html");
               engine.process("skills-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        Skills existingSkills = (Skills) service.getCommandsSkills().selectData(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingSkills", existingSkills));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("skills-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();


    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString =  req.getParameter("id");
        int id = Integer.parseInt(idString);
        System.out.println(id);
        String technology = req.getParameter("technology");
        System.out.println(technology);
        String levelOfPosition = req.getParameter("levelOfPosition");
        System.out.println(levelOfPosition);
        Skills skills = new Skills(id, Skills.Technology.valueOf(technology), levelOfPosition);
        System.out.println(skills);
        service.getCommandsSkills().insertData(skills);
        list(req,resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString =  req.getParameter("id");
        int id = Integer.parseInt(idString);
        System.out.println(id);
        String technology = req.getParameter("technology");
        System.out.println(technology);
        String levelOfPosition = req.getParameter("levelOfPosition");
        System.out.println(levelOfPosition);
        Skills skills = new Skills(id, Skills.Technology.valueOf(technology), levelOfPosition);
        service.getCommandsSkills().updateData(id,skills);
        list(req,resp);

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.getCommandsProject().delete(id);
        list(req,resp);

    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map list = service.getCommandsSkills().selectAllData("id_skills");
        System.out.println(list);
        Context ctx = new Context(req.getLocale(), Map.of("list", list));
        resp.setContentType("text/html");
        engine.process("skills-list", ctx, resp.getWriter());
        resp.getWriter().close();

    }
}
