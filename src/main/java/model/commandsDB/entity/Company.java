package model.commandsDB.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Company {
    private String name;
    private String address;
    public static final Company INCORRECT_QUERY = new Company("error","error");
}
