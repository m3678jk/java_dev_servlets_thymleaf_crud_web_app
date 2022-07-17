package model.serviceDAO.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "company")
public class Company {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    @Column(name = "id_company")
    private long idCompany;
    @Setter
    @Getter
    @Column(name = "name_of_company")
    private String nameOfCompany;
    @Setter
    @Getter
    @Column
    private String address;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "companies")
    private Set<Project> projects = new HashSet<>();

    @Override
    public String toString() {
        return "Company{" +
                "idCompany=" + idCompany +
                ", nameOfCompany='" + nameOfCompany + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return idCompany == company.idCompany && Objects.equals(nameOfCompany, company.nameOfCompany) && Objects.equals(address, company.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompany, nameOfCompany, address);
    }
}
