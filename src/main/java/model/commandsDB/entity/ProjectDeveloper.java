package model.commandsDB.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProjectDeveloper {
    private int projectId;
    private int developerId;
//TODO to find way how to avoid additional id in such tables. how to work with pair of primary key
    public final static ProjectDeveloper INCORRECT_QUERY = new ProjectDeveloper(0,0);

}
