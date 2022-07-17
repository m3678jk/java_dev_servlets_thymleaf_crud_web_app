package servletsThymeleaf;

import model.serviceDAO.DAO.CompaniesDAO;
import model.serviceDAO.entity.Company;
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
import java.util.List;
import java.util.Map;

import static servletsThymeleaf.Setting.PATH_TO_TEMPLATES;

@WebServlet("/companies")
public class CompaniesServlet extends HttpServlet {
    private TemplateEngine engine;
    private CompaniesDAO service;

    @Override
    public void init()  {
        service = new CompaniesDAO();
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
                insertCompany(req, resp);
                break;
            case "delete":
                deleteCompany(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "update":
                updateCompany(req, resp);
                break;
            default:
                listUser(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingComp", new Company()));
        resp.setContentType("text/html");
        engine.process("comp-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Company existingComp = (Company) service.getById(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingComp", existingComp));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("comp-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insertCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Company com = new Company();
        com.setNameOfCompany(name);
        com.setAddress(address);
        service.insert(com);
        listUser(req, resp);
    }

    private void updateCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        long id = Long.parseLong(idString);
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Company comp = new Company();
        comp.setAddress(address);
        comp.setNameOfCompany(name);
        service.update(id, comp);
        listUser(req, resp);
    }

    private void deleteCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long id = Long.parseLong(req.getParameter("id"));
        service.delete(id);
        listUser(req, resp);
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Company> listCompanies = service.getList();
        Context ctx = new Context(req.getLocale(), Map.of("list", listCompanies));
        resp.setContentType("text/html");
        engine.process("comp-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
