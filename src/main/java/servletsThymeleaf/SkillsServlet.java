package servletsThymeleaf;

import model.ServiceDB;
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
import static servletsThymeleaf.Setting.PATH_TO_TEMPLATES;

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
                insert(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            default:
                list(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingSkills", new Skills(0, Java, "Junior")));
        resp.setContentType("text/html");
        engine.process("skills-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Skills existingSkills = (Skills) service.getCommandsSkills().selectData(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingSkills", existingSkills));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("skills-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        String technology = req.getParameter("technology");
        String levelOfPosition = req.getParameter("levelOfPosition");
        Skills skills = new Skills(id, Skills.Technology.valueOf(technology), levelOfPosition);
        service.getCommandsSkills().insertData(skills);
        list(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        String technology = req.getParameter("technology");
        String levelOfPosition = req.getParameter("levelOfPosition");
        Skills skills = new Skills(id, Skills.Technology.valueOf(technology), levelOfPosition);
        service.getCommandsSkills().updateData(id, skills);
        list(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.getCommandsProject().delete(id);
        list(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map list = service.getCommandsSkills().selectAllData("id_skills");
        Context ctx = new Context(req.getLocale(), Map.of("list", list));
        resp.setContentType("text/html");
        engine.process("skills-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
