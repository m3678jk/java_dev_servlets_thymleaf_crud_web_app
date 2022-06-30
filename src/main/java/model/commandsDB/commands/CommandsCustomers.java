package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.Company;
import model.commandsDB.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandsCustomers extends Commands {
    public static final String INSERT = "insert into customers (name_of_customer, address) values  (?,?)";
    public static final String SELECT = "select * from customers where id_customer =?";
    public static final String SELECT_ALL = "select * from customers";
    public static final String DELETE = "delete from customers where id_customer = ?";
    public static final String UPDATE = "update customers set name_of_customer = ?, address = ? where  id_customer = ?";


    public CommandsCustomers(DatabaseConnector databaseConnector, String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        Customer customer = (Customer) object;
        try {
            insertSt.setString(1, customer.getName());
            insertSt.setString(2, customer.getAddress());

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
            return new Company(resultSet.getString("name_of_customer"),
                    resultSet.getString("address"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Customer.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        Customer customer = (Customer) object;
        if(!selectData(id).equals(Customer.INCORRECT_QUERY)){
            try {
                updateSt.setString(1, customer.getName());
                updateSt.setString(2, customer.getAddress());
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
