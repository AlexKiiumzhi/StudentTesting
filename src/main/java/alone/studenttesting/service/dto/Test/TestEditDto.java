package alone.studenttesting.service.dto.Test;

import alone.studenttesting.entity.Question;

import java.time.LocalDateTime;
import java.util.List;

public class TestEditDto {

    private Long testId;
    private String enName;
    private String uaName;
    private Long difficulty;
    private Integer questionAmount;
    private LocalDateTime testDate;

    public Long getId() { return testId; }

    public void setId(Long id) { this.testId = id; }

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

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }

}
