package alone.studenttesting.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en", nullable = false)
    private String enFirstName;

    @Column(name = "name_ua", nullable = false)
    private String uaFirstName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="subject_id")
    private List<Test> tests;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getEnFirstName() {
        return enFirstName;
    }

    public void setEnFirstName(String enFirstName) {
        this.enFirstName = enFirstName;
    }

    public String getUaFirstName() {
        return uaFirstName;
    }

    public void setUaFirstName(String uaFirstName) {
        this.uaFirstName = uaFirstName;
    }

    public List<Test> getTests() { return tests; }

    public void setTests(List<Test> tests) { this.tests = tests; }
}
