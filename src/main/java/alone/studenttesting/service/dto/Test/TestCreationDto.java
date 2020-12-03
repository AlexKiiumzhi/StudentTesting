package alone.studenttesting.service.dto.Test;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class TestCreationDto {

    @NotNull(message = "subject id must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=50, message="must be equal or less than 50")
    private Long subjectID;
    @Pattern(regexp="[A-Z0-9a-z]{3,100}",message="length must be from 3 to 100")
    @NotEmpty(message = "english test name must not be empty")
    private String enName;
    @Pattern(regexp="[A-Z0-9a-z]{3,100}",message="length must be from 3 to 100")
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

    public Long getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Long subjectID) {
        this.subjectID = subjectID;
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

}
