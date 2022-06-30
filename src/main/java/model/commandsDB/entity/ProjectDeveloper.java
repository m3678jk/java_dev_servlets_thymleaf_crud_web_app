package model.commandsDB.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProjectDeveloper {
    private int projectId;
    private int developerId;

    public final static ProjectDeveloper INCORRECT_QUERY = new ProjectDeveloper(0,0);

}
