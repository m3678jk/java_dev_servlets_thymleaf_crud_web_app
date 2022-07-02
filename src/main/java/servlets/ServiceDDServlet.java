package servlets;

import model.ServiceDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/salary")
public class ServiceDDServlet extends HttpServlet {
    ServiceDB service;
    @Override
    public void init() throws ServletException {
        try {
            service = new ServiceDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("enter correct ID");
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        int id = Integer.parseInt(parseId(req));
        String result = "";
        result+=service.getOperationOnDB().getSumOfSalary(id);
        resp.getWriter().println("<html>salary: " + result+ "<html>");
        resp.getWriter().close();
    }

    private String parseId(HttpServletRequest req){
        if(req.getParameterMap().containsKey("id")){
            return req.getParameter("id");
        }
        return "1";
    }
}
