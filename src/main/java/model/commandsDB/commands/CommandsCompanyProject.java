package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.CompanyProject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandsCompanyProject extends Commands {
    public static final String INSERT = "insert into company_project (id_company,  id_project) values  (?,?)";
    public static final String SELECT = "select * from company_project where id_com_pr =?";
    public static final String SELECT_ALL = "select * from company_project";
    public static final String DELETE = "delete from company_project where id_com_pr = ?";
    public static final String UPDATE = "update company_project set id_company = ?, id_project = ? where  id_com_pr = ?";

    public CommandsCompanyProject(DatabaseConnector databaseConnector, String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        CompanyProject pd = (CompanyProject) object;
        try {
            insertSt.setInt(1, pd.getCompanyId());
            insertSt.setInt(2, pd.getProjectId());

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
            return new CompanyProject(resultSet.getInt("id_company"),
                    resultSet.getInt("id_project"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CompanyProject.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        CompanyProject pd = (CompanyProject) object;
        if(!selectData(id).equals(CompanyProject.INCORRECT_QUERY)){
            try {
                updateSt.setInt(1, pd.getCompanyId());
                updateSt.setInt(2, pd.getProjectId());
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
