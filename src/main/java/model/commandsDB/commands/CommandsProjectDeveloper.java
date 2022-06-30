package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.ProjectDeveloper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandsProjectDeveloper extends Commands {
    public static final String INSERT = "insert into project_developer (id_project,  id_developer) values  (?,?)";
    public static final String SELECT = "select * from project_developer where id_pr_dev =?";
    public static final String SELECT_ALL = "select * from project_developer";
    public static final String DELETE = "delete from project_developer where id_pr_dev = ?";
    public static final String UPDATE = "update project_developer set id_project = ?, id_developer = ? where  id_pr_dev = ?";

    public CommandsProjectDeveloper(DatabaseConnector databaseConnector, String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        ProjectDeveloper pd = (ProjectDeveloper) object;
        try {
            insertSt.setInt(1, pd.getProjectId());
            insertSt.setInt(2, pd.getDeveloperId());

            return insertSt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object selectData(int id) {
        try {
            selectSt.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = selectSt.executeQuery()) {
            if (!resultSet.next()) {
                System.out.println("incorrect id");
            }
            return new ProjectDeveloper(resultSet.getInt("id_project"),
                    resultSet.getInt("id_developer"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ProjectDeveloper.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        ProjectDeveloper pd = (ProjectDeveloper) object;
        if(!selectData(id).equals(ProjectDeveloper.INCORRECT_QUERY)){
            try {
                updateSt.setInt(1, pd.getProjectId());
                updateSt.setInt(2, pd.getDeveloperId());
                updateSt.setInt(3, id);

                return updateSt.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Incorrect id");
        return false;
    }
}
