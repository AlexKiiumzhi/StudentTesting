package alone.studenttesting.service.impl;

import alone.studenttesting.entity.*;
import alone.studenttesting.exception.UserNotFoundException;
import alone.studenttesting.repository.SubjectRepository;
import alone.studenttesting.repository.TestRepository;
import alone.studenttesting.repository.UserRepository;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

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
        if (!userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            User newUser = userRepository.save(mapRegistrationDtoToUser(registrationDto));
            return newUser.getId();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<TestWithSubjectDto> getAllTestsWithSubjects() {

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
                throw new UserNotFoundException("test exists");
            }
        } else {
            throw new UserNotFoundException("test not present");
        }
    }
   
    @Override
    public Integer testPassing(TestPassingDto testPassingDto) {
        User user = getLoggedInUser();
        Optional<Test> optionalTest = testRepository.findById(testPassingDto.getTestId());
        List<List<Long>> studentAnswers = testPassingDto.getAnswerIds();
        LocalDateTime testStartDate = optionalTest.get().getTestDate();
        LocalDateTime passingDate = LocalDateTime.now();

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
