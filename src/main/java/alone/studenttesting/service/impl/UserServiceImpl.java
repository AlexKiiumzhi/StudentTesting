package alone.studenttesting.service.impl;

import alone.studenttesting.entity.*;
import alone.studenttesting.exception.*;
import alone.studenttesting.repository.SubjectRepository;
import alone.studenttesting.repository.TestRepository;
import alone.studenttesting.repository.UserRepository;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Long registerUser(RegistrationDto registrationDto) {
        log.info("Creating and saving new user to the database using the registrationDto: " + registrationDto.toString());
        if (!userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            User newUser = userRepository.save(mapRegistrationDtoToUser(registrationDto));
            return newUser.getId();
        } else {
            throw new UserAlreadyExistsException("This email is already taken");
        }
    }

    @Override
    public List<TestWithSubjectDto> getAllTestsWithSubjects() {
        log.info("Retrieving all tests with subjects from database: " );
        List<Subject> subjects = subjectRepository.findAll();
        List<TestWithSubjectDto> testWithSubjectDtos = new ArrayList<>();
        for (Subject subject : subjects) {
            for (Test test : subject.getTests())
                testWithSubjectDtos.add(mapTestAndSubjectToTestWithSubjectDto(test, subject));
        }
        return testWithSubjectDtos;

    }

    @Override
    public List<Test> testSelection(Long id) {
        log.info("Finding,Selecting a test and saving it into user in the database, test:" + id);
        Optional<User> optionalUser = userRepository.findById(getLoggedInUser().getId());
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent()) {
            User user = optionalUser.get();
            List<Long> testIds = user.getTests().stream()
                    .map(Test::getId)
                    .collect(Collectors.toList());
            if (!testIds.contains(id)) {
                user.getTests().add(optionalTest.get());
                userRepository.save(user);
                return user.getTests();
            } else {
                throw new TestNotFoundException("test not found");
            }
        } else {
            throw new TestAlreadyExistsException("this test is already selected");
        }
    }

    @Override
    public List<Question> testPreparing(Long id) {
        log.info("Retrieving and Preparing the test and for the user, test:" + id);
        Optional<User> optionalUser = userRepository.findById(getLoggedInUser().getId());
        Optional<Test> optionalTest = testRepository.findById(id);
        LocalDateTime testStartDate = optionalTest.get().getTestDate();
        LocalDateTime passingTestDate = LocalDateTime.now();
        if (optionalUser.isPresent()) {
            if (passingTestDate.isAfter(testStartDate)) {
                if (optionalTest.isPresent()) {
                    return optionalTest.get().getQuestions();
                } else {
                    throw new TestNotFoundException("Test not found");
                }
            } else {
                throw new TestTimeException("You are early, Please wait for test start time");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public Integer testPassing(TestPassingDto testPassingDto) {
        log.info("Passing the test,calculating results and saving them in database using testPassingDto:"
                + testPassingDto.toString());
        User user = getLoggedInUser();
        Optional<Test> optionalTest = testRepository.findById(testPassingDto.getTestId());
        List<List<Long>> studentAnswers = testPassingDto.getAnswerIds();

        Integer correctQuestions = 0;
        List<List<Long>> correctAnswers = new ArrayList<>();
        for (Question question : optionalTest.get().getQuestions()){
            List<Long> answers = new ArrayList<>();
            Long answerOrder = 0L;
            for (Answer answer : question.getAnswers()){
                ++ answerOrder;
                if (answer.getCorrectnessState() == true){
                    answers.add(answerOrder);
                }
            }
            correctAnswers.add(answers);
        }
        for(int i = 0; i < studentAnswers.size();i++){
            int answerOrder = 0;
            for(Long studentAnswer : studentAnswers.get(i)){
                ++ answerOrder;
                if(!correctAnswers.get(i).contains(studentAnswer)){
                break;
                }
                if(correctAnswers.get(i).size() == answerOrder) {
                    ++ correctQuestions;
                }
            }
        }
        int mark = correctQuestions * 100 / optionalTest.get().getQuestionAmount();
        userRepository.setMark(mark, user.getId(), testPassingDto.getTestId());
        return mark;
    }

    @Override
    public User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<TestDto> getAllTests(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Retrieving all tests from the database with the possibility of sorting and paging, Page number:" + pageNo
                + "Page size:" + pageSize + "Sort by: " + sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<Test> tests = testRepository.findAll(paging).getContent();
        List<TestDto> testDtos = new ArrayList<>();
        for (Test test : tests) {
            testDtos.add(mapTestToTestDto(test));
        }
        return testDtos;
    }

    private User mapRegistrationDtoToUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setEnFirstName(registrationDto.getEnFirstName());
        user.setUaFirstName(registrationDto.getUaFirstName());
        user.setEnLastName(registrationDto.getEnLastName());
        user.setUaLastName(registrationDto.getUaLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPhone(registrationDto.getPhone());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAge(registrationDto.getAge());
        return user;
    }

    private TestDto mapTestToTestDto(Test test) {
        TestDto testsDto = new TestDto();
        testsDto.setId(test.getId());
        testsDto.setEnName(test.getEnName());
        testsDto.setUaName(test.getUaName());
        testsDto.setDifficulty(test.getDifficulty());
        testsDto.setQuestionAmount((test.getQuestionAmount()));
        testsDto.setTestDate(test.getTestDate());
        testsDto.setQuestions(test.getQuestions());
        return testsDto;
    }

    private TestWithSubjectDto mapTestAndSubjectToTestWithSubjectDto(Test test, Subject subject) {
        TestWithSubjectDto testWithSubjectDto = new TestWithSubjectDto();
        testWithSubjectDto.setEnNameTest(test.getEnName());
        testWithSubjectDto.setUaNameTest(test.getUaName());
        testWithSubjectDto.setEnNameSubject(subject.getEnFirstName());
        testWithSubjectDto.setUaNameSubject(subject.getUaFirstName());
        testWithSubjectDto.setDifficulty(test.getDifficulty());
        testWithSubjectDto.setQuestionAmount(test.getQuestionAmount());
        return testWithSubjectDto;
    }
}
