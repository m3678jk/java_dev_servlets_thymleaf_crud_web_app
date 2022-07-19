package servletsThymeleaf;

import model.serviceDAO.dao.CustomerDAO;
import model.serviceDAO.entity.Customer;
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

@WebServlet("/customers")
public class CustomersServlet extends HttpServlet {
    private TemplateEngine engine;
    private CustomerDAO service;

    @Override
    public void init() {
        service = new CustomerDAO();
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
                insertCustomer(req, resp);
                break;
            case "delete":
                deleteCustomer(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "update":
                updateCustomer(req, resp);
                break;
            default:
                listUser(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingCustomer", new Customer()));
        resp.setContentType("text/html");
        engine.process("customer-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Customer existingCustomer = service.getById(id);
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingCustomer", existingCustomer));
        ctx.setVariable("id", id);
        resp.setContentType("text/html");
        engine.process("customer-edit-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void insertCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Customer com = new Customer();
        com.setAddress(address);
        com.setName(name);
        service.inset(com);
        listUser(req, resp);
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        long id = Long.parseLong(idString);
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        service.update(id, customer);
        listUser(req, resp);
    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long id = Long.parseLong(req.getParameter("id"));
        service.delete(id);
        listUser(req, resp);
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> listCustomers = service.getList();
        Context ctx = new Context(req.getLocale(), Map.of("list", listCustomers));
        resp.setContentType("text/html");
        engine.process("customer-list", ctx, resp.getWriter());
        resp.getWriter().close();
    }
}
