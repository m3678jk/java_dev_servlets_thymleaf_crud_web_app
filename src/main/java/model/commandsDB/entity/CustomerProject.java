package model.commandsDB.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerProject {
    private int customerId;
    private int projectId;
    public final static CustomerProject INCORRECT_QUERY = new CustomerProject(0, 0);

}
