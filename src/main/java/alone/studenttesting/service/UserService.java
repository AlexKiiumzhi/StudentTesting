package alone.studenttesting.service;

import alone.studenttesting.entity.Question;
import alone.studenttesting.entity.Subject;
import alone.studenttesting.entity.User;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import alone.studenttesting.service.dto.Test.TestsWithSubjectDto;
import alone.studenttesting.service.dto.TestWithResultsDto;
import alone.studenttesting.service.dto.UserInfoDto;

import java.util.List;

public interface UserService  {

    TestsWithSubjectDto getAllTests(Integer pageNo, Integer pageSize, String sortBy);
    void registerUser(RegistrationDto registrationDto);
    List<TestWithSubjectDto> searchBySubject(Long subjectId);
    List<Subject> getAllSubjects();
    UserInfoDto getUserRegistrationInfo();
    List<TestWithResultsDto> getUserTests();
    List<Question> testSelection(Long id);
    User getLoggedInUser();
    void testPassing(TestPassingDto testPassingDto);


}
