package servletsThymeleaf;

import model.serviceDAO.DAO.ProjectDAO;
import model.serviceDAO.entity.Project;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static servletsThymeleaf.Setting.PATH_TO_TEMPLATES;

@WebServlet("/projects")
public class ProjectsServlet extends HttpServlet {
    private TemplateEngine engine;
    private ProjectDAO service;

    @Override
    public void init() {
        service = new ProjectDAO();
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
                "existingProj", new Project()));
        resp.setContentType("text/html");
        engine.process("project-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Project existingProj = service.getById(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingProj", existingProj));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("project-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String nameOfProject = req.getParameter("nameOfProject");
        String description = req.getParameter("description");
        String date = req.getParameter("date");
        Project proj = new Project();
        proj.setDate(LocalDate.parse(date));
        proj.setDescription(description);
        proj.setNameOfProject(nameOfProject);

        service.inset(proj);
        list(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        String nameOfProject = req.getParameter("nameOfProject");
        String description = req.getParameter("description");
        String date = req.getParameter("date");
        Project proj = new Project();
        proj.setNameOfProject(nameOfProject);
        proj.setDescription(description);
        proj.setDate(LocalDate.parse(date));
        service.update(id, proj);
        list(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id);
        list(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Project> list = service.getList();
        Context ctx = new Context(req.getLocale(), Map.of("list", list));
        resp.setContentType("text/html");
        engine.process("project-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
