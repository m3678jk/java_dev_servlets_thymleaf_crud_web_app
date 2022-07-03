package model.commandsDB.commands;

import model.DatabaseConnector;
import model.commandsDB.entity.Skills;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandsSkills extends Commands {

    public static final String INSERT =
            "insert into skills (id_developer, technology, levelOfPosition) values  (?,?,?)";
    public static final String SELECT = "select * from skills where id_skills =?";
    public static final String SELECT_ALL = "select * from skills";
    public static final String DELETE = "delete from skills where id_skills = ?";
    public static final String UPDATE =
            "update skills set id_developer = ?,technology=?,levelOfPosition=? where id_skills = ?";

    public CommandsSkills(DatabaseConnector databaseConnector,
                          String insert, String select, String selectAll, String delete, String update) throws SQLException {
        super(databaseConnector, insert, select, selectAll, delete, update);
    }

    @Override
    public boolean insertData(Object object) {
        Skills skills = (Skills) object;
        try {
            insertSt.setInt(1, skills.getIdDev());
            insertSt.setString(2, skills.getTechnology().name());
            insertSt.setString(3, skills.getLevelOfPosition());

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
            return new Skills(resultSet.getInt("id_developer"),
                    Skills.Technology.valueOf(resultSet.getString("technology")),
                    resultSet.getString("levelOfPosition"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Skills.INCORRECT_QUERY;
    }

    @Override
    public boolean updateData(int id, Object object) {
        Skills skills = (Skills) object;
        if(!selectData(id).equals(Skills.INCORRECT_QUERY)){
            try {
                updateSt.setInt(1, skills.getIdDev());
                updateSt.setString(2, skills.getTechnology().name());
                updateSt.setString(3, skills.getLevelOfPosition());
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
