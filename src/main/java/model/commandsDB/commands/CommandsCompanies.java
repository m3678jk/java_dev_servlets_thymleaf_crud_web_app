package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandsCompanies extends Commands {
    public static final String INSERT = "insert into companies (name_of_company, address) values  (?,?)";
    public static final String SELECT = "select * from companies where id_company =?";
    public static final String SELECT_ALL = "select * from companies";
    public static final String DELETE = "delete from companies where id_company = ?";
    public static final String UPDATE = "update companies set name_of_company = ?, address = ? where  id_company = ?";


    public CommandsCompanies(DatabaseConnector databaseConnector, String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        Company company = (Company) object;
        try {
            insertSt.setString(1, company.getName());
            insertSt.setString(2, company.getAddress());

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
            return new Company(resultSet.getString("name_of_company"),
                    resultSet.getString("address"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Company.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        Company company = (Company) object;
        if(!selectData(id).equals(Company.INCORRECT_QUERY)){
            try {
                updateSt.setString(1, company.getName());
                updateSt.setString(2, company.getAddress());
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
