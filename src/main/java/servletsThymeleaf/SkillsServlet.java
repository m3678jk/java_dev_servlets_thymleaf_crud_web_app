package servletsThymeleaf;


import model.serviceDAO.dao.DeveloperDAO;
import model.serviceDAO.dao.SkillsDAO;
import model.serviceDAO.entity.Developer;
import model.serviceDAO.entity.Skills;
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


@WebServlet("/skills")
public class SkillsServlet extends HttpServlet {
    private TemplateEngine engine;
    private SkillsDAO service;

    @Override
    public void init() {
        service = new SkillsDAO();
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(getServletContext()
                .getRealPath("WEB-INF/templates/")+"/");
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
            case "remove":
                showDeleteForm(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "redirect":
                showSelectIdForm(req,resp);
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

    private void showSelectIdForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Context ctx = new Context(req.getLocale(), Map.of(
                "enter", ""));
        ctx.setVariable("id", id);

        resp.setContentType("text/html");
        engine.process("skills-select-id-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Context ctx = new Context(req.getLocale(), Map.of(
                "enter", ""));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("skills-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long skillsId = Long.parseLong(req.getParameter("skillsId"));
        Skills existingSkills = service.getById(skillsId);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingSkills", existingSkills));
        ctx.setVariable("skillsId", skillsId);
        resp.setContentType("text/html");
        engine.process("skills-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Integer.parseInt(req.getParameter("id"));
        Context ctx = new Context(req.getLocale(), Map.of(
                "enter", ""));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("skills-delete-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        long id = Long.parseLong(idString);
        Skills.Technology technology = Skills.Technology.valueOf(req.getParameter("technology"));
        String levelOfPosition = req.getParameter("levelOfPosition");
        Skills skills = new Skills();
        skills.setTechnology(technology);
        skills.setLevelOfPosition(levelOfPosition);
        service.insert(id,skills);
        list(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("skillsId");
        long id = Long.parseLong(idString);
        Skills.Technology technology = Skills.Technology.valueOf(req.getParameter("technology"));
        String levelOfPosition = req.getParameter("levelOfPosition");
        Skills skills = new Skills();
        skills.setTechnology(technology);
        skills.setLevelOfPosition(levelOfPosition);
        service.update(id, skills);
        list(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        int id = Integer.parseInt(req.getParameter("id"));
        long skillsId = Long.parseLong(req.getParameter("skillsId"));
        service.delete(skillsId);
        list(req, resp);

    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        DeveloperDAO developerDAO = new DeveloperDAO();
        List<Developer> listDevelopers = developerDAO.getList();
        Context ctx = new Context(req.getLocale(), Map.of("list", listDevelopers));
        resp.setContentType("text/html");
        engine.process("dev-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
