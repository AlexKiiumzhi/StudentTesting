package alone.studenttesting.service.dto;

import javax.validation.constraints.*;

public class AnswerEditDto {

    @NotNull
    @Min(value=1)
    private Long answerId;
    @Pattern(regexp="[A-Z0-9a-z]{3,100}")
    @NotEmpty
    private String enAnswer;
    @Pattern(regexp="[А-ЩЬЮЯҐЄІЇ0-9а-щьюяґєії]{3,100}")
    @NotEmpty
    private String uaAnswer;
    @NotNull
    private Boolean correctnessState;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
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
