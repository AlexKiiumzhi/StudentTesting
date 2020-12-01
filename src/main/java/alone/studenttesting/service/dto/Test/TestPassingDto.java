package alone.studenttesting.service.dto.Test;

import alone.studenttesting.entity.Answer;

import java.util.List;

public class TestPassingDto {
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
