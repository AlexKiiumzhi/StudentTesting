package alone.studenttesting.service.dto.Test;

import alone.studenttesting.entity.Question;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

public class TestEditDto {
    @NotNull
    @Min(value=1)
    private Long testId;
    @Pattern(regexp="[A-Z0-9a-z]{3,100}")
    @NotEmpty
    private String enName;
    @Pattern(regexp="[А-ЩЬЮЯҐЄІЇ0-9а-щьюяґєії]{3,100}")
    @NotEmpty
    private String uaName;
    @NotNull
    @Min(value=1)
    @Max(value=10)
    private Long difficulty;
    @NotNull
    private Integer questionAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String testDate;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
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

    public String  getTestDate() {
        return testDate;
    }

    public void setTestDate(String  testDate) {
        this.testDate = testDate;
    }

}
