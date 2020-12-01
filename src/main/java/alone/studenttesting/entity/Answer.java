package alone.studenttesting.entity;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer implements  IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_en", nullable = false)
    private String enAnswer;

    @Column(name = "answer_ua", nullable = false)
    private String uaAnswer;

    @Column
    private Boolean correctnessState;

    @Override
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEnAnswer() {
        return enAnswer;
    }

    public void setEnAnswer(String enAnswer) {
        this.enAnswer = enAnswer;
    }

    public String getUaAnswer() {
        return uaAnswer;
    }

    public void setUaAnswer(String uaAnswer) {
        this.uaAnswer = uaAnswer;
    }

    public Boolean getCorrectnessState() { return correctnessState; }

    public void setCorrectnessState(Boolean correctnessState) { this.correctnessState = correctnessState; }
}
