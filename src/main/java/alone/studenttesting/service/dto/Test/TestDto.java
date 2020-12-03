package alone.studenttesting.service.dto.Test;

import alone.studenttesting.entity.Question;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TestDto {
    @Id
    @NotNull(message = "test id must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=50, message="must be equal or less than 50")
    private Long id;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "english test name must not be empty")
    private String enName;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "ukrainian test name must not be empty")
    private String uaName;
    @NotNull(message = "difficulty scale must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=10, message="must be equal or less than 10")
    private Long difficulty;
    @NotNull(message = "question amount must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=20, message="must be equal or less than 20")
    private Integer questionAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime testDate;
    private List<Question> questions;

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
