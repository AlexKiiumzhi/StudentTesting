package alone.studenttesting.service.dto.Test;

import alone.studenttesting.entity.Answer;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TestPassingDto {
    @NotNull(message = "test id must not be null")
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=50, message="must be equal or less than 50")
    private Long testId;
    private List<List<Long>> answerIds;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public List<List<Long>> getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(List<List<Long>> answerIds) {
        this.answerIds = answerIds;
    }
}
