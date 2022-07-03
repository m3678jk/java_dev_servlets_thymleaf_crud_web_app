package model.commandsDB.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@AllArgsConstructor
@Data
public class Project  {
    private String nameOfProject;
    private String description;
    private String date; //TODO to fix local date implementation with thymeleaf

    public final static Project INCORRECT_QUERY = new Project("error", "error", LocalDate.now().toString());

}
