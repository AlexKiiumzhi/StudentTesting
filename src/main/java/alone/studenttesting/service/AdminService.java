package alone.studenttesting.service;

import alone.studenttesting.service.dto.*;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;

public interface AdminService {
    void blockUser(Long id);
    void unblockUser(Long id);
    void createTest(TestCreationDto testCreationDto);
    void deleteTest(Long testID);
    void editTest(TestEditDto testEditDto);
    void createQuestion(QuestionCreationDto questionCreationDto);
    void editQuestion(QuestionEditDto questionEditDto);
    void createAnswer(AnswerCreationDto answerCreationDto);
    void editUser(UserEditDto userEditDto); }
