package alone.studenttesting.service.dto;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class AnswerCreationDto {
    @NotNull(message = "question id must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=50, message="must be equal or less than 50")
    private Long questionId;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "ukrainian answer name must not be empty")
    private String enAnswer;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "english answer name must not be empty")
    private String uaAnswer;
    private Boolean correctnessState;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

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

    public Boolean getCorrectnessState() {
        return correctnessState;
    }

    public void setCorrectnessState(Boolean correctnessState) {
        this.correctnessState = correctnessState;
    }
}
