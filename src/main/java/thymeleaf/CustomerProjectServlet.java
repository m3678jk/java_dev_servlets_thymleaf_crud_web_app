package thymeleaf;

import model.ServiceDB;
import model.commandsDB.entity.CompanyProject;
import model.commandsDB.entity.Customer;
import model.commandsDB.entity.CustomerProject;
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

@WebServlet("/customerProject")
public class CustomerProjectServlet extends HttpServlet {
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
                "existingComProj", new CustomerProject(2, 2)));
        resp.setContentType("text/html");
        engine.process("cust-proj-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        CustomerProject existingCustProj = (CustomerProject) service.getCommandsCustomerProject().selectData(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingCustProj", existingCustProj));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("cust-proj-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();


    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int customerId = Integer.parseInt(req.getParameter("customerId"));

        int projectId = Integer.parseInt(req.getParameter("projectId"));

        CustomerProject customerProject = new CustomerProject(customerId, projectId);
        System.out.println(customerProject);
        service.getCommandsCustomerProject().insertData(customerProject);
        list(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        int customerId = Integer.parseInt(req.getParameter("customerId"));
        int projectId = Integer.parseInt(req.getParameter("projectId"));
        CustomerProject customerProject = new CustomerProject(customerId, projectId);

        service.getCommandsCustomerProject().updateData(id, customerProject);
        list(req, resp);

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.getCommandsCustomerProject().delete(id);
        list(req, resp);

    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map list = service.getCommandsCustomerProject().selectAllData("id_cus_pr");
        System.out.println(list);
        Context ctx = new Context(req.getLocale(), Map.of("list", list));
        resp.setContentType("text/html");
        engine.process("cust-proj-list", ctx, resp.getWriter());
        resp.getWriter().close();

    }
}
