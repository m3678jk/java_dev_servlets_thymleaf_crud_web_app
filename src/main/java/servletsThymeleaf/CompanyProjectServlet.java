package servletsThymeleaf;

import model.ServiceDB;
import model.commandsDB.entity.CompanyProject;
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

import static servletsThymeleaf.Setting.PATH_TO_TEMPLATES;

@WebServlet("/companyProject")
public class CompanyProjectServlet extends HttpServlet {
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
                "existingComProj", new CompanyProject(2, 2)));
        resp.setContentType("text/html");
        engine.process("com-proj-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        CompanyProject existingCompProj = (CompanyProject) service.getCommandsCompanyProject().selectData(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingCompProj", existingCompProj));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("com-proj-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int idCompany = Integer.parseInt(req.getParameter("companyId"));
        int idProject = Integer.parseInt(req.getParameter("projectId"));
        CompanyProject companyProject = new CompanyProject(idCompany, idProject);
        service.getCommandsCompanyProject().insertData(companyProject);
        list(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        int idCompany = Integer.parseInt(req.getParameter("companyId"));
        int idProject = Integer.parseInt(req.getParameter("projectId"));
        CompanyProject companyProject = new CompanyProject(idCompany, idProject);
        service.getCommandsCompanyProject().updateData(id, companyProject);
        list(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.getCommandsCompanyProject().delete(id);
        list(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map list = service.getCommandsCompanyProject().selectAllData("id_com_pr");
        Context ctx = new Context(req.getLocale(), Map.of("list", list));
        resp.setContentType("text/html");
        engine.process("com-proj-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
