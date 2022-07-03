package model.commandsDB;

import model.DatabaseConnector;
import model.commandsDB.entity.Developer;
import model.commandsDB.entity.ProjectList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OperationOnDB {
    protected PreparedStatement getSumOfSalarySt;
    protected PreparedStatement getListOfDevelopersSt;
    protected PreparedStatement getListOfJavaDevSt;
    protected PreparedStatement getListOfMidDevSt;
    protected PreparedStatement getListOfProjectSt;

    public OperationOnDB(DatabaseConnector databaseConnector) throws SQLException {
        Connection connection = databaseConnector.getConnection();
        getSumOfSalarySt = connection.prepareStatement(GET_SUM_OF_SALARY_BY_ID_PROJECT_QUERY);
        getListOfDevelopersSt = connection.prepareStatement(GET_LIST_OF_DEV_BY_ID_PROJECT);
        getListOfJavaDevSt = connection.prepareStatement(GET_LIST_OF_JAVA_DEV);
        getListOfMidDevSt = connection.prepareStatement(GET_LIST_OF_MID_DEV);
        getListOfProjectSt = connection.prepareStatement((GET_LIST_OF_PROJECT_DATE_NAME_QTY_OF_DEV_FORMAT));
    }

    public int getSumOfSalary(int projectId) {
        int result = 0;
        try {
            getSumOfSalarySt.setLong(1, projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = getSumOfSalarySt.executeQuery()) {
            if (!resultSet.next()) {
                System.out.println("incorrect id");
            }
            result = resultSet.getInt("sum_of_salary");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Map<Long, Developer> getListOfDevelopers(int projectId) {
        Map<Long, Developer> result = new HashMap<>();
        try {
            getListOfDevelopersSt.setLong(1, projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = getListOfDevelopersSt.executeQuery()) {
            while (resultSet.next()) {
                long key = resultSet.getLong("id_dev");
                Developer dev = new Developer(resultSet.getString("f_name"), resultSet.getString("s_name"),
                        resultSet.getInt("age"),
                        Developer.Sex.valueOf(resultSet.getString("sex").toUpperCase(Locale.ROOT)),
                        resultSet.getInt("salary"));
                result.put(key, dev);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Map<Long, Developer> getListOfJavaDev() {
        Map<Long, Developer> result = new HashMap<>();
        try (ResultSet resultSet = getListOfJavaDevSt.executeQuery()) {
            while (resultSet.next()) {
                long key = resultSet.getLong("id_dev");
                Developer dev = new Developer(resultSet.getString("f_name"), resultSet.getString("s_name"),
                        resultSet.getInt("age"),
                        Developer.Sex.valueOf(resultSet.getString("sex").toUpperCase(Locale.ROOT)),
                        resultSet.getInt("salary"));
                result.put(key, dev);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Map<Long, Developer> getListMidDev() {
        Map<Long, Developer> result = new HashMap<>();
        try (ResultSet resultSet = getListOfMidDevSt.executeQuery()) {
            while (resultSet.next()) {
                long key = resultSet.getLong("id_dev");
                Developer dev = new Developer(resultSet.getString("f_name"), resultSet.getString("s_name"),
                        resultSet.getInt("age"),
                        Developer.Sex.valueOf(resultSet.getString("sex").toUpperCase(Locale.ROOT)),
                        resultSet.getInt("salary"));
                result.put(key, dev);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<ProjectList> getListOfProject() {
        List<ProjectList> result = new ArrayList<>();
        try (ResultSet resultSet = getListOfProjectSt.executeQuery()) {
            while (resultSet.next()) {
                result.add(new ProjectList(resultSet.getString("st_d"),
                        resultSet.getString("name_pr"),
                        resultSet.getString("total_dev")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static final String GET_SUM_OF_SALARY_BY_ID_PROJECT_QUERY = "select project_developer.id_project as id_pr, sum(developers.salary) as sum_of_salary\n" +
            "\tfrom developers \n" +
            "\tjoin project_developer on developers.id = project_developer.id_developer \n" +
            "\tgroup by id_pr\n" +
            "\thaving id_pr =?\n";

    public static final String GET_LIST_OF_DEV_BY_ID_PROJECT = "select project_developer.id_project as id_pr, developers.id as id_dev,\n" +
            "developers.firstName as f_name,  developers.secondName as s_name, \n" +
            "developers.age as age, developers.sex as sex, developers.salary as salary\n" +
            "from developers \n" +
            "join project_developer on developers.id = project_developer.id_developer \n" +
            "having id_pr = ?\n";

    public static final String GET_LIST_OF_JAVA_DEV = "select developers.id as id_dev, skills.technology as tech,\n" +
            " developers.firstName as f_name,  developers.secondName as s_name, developers.age as age, \n" +
            " developers.sex as sex, developers.salary as salary\n" +
            " from developers\n" +
            " join skills on developers.id = skills.id_developer\n" +
            " having tech = \"java\";";
    public static final String GET_LIST_OF_MID_DEV = "select distinct developers.id as id_dev, skills.levelOfPosition as lev,\n" +
            "developers.firstName as f_name,  developers.secondName as s_name, \n" +
            "developers.age as age, developers.sex as sex, developers.salary as salary\n" +
            "from developers \n" +
            "join skills on developers.id = skills.id_developer \n" +
            "having lev = \"middle\"";


    public static final String GET_LIST_OF_PROJECT_DATE_NAME_QTY_OF_DEV_FORMAT = "select projects.start_date as st_d, projects.name_of_project as name_pr, projects.id_project as id_pr,\n" +
            " count(*) as total_dev\n" +
            "\tfrom projects \n" +
            "\tjoin project_developer on projects.id_project = project_developer.id_project\n" +
            "\tgroup by id_pr\n";
}
