package softuni.workshop.data.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private Set<Project> projects;

    public Company(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
