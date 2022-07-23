package model.serviceDAO.manager;

import lombok.Getter;
import model.serviceDAO.dao.*;

public class DatabaseManager {

    @Getter
    private RelationManager relationManager;
    @Getter
    private OperationOnDB operationOnDB;
    @Getter
    private ProjectDAO projectDAO;
    @Getter
    private DeveloperDAO developerDAO;
    @Getter
    private CustomerDAO customerDAO;
    @Getter
    private CompanyDAO companyDAO;
    @Getter
    private SkillsDAO skillsDAO;

    public DatabaseManager() {
        relationManager = new RelationManager();
        operationOnDB = new OperationOnDB();
        projectDAO = new ProjectDAO();
        developerDAO = new DeveloperDAO();
        customerDAO = new CustomerDAO();
        companyDAO = new CompanyDAO();
        skillsDAO = new SkillsDAO();
    }
}
