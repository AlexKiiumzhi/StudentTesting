package alone.studenttesting.service.dto.Test;

import java.util.ArrayList;
import java.util.List;

public class TestsWithSubjectDto {

    private List<TestWithSubjectDto> testWithSubjectDtos = new ArrayList<>();
    private Integer totalPages;

    public List<TestWithSubjectDto> getTestWithSubjectDtos() {
        return testWithSubjectDtos;
    }

    public void setTestWithSubjectDtos(List<TestWithSubjectDto> testWithSubjectDtos) {
        this.testWithSubjectDtos = testWithSubjectDtos;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
