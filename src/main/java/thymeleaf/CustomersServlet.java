package thymeleaf;

import model.ServiceDB;
import model.commandsDB.entity.Company;
import model.commandsDB.entity.Customer;
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

@WebServlet("/customers")
public class CustomersServlet extends HttpServlet {
    private TemplateEngine engine;

    private ServiceDB service;
    private Customer customer;

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
                insertCustomer(req, resp);
                break;
            case "delete":
                deleteCustomer(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
            case "update":
                updateCustomer(req, resp);
            default:
                listUser(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context ctx = new Context(req.getLocale(), Map.of(
                "existingCustomer", new Customer("enter name", "address")));
        resp.setContentType("text/html");
        engine.process("customer-new-form", ctx, resp.getWriter());
        resp.getWriter().close();
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        Customer existingCustomer = (Customer) service.getCommandsCustomers().selectData(id);
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
        Company com = new Company(name, address);

        service.getCommandsCompanies().insertData(com);
        listUser(req, resp);
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        System.out.println(id);
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Customer customer = new Customer(name, address);
        service.getCommandsCustomers().updateData(id, customer);
        listUser(req, resp);

    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.getCommandsCustomers().delete(id);
        listUser(req, resp);

    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map listCustomers = service.getCommandsCustomers().selectAllData("id_customer");
        System.out.println(listCustomers);
        Context ctx = new Context(req.getLocale(), Map.of("list", listCustomers));
        resp.setContentType("text/html");
        engine.process("customer-list", ctx, resp.getWriter());
        resp.getWriter().close();

    }
}