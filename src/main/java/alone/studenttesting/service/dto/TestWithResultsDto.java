package alone.studenttesting.service.dto;


public class TestWithResultsDto {

    private Long testId;
    private String testEnName;
    private String testUaName;
    private String subjectEnName;
    private String subjectUaName;
    private Integer mark;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestEnName() {
        return testEnName;
    }

    public void setTestEnName(String testEnName) {
        this.testEnName = testEnName;
    }

    public String getTestUaName() {
        return testUaName;
    }

    public void setTestUaName(String testUaName) {
        this.testUaName = testUaName;
    }

    public String getSubjectEnName() {
        return subjectEnName;
    }

    public void setSubjectEnName(String subjectEnName) {
        this.subjectEnName = subjectEnName;
    }

    public String getSubjectUaName() {
        return subjectUaName;
    }

    public void setSubjectUaName(String subjectUaName) {
        this.subjectUaName = subjectUaName;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
