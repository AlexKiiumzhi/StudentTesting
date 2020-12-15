package alone.studenttesting.service.dto;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionEditDto {

    @NotNull(message = "question id must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=50, message="must be equal or less than 50")
    private Long questionId;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "english question name must not be empty")
    private String enText;
    @Pattern(regexp="[A-Z0-9a-z]{3,20}",message="length must be from 3 to 20")
    @NotEmpty(message = "ukrainian question name must not be empty")
    private String uaText;
    private List<Long> answerIds ;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public List<Long> getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(List<Long> answerIds) {
        this.answerIds = answerIds;
    }
}
