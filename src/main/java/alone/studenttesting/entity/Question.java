package alone.studenttesting.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_en", nullable = false)
    private String enText;

    @Column(name = "text_ua", nullable = false)
    private String uaText;

    @Column
    private Boolean pass;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="question_id")
    private List<Answer> answers = new ArrayList<>();

    @Override
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEnText() {
        return enText;
    }

    public void setEnText(String enText) {
        this.enText = enText;
    }

    public String getUaText() {
        return uaText;
    }

    public void setUaText(String uaText) {
        this.uaText = uaText;
    }

    public Boolean getPass() { return pass; }

    public void setPass(Boolean pass) { this.pass = pass; }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
