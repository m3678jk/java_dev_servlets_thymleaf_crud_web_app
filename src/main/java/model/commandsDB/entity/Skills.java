package model.commandsDB.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Skills {
    private int idDev;
    private Technology technology;
    private String levelOfPosition;

    public final static Skills INCORRECT_QUERY = new Skills(
            0, Technology.C_SHARP,"error");

    public enum Technology{
        Java,
        JS,
        C_PLUS_PLUS,
        C_SHARP


    }
}
