package servletsJSP;

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
import java.util.List;
import java.util.Map;

@WebServlet(value = "/additional")
public class AdditionalCommandsServlet extends HttpServlet {
    private ServiceDB service;


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
            case "listOfMidDevelopers":
                listOfMidDevelopers(req, resp);
                break;
            case "listOfJavaDevelopers":
                listOfJavaDevelopers(req, resp);
                break;
            case "listOfProjects":
                listOfProjects(req, resp);
                break;
            case "listOfDev":
                listOfDev(req, resp);
                break;
            case "salary":
                salary(req, resp);
                break;

        }
    }

    private void salary(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=utf-8");
        int id = Integer.parseInt(parseId(req));

        int salary=service.getOperationOnDB().getSumOfSalary(id);
        req.setAttribute("id", id);
        req.setAttribute("salary", salary);
        RequestDispatcher dispatcher = req.getRequestDispatcher("salary.jsp");
        dispatcher.forward(req,resp);
    }

    private void listOfMidDevelopers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map list = service.getOperationOnDB().getListMidDev();
        req.setAttribute("list", list);
        req.setAttribute("text", "middle");

        RequestDispatcher dispatcher = req.getRequestDispatcher("developer-list.jsp");
        dispatcher.forward(req,resp);

    }

    private void listOfProjects(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List list = service.getOperationOnDB().getListOfProject();
        System.out.println(list);
        req.setAttribute("list", list);

        RequestDispatcher dispatcher = req.getRequestDispatcher("project-list.jsp");
        dispatcher.forward(req,resp);

    }
    private void listOfDev(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        int id = Integer.parseInt(parseId(req));
        Map list = service.getOperationOnDB().getListOfDevelopers(id);
        System.out.println(list);
        req.setAttribute("list", list);
        req.setAttribute("text", "at project "+ id);

        RequestDispatcher dispatcher = req.getRequestDispatcher("developer-list.jsp");
        dispatcher.forward(req,resp);

    }

    private void listOfJavaDevelopers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map list = service.getOperationOnDB().getListOfJavaDev();
        System.out.println(list);
        req.setAttribute("list", list);
        req.setAttribute("text", "Java");

        RequestDispatcher dispatcher = req.getRequestDispatcher("developer-list.jsp");
        dispatcher.forward(req,resp);
    }

    private String parseId(HttpServletRequest req){
        if(req.getParameterMap().containsKey("id")){
            return req.getParameter("id");
        }
        return "1";
    }
}
