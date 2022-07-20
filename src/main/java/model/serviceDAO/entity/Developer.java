package model.serviceDAO.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Table(name = "developer")
@Entity
public class Developer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private long id;

    @Column(name = "first_name")
    @Setter
    @Getter
    private String firstName;

    @Column(name = "second_name")
    @Setter
    @Getter
    private String secondName;

    @Setter
    @Getter
    private int age;

    @Enumerated(EnumType.STRING)
    @Setter
    @Getter
    private Sex sex;

    @Setter
    @Getter
    private int salary;


    public enum Sex {
        MALE,
        FEMALE,
        UNKNOWN
    }

    @Getter
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "developers")
    private Set<Project> projects = new HashSet<>();


    @Getter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "developer", cascade = {CascadeType.MERGE, CascadeType.PERSIST} , orphanRemoval = true)
    private Set<Skills> skills = new HashSet<>();


    public void addSkills(Skills skill) {
        skills.add(skill);
        skill.setDeveloper(this);
    }

    public void removeSkills(Skills skill) {
        skills.remove(skill);
        skill.setDeveloper(null);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id && age == developer.age && Objects.equals(firstName, developer.firstName) && Objects.equals(secondName, developer.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, age);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", salary=" + salary +
                '}';
    }
}