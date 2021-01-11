package alone.studenttesting.service;

import alone.studenttesting.entity.Answer;
import alone.studenttesting.entity.Test;
import alone.studenttesting.service.dto.*;
import alone.studenttesting.service.dto.AnswerEditDto;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;

import java.util.List;

public interface AdminService {
    List<Test> getAllTests();
    void blockUser(Long id);
    void unBlockUser(Long id);
    List<Answer> getAllAnswers();
    void createTest(TestCreationDto testCreationDto);
    void deleteTest(Long testID);
    void editTest(TestEditDto testEditDto);
    void createQuestion(QuestionCreationDto questionCreationDto);
    void editQuestion(QuestionEditDto questionEditDto);
    void createAnswer(AnswerCreationDto answerCreationDto);
    void editAnswer(AnswerEditDto AnswerEditDto);
    void editUser(UserEditDto userEditDto); }
