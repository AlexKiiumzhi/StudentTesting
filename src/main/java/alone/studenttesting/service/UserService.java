package alone.studenttesting.service;

import alone.studenttesting.entity.Question;
import alone.studenttesting.entity.Test;
import alone.studenttesting.entity.User;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;

import java.util.List;

public interface UserService  {
    String getUserRole(String email);
    List<TestDto> getAllTests(Integer pageNo, Integer pageSize, String sortBy);
    Long registerUser(RegistrationDto registrationDto);
    List<TestWithSubjectDto> getAllTestsWithSubjects();
    List<Test> testSelection(Long id);
    User getLoggedInUser();
    Integer testPassing(TestPassingDto testPassingDto);
    List<Question> testPreparing(Long id);

}
