package alone.studenttesting.service.dto;

import alone.studenttesting.entity.Answer;

import java.util.List;

public class QuestionCreationDto {

    private Long testId;
    private String enText;
    private String uaText;


    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

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
}
