package alone.studenttesting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test")
public class Test implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en",nullable = false)
    private String enName;

    @Column(name = "name_ua",nullable = false)
    private String uaName;

    @Column(nullable = false)
    private Long difficulty;

    @Column(name = "question_amount", nullable = false)
    private Integer questionAmount;

    @Column(name = "test_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime testDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="test_id")
    private List<Question> questions = new ArrayList<>();


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getUaName() {
        return uaName;
    }

    public void setUaName(String uaName) {
        this.uaName = uaName;
    }

    public Long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getQuestionAmount() { return questionAmount; }

    public void setQuestionAmount(Integer questionAmount) { this.questionAmount = questionAmount; }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }



    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
