package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class CommandsProject extends Commands {
    public static final String INSERT= "insert into projects (name_of_project, description, start_date) values  (?,?,?)";
    public static final String SELECT = "select * from projects where id_project =?";
    public static final String SELECT_ALL = "select * from projects";
    public static final String DELETE = "delete from projects where id_project = ?";
    public static final String UPDATE = "update projects set name_of_project = ?, description= ?, start_date =? where id_project = ?";

    public CommandsProject(DatabaseConnector databaseConnector,
                           String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        Project project = (Project) object;
        try {
            insertSt.setString(1, project.getNameOfProject());
            insertSt.setString(2, project.getDescription());
            insertSt.setString(3, project.getDate()); //TODO to fix local date implementation with thymeleaf

            return insertSt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Project selectData(int id) {
        try {
            selectSt.setLong(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = selectSt.executeQuery()){
            if( !resultSet.next()){
                System.out.println("incorrect id");
            }
            return new Project(resultSet.getString("name_of_project"),
                    resultSet.getString("description"),
                    resultSet.getString("start_date"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Project.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        Project project = (Project) object;
        if(!selectData(id).equals(Project.INCORRECT_QUERY)){
            try {
                updateSt.setString(1, project.getNameOfProject());
                updateSt.setString(2, project.getDescription());
                updateSt.setString(3, project.getDate());
                updateSt.setInt(4, id);

                return updateSt.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Incorrect id");
        return false;
    }
}
