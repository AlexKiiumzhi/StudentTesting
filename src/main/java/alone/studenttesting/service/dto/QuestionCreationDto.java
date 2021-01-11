package alone.studenttesting.service.dto;

import alone.studenttesting.entity.Answer;

import javax.validation.constraints.*;
import java.util.List;

public class QuestionCreationDto {

    @NotNull
    @Min(value=1)
    private Long testId;
    @Pattern(regexp="[A-Z0-9a-z]{3,100}")
    @NotEmpty
    private String enText;
    @Pattern(regexp="[А-ЩЬЮЯҐЄІЇ0-9а-щьюяґєії]{3,100}")
    @NotEmpty
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
