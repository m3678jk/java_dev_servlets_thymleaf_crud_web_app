package servlets;

import model.ServiceDB;
import model.commandsDB.entity.Developer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(value = "/developers")
public class DeveloperServlet extends HttpServlet {
    private ServiceDB service;
    private Developer developer;

    @Override
    public void init() throws ServletException {
        try {
            service = new ServiceDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                insertDeveloper(req, resp);
                break;
            case "delete":
                deleteDeveloper(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
            case "update":
                updateDeveloper(req,resp);
            default:
                listUser(req, resp);
                break;
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("developer-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        Developer existingDeveloper = (Developer) service.getCommandsDevelopers().selectData(id);
        System.out.println(existingDeveloper);
        RequestDispatcher dispatcher = req.getRequestDispatcher("developer-form.jsp");
        req.setAttribute("id", id);
        req.setAttribute("developer", existingDeveloper);
        dispatcher.forward(req, resp);
    }

    private void insertDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        int age = Integer.parseInt(req.getParameter("age"));
        Developer.Sex sex = Developer.Sex.valueOf(req.getParameter("sex"));
        int salary = Integer.parseInt(req.getParameter("salary"));
        developer = new Developer(firstName, secondName, age, sex, salary);
        service.getCommandsDevelopers().insertData(developer);
        resp.sendRedirect("/developers");
    }

    private void updateDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.getInteger(req.getParameter("id"));
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        int age = Integer.parseInt(req.getParameter("age"));
        Developer.Sex sex = Developer.Sex.valueOf(req.getParameter("Sex"));
        int salary = Integer.parseInt(req.getParameter("salary"));
        Developer dev = new Developer(firstName, secondName, age, sex, salary);
        service.getCommandsDevelopers().updateData(id, dev);
        resp.sendRedirect("/developers");

    }
    private void deleteDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.getCommandsDevelopers().delete(id);
        resp.sendRedirect("/developers");

    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map listDevelopers = service.getCommandsDevelopers().selectAllData("id");
        req.setAttribute("listDevelopers", listDevelopers);
        RequestDispatcher dispatcher = req.getRequestDispatcher("developer-list.jsp");
        dispatcher.forward(req,resp);

    }
}
