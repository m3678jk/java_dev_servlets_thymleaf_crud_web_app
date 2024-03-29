package servletsThymeleaf;


import model.serviceDAO.dao.ProjectDAO;
import model.serviceDAO.entity.Project;
import model.serviceDAO.manager.RelationManager;
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


@WebServlet("/companyProject")
public class CompanyProjectServlet extends HttpServlet {
    private TemplateEngine engine;
    private RelationManager service;

    @Override
    public void init() throws ServletException {
        service = new RelationManager();
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
        System.out.println(action);
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
            default:
                list(req, resp);
                break;
        }
    }


    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Integer.parseInt(req.getParameter("id"));
        Context ctx = new Context(req.getLocale(), Map.of(
                "enter", ""));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("com-proj-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Integer.parseInt(req.getParameter("id"));
        Context ctx = new Context(req.getLocale(), Map.of(
                "enter", ""));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("com-proj-delete-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long idProject = Long.parseLong(req.getParameter("id"));
        long idCompany = Long.parseLong(req.getParameter("companyId"));
        service.addCompanyToProject(idProject,idCompany);
        list(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long idProject = Long.parseLong(req.getParameter("id"));
        long idCompany = Long.parseLong(req.getParameter("companyId"));
        service.deleteCompanyFromProject(idProject,idCompany);
        list(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> list = projectDAO.getList();
        Context ctx = new Context(req.getLocale(), Map.of("list", list));
        resp.setContentType("text/html");
        engine.process("project-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
