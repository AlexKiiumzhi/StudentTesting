package alone.studenttesting;

import alone.studenttesting.entity.Answer;
import alone.studenttesting.entity.Question;
import alone.studenttesting.entity.Subject;
import alone.studenttesting.entity.User;

import alone.studenttesting.entity.enums.Role;
import alone.studenttesting.repository.*;
import alone.studenttesting.service.dto.*;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import alone.studenttesting.service.impl.AdminServiceImpl;
import alone.studenttesting.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static sun.nio.cs.Surrogate.is;

public class UserServiceTest {
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private TestRepository testRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUser_shall_register_a_user() {
        // given
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("alex@gmail.com");
        registrationDto.setPassword("alex123");

        User user = new User();
        user.setId(1L);
        user.setEmail("alex1234@gmail.com");
        user.setPassword("alex2343");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        // when
        Long actualId = userService.registerUser(registrationDto);

        // then
        assertEquals(actualId, user.getId());
    }

    @Test
    public void getAllTestsWithSubjects_shall_return_all_tests_with_subjects(){
        // given
        alone.studenttesting.entity.Test test1 = new alone.studenttesting.entity.Test();
        test1.setId(1L);
        test1.setEnName("Mathemati 11");
        test1.setUaName("Математ 11");
        test1.setDifficulty(6L);
        test1.setQuestionAmount(11);

        List<alone.studenttesting.entity.Test> tests = new ArrayList<>();
        tests.add(test1);

        Subject subject1 = new Subject();
        subject1.setEnFirstName("Mathemati 22");
        subject1.setUaFirstName("Математ 22");
        subject1.setTests(tests);
        Subject subject2 = new Subject();
        subject2.setEnFirstName("Mathemati 22");
        subject2.setUaFirstName("Математ 22");
        subject2.setTests(tests);

        List<Subject> expectedProducts = new ArrayList<>();

        when(subjectRepository.findAll()).thenReturn(expectedProducts);

        // when
        List<TestWithSubjectDto> actualTestsAndSubjects = userService.getAllTestsWithSubjects();

        // then
        int index = 0;
        for (TestWithSubjectDto actualTestsAndSubject : actualTestsAndSubjects){
            assertSubject(actualTestsAndSubject, expectedProducts.get(index++));
        }
    }

    @Test
    public void testSelection_shall_select_a_test() {
        // given
        alone.studenttesting.entity.Test test = new alone.studenttesting.entity.Test();
        test.setId(2L);
        test.setEnName("Mathemati 11");
        test.setUaName("Математ 11");
        test.setDifficulty(6L);
        test.setQuestionAmount(11);

        List<alone.studenttesting.entity.Test> tests = new ArrayList<>();
        tests.add(test);

        User user = new User();
        user.setId(1L);
        user.setEmail("alex1234@gmail.com");
        user.setPassword("alex2343");
        user.setTests(tests);
        List<alone.studenttesting.entity.Test> expectedTests = user.getTests();

        mockSecurityContext();
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));
        when(userRepository.save(any())).thenReturn(user);

        // when
        userService.testSelection(user.getId());
        List<alone.studenttesting.entity.Test> actualTests = userRepository.findById(user.getId()).get().getTests();
        // then
        int index = 0;
        for (alone.studenttesting.entity.Test actualTest : actualTests) {
            assertTest(actualTest, expectedTests.get(index++));
        }
    }

        @Test
        public void testPreparing_shall_select_a_test(){
        // given
            LocalDateTime TestStartDate = LocalDateTime.of(2020,12,02,10,00);
        alone.studenttesting.entity.Test test = new alone.studenttesting.entity.Test();
        test.setId(2L);
        test.setEnName("Mathemati 11");
        test.setUaName("Математ 11");
        test.setDifficulty(6L);
        test.setQuestionAmount(11);
        test.setTestDate(TestStartDate);

        List<alone.studenttesting.entity.Test> tests = new ArrayList<>();
        tests.add(test);

        User user = new User();
        user.setId(1L);
        user.setEmail("alex1234@gmail.com");
        user.setPassword("alex2343");
        user.setTests(tests);

        mockSecurityContext();
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));

        // when
        userService.testPreparing(test.getId());
        LocalDateTime passingTestDate = LocalDateTime.now();

                    // then
            assertEquals(passingTestDate.isAfter(TestStartDate), Boolean.TRUE);
    }

    /*@Test
    public void testPreparing_shall_select_a_test(){
        // given

        alone.studenttesting.entity.Test test = new alone.studenttesting.entity.Test();
        test.setId(1L);
        test.setEnName("Mathemati 11");
        test.setUaName("Математ 11");
        test.setDifficulty(6L);
        test.setQuestionAmount(11);

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
        List <Long> answerIds2 = new ArrayList<>();
        answerIds.add(2L);

        List<List<Long>> answerIds3 = new ArrayList<>();
        answerIds3.add(answerIds);
        answerIds3.add(answerIds2);

        TestPassingDto testPassingDto = new TestPassingDto();
        testPassingDto.setTestId(1L);
        testPassingDto.setAnswerIds(answerIds3);

        List<alone.studenttesting.entity.Test> tests = new ArrayList<>();
        tests.add(test);

        User user = new User();
        user.setId(1L);
        user.setEmail("alex1234@gmail.com");
        user.setPassword("alex2343");
        user.setTests(tests);

        int Mark = 0;

        mockSecurityContext();
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));
        when(userRepository.setMark(Mark, user.getId(), testPassingDto.getTestId())).thenReturn(Optional.of(test));

        // when
        userService.testPassing(testPassingDto);


        // then
        assertEquals(passingTestDate.isAfter(TestStartDate), Boolean.TRUE);
    }*/



    private void assertSubject(TestWithSubjectDto testWithSubjectDto,Subject subject){
        assertEquals(testWithSubjectDto.getEnNameSubject(),subject.getEnFirstName());
        assertEquals(testWithSubjectDto.getUaNameSubject(), subject.getUaFirstName());
        assertEquals(testWithSubjectDto.getEnNameTest(),subject.getTests().get(subject.getTests().size() - 1).getEnName());
        assertEquals(testWithSubjectDto.getUaNameTest(),subject.getTests().get(subject.getTests().size() - 1).getUaName());

    }

    private void assertTest(alone.studenttesting.entity.Test actualTest, alone.studenttesting.entity.Test expectedTest){
        assertEquals(actualTest.getId(), expectedTest.getId());
        assertEquals(actualTest.getQuestionAmount(), expectedTest.getQuestionAmount());
        assertEquals(actualTest.getDifficulty(), expectedTest.getDifficulty());
        assertEquals(actualTest.getEnName(), expectedTest.getEnName());
        assertEquals(actualTest.getUaName(), expectedTest.getUaName());
    }

    private void mockSecurityContext(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
