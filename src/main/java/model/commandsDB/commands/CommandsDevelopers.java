package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.Developer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class CommandsDevelopers extends Commands {
    public static final String INSERT = "insert into developers (firstName, secondName, age, sex, salary) values  (?,?,?,?,?)";
    public static final String SELECT = "select * from developers where id =?";
    public static final String SELECT_ALL = "select * from developers";
    public static final String DELETE = "delete from developers where id = ?";
    public static final String UPDATE = "update developers set firstName = ?,secondName= ?, age =?, sex=?, salary=? where id = ?";

    public CommandsDevelopers(DatabaseConnector databaseConnector, String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        Developer dev = (Developer) object;
        try {
            insertSt.setString(1,dev.getFirstName());
            insertSt.setString(2,dev.getSecondName());
            insertSt.setInt(3,dev.getAge());
            insertSt.setString(4, dev.getSex().name());
            insertSt.setInt(5,dev.getSalary());

            return insertSt.executeUpdate()==1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object selectData(int id) {
        try {
            selectSt.setLong(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet resultSet = selectSt.executeQuery()){
            if( !resultSet.next()){
                System.out.println("incorrect id");
            }
            return new Developer(resultSet.getString("firstName"),
                    resultSet.getString("secondName"),
                    resultSet.getInt("age"),
                    Developer.Sex.valueOf(resultSet.getString("sex").toUpperCase(Locale.ROOT)),
                    resultSet.getInt("salary"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Developer.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        Developer dev = (Developer) object;
        if(!selectData(id).equals(Developer.INCORRECT_QUERY)){
            try {
                updateSt.setString(1, dev.getFirstName());
                updateSt.setString(2, dev.getSecondName());
                updateSt.setInt(3, dev.getAge());
                updateSt.setString(4, dev.getSex().name());
                updateSt.setInt(5,dev.getSalary());
                updateSt.setInt(6, id);

                return updateSt.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Incorrect id");
        return false;
    }
}



