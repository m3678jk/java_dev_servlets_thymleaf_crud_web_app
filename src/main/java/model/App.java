package model;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {

        ServiceDB service = new ServiceDB();


        //-- All commands on developers table:
//        System.out.println(service.getCommandsDevelopers().insertData(
//                new Developer("name1", "secondName2", 30, Developer.Sex.MALE, 3000)));
//        System.out.println(service.getCommandsDevelopers().selectData(6));
        System.out.println(service.getCommandsDevelopers().selectAllData("id"));
//        System.out.println(service.getCommandsDevelopers().delete(7));
//        System.out.println(service.getCommandsDevelopers().updateData(
//                6, new Developer("ch2", "ch", 36, Developer.Sex.MALE, 4000)));
//          System.out.println(service.getCommandsDevelopers().selectAllData("id"));


//        //--All operation on skills table
//        System.out.println(service.getCommandsSkills().insertData(
//                new Skills(5, Skills.Technology.C_SHARP, "middle")));
//        System.out.println(service.getCommandsSkills().selectData(4));
//        System.out.println(service.getCommandsSkills().selectAllData("id_skills"));
//        System.out.println(service.getCommandsSkills().delete(5));
//          System.out.println(service.getCommandsSkills().updateData(4,
//               new Skills(4, Skills.Technology.Java, "Middle")));

//        -- All operation with Projects table
//        System.out.println(service.getCommandsProject().selectAllData("id_project"));
//        System.out.println(service.getCommandsProject().selectData(5));
//        System.out.println(service.getCommandsProject().updateData(6,
//               new Project("warehouse","testUpdate", LocalDate.parse("2021-07-07"))));
//        System.out.println(service.getCommandsProject().delete(6));
//        System.out.println(service.getCommandsProject().selectAllData("id_project"));


//        // -- All commands on Companies table
//
//        System.out.println(service.getCommandsCompanies().insertData(new Company("ZF", "Lagenhagen")));
//        System.out.println(service.getCommandsCompanies().updateData(5, new Company("ZF DE", "Hanover")));
//        System.out.println(service.getCommandsCompanies().selectData(5));
//        System.out.println(service.getCommandsCompanies().delete(5));
//        System.out.println(service.getCommandsCompanies().selectAllData("id_company"));
//
//        -- All commands on Customers table

//        System.out.println(service.getCommandsCustomers().insertData(new Customer("ZF", "Lagenhagen")));
//        System.out.println(service.getCommandsCustomers().updateData(4, new Customer("ZF DE", "Hanover")));
//        System.out.println(service.getCommandsCustomers().selectData(1));
//        System.out.println(service.getCommandsCustomers().delete(1));
//        System.out.println(service.getCommandsCustomers().selectAllData("id_customer"));

        //        -- All commands on CompanyProject table
//        System.out.println(service.getCommandsCompanyProject().insertData(new CompanyProject(2, 4)));
//        System.out.println(service.getCommandsCompanyProject().updateData(5,new CompanyProject(2, 1)));
//        System.out.println(service.getCommandsCompanyProject().selectData(5));
//        System.out.println(service.getCommandsCompanyProject().selectAllData("id_com_pr"));
//        System.out.println(service.getCommandsCompanyProject().delete(5));

        // -- All commands on CustomerProject table
//        System.out.println(service.getCommandsCustomerProject().insertData(new CustomerProject(2, 4)));
//        System.out.println(service.getCommandsCustomerProject().updateData(5,new CustomerProject(2, 1)));
//        System.out.println(service.getCommandsCustomerProject().selectData(5));
//        System.out.println(service.getCommandsCustomerProject().selectAllData("id_cus_pr"));//
//        System.out.println(service.getCommandsCustomerProject().delete(5));

        // -- All commands on ProjectDeveloper table
//        System.out.println(service.getCommandsProjectDeveloper().insertData(new ProjectDeveloper(2, 6)));
//        System.out.println(service.getCommandsProjectDeveloper().updateData(4,new ProjectDeveloper(1, 1)));
//        System.out.println(service.getCommandsProjectDeveloper().selectData(5));
//        System.out.println(service.getCommandsProjectDeveloper().selectAllData("id_pr_dev"));//
//        System.out.println(service.getCommandsProjectDeveloper().delete(5));//



//      -- All nonCRUD commands

        System.out.println("---\n");
        System.out.println("Sum of salary:");
        System.out.println(service.getOperationOnDB().getSumOfSalary(3));
        System.out.println("---\n");
        System.out.println("List of developers:");
        System.out.println(service.getOperationOnDB().getListOfDevelopers(2));
        System.out.println("---\n");
        System.out.println("List of java developers:");
        System.out.println(service.getOperationOnDB().getListOfJavaDev());


        System.out.println("---\n");
        System.out.println("List of mid developers:");
        System.out.println(service.getOperationOnDB().getListMidDev());
        System.out.println("---\n");
        System.out.println("List of project and count of developers:");
        System.out.println(service.getOperationOnDB().getListOfProject());


    }
}
