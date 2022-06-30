package model.commandsDB.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompanyProject {
    private int companyId;
    private int projectId;
    public final static CompanyProject INCORRECT_QUERY = new CompanyProject(0, 0);

}
