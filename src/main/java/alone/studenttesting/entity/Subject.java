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
    private String enName;

    @Column(name = "name_ua", nullable = false)
    private String uaName;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="subject_id")
    private List<Test> tests = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getUaName() {
        return uaName;
    }

    public void setUaName(String uaFirstName) {
        this.uaName = uaFirstName;
    }

    public List<Test> getTests() { return tests; }

    public void setTests(List<Test> tests) { this.tests = tests; }
}
