package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.CustomerProject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandsCustomerProject extends Commands {
    public static final String INSERT = "insert into customer_project (id_customer,  id_project) values  (?,?)";
    public static final String SELECT = "select * from customer_project where id_cus_pr =?";
    public static final String SELECT_ALL = "select * from customer_project";
    public static final String DELETE = "delete from customer_project where id_cus_pr = ?";
    public static final String UPDATE = "update customer_project set id_customer = ?, id_project = ? where  id_cus_pr = ?";

    public CommandsCustomerProject(DatabaseConnector databaseConnector, String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        CustomerProject pd = (CustomerProject) object;
        try {
            insertSt.setInt(1, pd.getCustomerId());
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
            return new CustomerProject(resultSet.getInt("id_customer"),
                    resultSet.getInt("id_project"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CustomerProject.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        CustomerProject pd = (CustomerProject) object;
        if(!selectData(id).equals(CustomerProject.INCORRECT_QUERY)){
            try {
                updateSt.setInt(1, pd.getCustomerId());
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
