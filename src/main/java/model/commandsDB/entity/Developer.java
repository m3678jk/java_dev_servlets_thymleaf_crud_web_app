package model.commandsDB.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Developer {

    private String firstName;
    private String secondName;
    private int age;
    private Sex sex;
    private int salary;

    public static final Developer INCORRECT_QUERY = new Developer("error", "error", 0, Sex.UNKNOWN, 0);

    public enum Sex {
        MALE,
        FEMALE,
        UNKNOWN

    }
}