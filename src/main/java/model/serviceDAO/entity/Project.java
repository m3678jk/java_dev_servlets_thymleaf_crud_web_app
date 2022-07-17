package model.serviceDAO.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "projects")
@Setter
@Entity
public class Project {
    @Id
    @Column(name = "id_project")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(name = "name_of_project")
    @Setter
    @Getter
    private String nameOfProject;

    @Column
    @Setter
    @Getter
    private String description;

    @Column(name = "start_date")
    @Setter
    @Getter
    private LocalDate date;

    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Developer> developers = new HashSet<>();

    public void addDeveloper(Developer developer) {
        developers.add(developer);
        developer.getProjects().add(this);
    }

    public void removeDeveloper(Developer developer) {
        developers.remove(developer);
        developer.getProjects().remove(this);
    }


    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Customer> customers = new HashSet<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.getProjects().add(this);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
        customer.getProjects().remove(this);
    }

    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Company> companies = new HashSet<>();

    public void addCompany(Company company) {
        companies.add(company);
        company.getProjects().add(this);
    }

    public void removeCompany(Company company) {
        companies.remove(company);
        company.getProjects().remove(this);
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", nameOfProject='" + nameOfProject + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", developers=" + developers +
                ", customers=" + customers +
                ", companies=" + companies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && Objects.equals(nameOfProject, project.nameOfProject) && Objects.equals(description, project.description) && Objects.equals(date, project.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameOfProject, description, date);
    }
}