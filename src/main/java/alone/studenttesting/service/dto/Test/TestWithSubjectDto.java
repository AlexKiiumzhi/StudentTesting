package alone.studenttesting.service.dto.Test;

import javax.validation.constraints.*;

public class TestWithSubjectDto {

    private String enNameTest;
    private String uaNameTest;
    private String enNameSubject;
    private String uaNameSubject;
    private Long difficulty;
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
