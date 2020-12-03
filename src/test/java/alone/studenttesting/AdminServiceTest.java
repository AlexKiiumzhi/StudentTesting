package alone.studenttesting;

import alone.studenttesting.entity.Answer;
import alone.studenttesting.entity.Question;
import alone.studenttesting.entity.Subject;
import alone.studenttesting.entity.User;
import alone.studenttesting.entity.enums.Role;
import alone.studenttesting.repository.*;
import alone.studenttesting.service.dto.AnswerCreationDto;
import alone.studenttesting.service.dto.QuestionCreationDto;
import alone.studenttesting.service.dto.QuestionEditDto;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;
import alone.studenttesting.service.dto.UserEditDto;
import alone.studenttesting.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class AdminServiceTest {

    @Mock
    private TestRepository testRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void blockUser_shall_block_a_user() {
        // given

        User user = new User();
        user.setId(1L);
        user.setEmail("alex@gmail.com");
        user.setPassword("alex123");
        user.setRole(Role.USER);
        user.setBlocked(Boolean.FALSE);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        // when
        adminService.blockUser(1L);
        User actualUser = userRepository.findById(1L).get();
        // then
        assertTrue(actualUser.getBlocked());

    }

    @Test
    public void unblockUser_shall_block_a_user() {
        // given

        User user = new User();
        user.setId(1L);
        user.setEmail("alex@gmail.com");
        user.setPassword("alex123");
        user.setRole(Role.USER);
        user.setBlocked(Boolean.TRUE);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        // when
        adminService.unBlockUser(1L);
        User actualUser = userRepository.findById(1L).get();
        // then
        assertFalse(actualUser.getBlocked());

    }

    @Test
    public void createTest_shall_create_a_test() {
        // given
        TestCreationDto expectedDto = new TestCreationDto();
        expectedDto.setSubjectID(2L);
        expectedDto.setEnName("Mathematics 1");
        expectedDto.setUaName("Математика 1");
        expectedDto.setDifficulty(7L);
        expectedDto.setQuestionAmount(12);

        Subject subject = new Subject();
        subject.setId(1L);

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(testRepository.findByEnName(anyString())).thenReturn(Optional.empty());
        when(testRepository.findByUaName(anyString())).thenReturn(Optional.empty());
        when(subjectRepository.save(any())).thenReturn(subject);

        // when
        adminService.createTest(expectedDto);
        List<alone.studenttesting.entity.Test> actualTests = subjectRepository.findById(subject.getId()).get().getTests();

        // then
        assertCreateTest(actualTests.get(actualTests.size() - 1), expectedDto);
    }

    @Test
    public void createQuestion_shall_create_a_question() {
        // given
        QuestionCreationDto expectedDto = new QuestionCreationDto();
        expectedDto.setTestId(1L);
        expectedDto.setEnText("Ukrainian Language and Literature 1");
        expectedDto.setUaText("Українська мова і література 1");

        alone.studenttesting.entity.Test test = new alone.studenttesting.entity.Test();
        test.setId(2L);

        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));
        when(questionRepository.findByEnText(anyString())).thenReturn(Optional.empty());
        when(questionRepository.findByUaText(anyString())).thenReturn(Optional.empty());
        when(testRepository.save(any())).thenReturn(test);

        // when
        adminService.createQuestion(expectedDto);
        List<Question> actualQuestions = testRepository.findById(test.getId()).get().getQuestions();

        // then
        assertCreateQuestion(actualQuestions.get(actualQuestions.size() - 1) , expectedDto);
    }

    @Test
    public void createAnswer_shall_create_an_answer() {
        // given
        AnswerCreationDto expectedDto = new AnswerCreationDto();
        expectedDto.setQuestionId(1L);
        expectedDto.setEnAnswer("answer 1");
        expectedDto.setUaAnswer("відповідь 1");

        Question question = new Question();
        question.setId(2L);

        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(answerRepository.findByEnAnswer(anyString())).thenReturn(Optional.empty());
        when(answerRepository.findByUaAnswer(anyString())).thenReturn(Optional.empty());
        when(questionRepository.save(any())).thenReturn(question);

        // when
        adminService.createAnswer(expectedDto);
        List<Answer> actualAnswers = questionRepository.findById(question.getId()).get().getAnswers();

        // then
        assertAnswer(actualAnswers.get(actualAnswers.size() - 1), expectedDto);
    }

    @Test
    public void deleteUser_shall_delete_a_user() {
        // given
        alone.studenttesting.entity.Test test = new alone.studenttesting.entity.Test();
        test.setId(1L);
        test.setEnName("Ukrainian Language and Literature 1");
        test.setEnName("Українська мова і література 1");
        test.setDifficulty(5L);
        test.setQuestionAmount(11);
        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));

        // when
        adminService.deleteTest(test.getId());
        Optional<alone.studenttesting.entity.Test> actualTest  = testRepository.findByEnName(test.getEnName());

        // then
        assertFalse(actualTest.isPresent());
    }

    @Test
    public void editTest_shall_update_a_test() {
        // given

        TestEditDto expectedDto = new TestEditDto();
        expectedDto.setId(1L);
        expectedDto.setEnName("Mathematics 1");
        expectedDto.setUaName("Математика 1");
        expectedDto.setDifficulty(7L);
        expectedDto.setQuestionAmount(12);

        alone.studenttesting.entity.Test test = new alone.studenttesting.entity.Test();
        test.setId(1L);
        test.setEnName("Mathemati");
        test.setUaName("Математ");
        test.setDifficulty(6L);
        test.setQuestionAmount(11);

        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));

        // when
        adminService.editTest(expectedDto);
        alone.studenttesting.entity.Test actualTest = testRepository.findById(test.getId()).get();

        // then
        assertEditTest(actualTest, expectedDto);
    }

    @Test
    public void editQuestion_shall_update_a_question() {
        // given

        Answer answer1 = new Answer();
        answer1.setId(1L);
        answer1.setUaAnswer("answer 11");
        answer1.setEnAnswer("відповідь 11");
        Answer answer2 = new Answer();
        answer1.setId(2L);
        answer1.setUaAnswer("answer 22");
        answer1.setEnAnswer("відповідь 22");

        List <Long> answerIds = new ArrayList<>();
        answerIds.add(1L);
        answerIds.add(2L);

        QuestionEditDto expectedDto = new QuestionEditDto();
        expectedDto.setQuestionId(1L);
        expectedDto.setEnText("text 1");
        expectedDto.setUaText("текст 1");
        expectedDto.setAnswerIds(answerIds);

        Question question = new Question();
        question.setId(1L);
        question.setEnText("text 1");
        question.setUaText("текст 1");

        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer1));
        when(answerRepository.findById(2L)).thenReturn(Optional.of(answer2));
        when(questionRepository.save(any())).thenReturn(question);

        // when
        adminService.editQuestion(expectedDto);
        Question actualQuestion = questionRepository.findById(question.getId()).get();

        // then
        assertEditQuestion(actualQuestion, expectedDto);
    }

    @Test
    public void editUser_shall_update_a_user() {
        // given

        alone.studenttesting.entity.Test test1 = new alone.studenttesting.entity.Test();
        test1.setId(1L);
        test1.setEnName("Mathemati 11");
        test1.setUaName("Математ 11");
        test1.setDifficulty(6L);
        test1.setQuestionAmount(11);
        alone.studenttesting.entity.Test test2 = new alone.studenttesting.entity.Test();
        test2.setId(2L);
        test2.setEnName("Mathemati 22");
        test2.setUaName("Математ 22");
        test2.setDifficulty(6L);
        test2.setQuestionAmount(11);

        List <Long> testIds = new ArrayList<>();
        testIds.add(1L);
        testIds.add(2L);

        UserEditDto expectedDto = new UserEditDto();
        expectedDto.setId(1L);
        expectedDto.setEmail("alex@gmail.com");
        expectedDto.setPassword("alex123");
        expectedDto.setTestIds(testIds);

        User user = new User();
        user.setId(1L);
        user.setEmail("alex1234@gmail.com");
        user.setPassword("alex2343");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(Optional.of(test1));
        when(testRepository.findById(2L)).thenReturn(Optional.of(test2));
        when(userRepository.save(any())).thenReturn(user);

        // when
        adminService.editUser(expectedDto);
        User actualUser = userRepository.findById(user.getId()).get();

        // then
        assertEditUser(actualUser , expectedDto);
    }

    private void assertCreateTest(alone.studenttesting.entity.Test test, TestCreationDto expectedDto){
        assertEquals(test.getEnName(), expectedDto.getEnName());
        assertEquals(test.getUaName(), expectedDto.getUaName());
        assertEquals(test.getDifficulty(), expectedDto.getDifficulty());
        assertEquals(test.getQuestionAmount(), expectedDto.getQuestionAmount());
    }

    private void assertEditTest(alone.studenttesting.entity.Test test, TestEditDto expectedDto){
        assertEquals(test.getId(), expectedDto.getId());
        assertEquals(test.getEnName(), expectedDto.getEnName());
        assertEquals(test.getUaName(), expectedDto.getUaName());
        assertEquals(test.getDifficulty(), expectedDto.getDifficulty());
        assertEquals(test.getQuestionAmount(), expectedDto.getQuestionAmount());
    }

    private void assertCreateQuestion(Question question, QuestionCreationDto expectedDto){
        assertEquals(question.getEnText(),expectedDto.getEnText());
        assertEquals(question.getUaText(),expectedDto.getUaText());
            }

    private void assertEditQuestion(Question question, QuestionEditDto expectedDto){
        assertEquals(question.getId(),expectedDto.getQuestionId());
        assertEquals(question.getEnText(),expectedDto.getEnText());
        assertEquals(question.getUaText(),expectedDto.getUaText());
    }

    private void assertAnswer(Answer answer, AnswerCreationDto expectedDto){
        assertEquals(answer.getEnAnswer(), expectedDto.getEnAnswer());
        assertEquals(answer.getUaAnswer(), expectedDto.getUaAnswer());
    }

    private void assertEditUser(User user, UserEditDto userEditDto){
        assertEquals(user.getId(), userEditDto.getId());
        assertEquals(user.getPassword(), userEditDto.getPassword());
        assertEquals(user.getEmail(),userEditDto.getEmail());
    }
}
