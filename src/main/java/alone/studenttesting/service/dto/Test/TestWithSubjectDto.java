package alone.studenttesting.service.dto.Test;

import javax.validation.constraints.*;

public class TestWithSubjectDto {

    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "english test name must not be empty")
    private String enNameTest;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "ukrainian test name must not be empty")
    private String uaNameTest;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "ukrainian subject name must not be empty")
    private String enNameSubject;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "english subject name must not be empty")
    private String uaNameSubject;
    @NotNull(message = "difficulty scale must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=10, message="must be equal or less than 10")
    private Long difficulty;
    @NotNull(message = "question amount must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=20, message="must be equal or less than 20")
    private Integer questionAmount;

    public String getEnNameTest() {
        return enNameTest;
    }

    public void setEnNameTest(String enNameTest) {
        this.enNameTest = enNameTest;
    }

    public String getUaNameTest() {
        return uaNameTest;
    }

    public void setUaNameTest(String uaNameTest) {
        this.uaNameTest = uaNameTest;
    }

    public String getEnNameSubject() {
        return enNameSubject;
    }

    public void setEnNameSubject(String enNameSubject) {
        this.enNameSubject = enNameSubject;
    }

    public String getUaNameSubject() {
        return uaNameSubject;
    }

    public void setUaNameSubject(String uaNameSubject) {
        this.uaNameSubject = uaNameSubject;
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
}
