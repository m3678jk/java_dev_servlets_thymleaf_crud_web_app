package model.serviceDAO.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "skills")
public class Skills {
    @Id
    @Column(name = "id_skills")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private Technology technology;

    @Setter
    @Getter
    @Column(name = "level_of_position")
    private String levelOfPosition;

    @Setter
    @Getter
    @ManyToOne
    private Developer developer;

    public enum Technology {
        JAVA,
        JS,
        C_PLUS_PLUS,
        C_SHARP
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skills skills = (Skills) o;
        return technology == skills.technology && Objects.equals(levelOfPosition, skills.levelOfPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, technology, levelOfPosition);
    }

    @Override
    public String toString() {
        return "Skills{" +
                "id=" + id +
                ", technology=" + technology +
                ", levelOfPosition='" + levelOfPosition + '\'' +
                '}';
    }
}
