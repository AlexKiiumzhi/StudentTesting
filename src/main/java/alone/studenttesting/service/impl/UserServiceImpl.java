package alone.studenttesting.service.impl;

import alone.studenttesting.entity.*;
import alone.studenttesting.entity.enums.Role;
import alone.studenttesting.exception.*;
import alone.studenttesting.repository.SubjectRepository;
import alone.studenttesting.repository.TestRepository;
import alone.studenttesting.repository.UserRepository;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.*;
import alone.studenttesting.service.dto.TestWithResultsDto;
import alone.studenttesting.service.dto.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Guest can register in system by providing the information needed according to registrationDto
     * and if the email is not already present in the database then the user is created
     *
     * @param registrationDto all information needed to create a user
     */
    @Override
    @Transactional
    public void registerUser(RegistrationDto registrationDto) {
        log.info("Creating and saving new user to the database using the registrationDto: " + registrationDto.toString());
        if (!userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            userRepository.save(mapRegistrationDtoToUser(registrationDto));
        } else {
            throw new UserAlreadyExistsException("This email is already taken");
        }
    }

    /**
     * Retrieving all registration information from database
     * @return UserInfoDto all information about the user
     */
    @Override
    @Transactional
    public UserInfoDto getUserRegistrationInfo() {
        log.info("Retrieving all registration info from database: ");
        return mapUserToUserInfoDto(getLoggedInUser());
    }

    /**
     * Retrieving all tests that the user has passed from database with the marks
     * @return List of TestWithResultsDto (all user tests with marks)
     */
    @Override
    @Transactional
    public List<TestWithResultsDto> getUserTests() {
        log.info("Retrieving all tests with subjects from database: ");
        User user = getLoggedInUser();
            List<Subject> subjects = subjectRepository.findAll();
            List<TestWithResultsDto> testWithResultsDto = new ArrayList<>();
            for (Subject subject : subjects) {
                for (Test test : subject.getTests()) {
                    Integer mark = userRepository.getTestMarks(user.getId(),test.getId());
                        if (mark != null){
                            testWithResultsDto.add(mapTestAndResultsToTestWithResultsDto(test, subject, mark));
                        }
                    }
                }
            return testWithResultsDto;
    }

    /**
     * User can select a test by providing a test id which then is saved for that user after checking if the test
     *  does not already exist in user's List of tests
     *
     * @param id test id which the
     * @return List of questions
     */
    @Override
    @Transactional
    public List<Question> testSelection(Long id) {
        log.info("Finding,Selecting a test and saving it into user in the database, test:" + id);
        User user = getLoggedInUser();
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent()) {
            LocalDateTime testStartDate = optionalTest.get().getTestDate();
            LocalDateTime passingTestDate = LocalDateTime.now();
                if (!user.getBlocked()) {
                    if (passingTestDate.isAfter(testStartDate)) {
                    List<Long> testIds = user.getTests().stream()
                            .map(Test::getId)
                            .collect(Collectors.toList());
                    if (!testIds.contains(id)) {
                        testRepository.addUserTest(user.getId(), id);
                        return optionalTest.get().getQuestions();
                    } else {
                        throw new TestAlreadyExistsException("this test is already selected");
                    }
                    }else {
                        throw new TestTimeException("You are early, Please wait for test start time");
                    }
                } else {
                    throw new UserIsBlockedException("This user is blocked ");
                }

        } else {
            throw new TestNotFoundException("test not found");
        }
    }

    /**
     * User can pass a test by providing a testPassingDto then checking if the test does not already exist in user's List of tests
     * and calculating the results by comparing the provided answer ids with the correct answer ids in the database
     *
     * @param testPassingDto all information needed to pass a test
     */
    @Override
    @Transactional
    public void testPassing(TestPassingDto testPassingDto) {
        log.info("Passing the test,calculating results and saving them in database using testPassingDto:"
                + testPassingDto.toString());
        User user = getLoggedInUser();
        Optional<Test> optionalTest = testRepository.findById(testPassingDto.getTestId());
        List<List<Long>> studentAnswers = testPassingDto.getAnswerIds();
        List<Long> testIds = user.getTests().stream().map(Test::getId).collect(Collectors.toList());
        if (!testIds.contains(testPassingDto.getTestId())) {
            testSelection(testPassingDto.getTestId());
        }
            if (optionalTest.isPresent()) {
                if (!user.getBlocked()) {
                    int correctQuestions = 0;
                    List<List<Long>> correctAnswers = new ArrayList<>();
                    for (Question question : optionalTest.get().getQuestions()) {
                        List<Long> answers = new ArrayList<>();
                        Long answerOrder = 0L;
                        for (Answer answer : question.getAnswers()) {
                            ++answerOrder;
                            if (answer.getCorrectnessState()) {
                                answers.add(answerOrder);
                            }
                        }
                        correctAnswers.add(answers);
                    }
                    for (int i = 0; i < studentAnswers.size(); i++) {
                        int answerOrder = 0;
                        for (Long studentAnswer : studentAnswers.get(i)) {
                            ++answerOrder;
                            if (!correctAnswers.get(i).contains(studentAnswer)) {
                                break;
                            }
                            if (correctAnswers.get(i).size() == answerOrder) {
                                ++correctQuestions;
                            }
                        }
                    }
                    int mark = correctQuestions * 100 / optionalTest.get().getQuestionAmount();
                    userRepository.setMark(mark, user.getId(), testPassingDto.getTestId());
                } else {
                    throw new UserIsBlockedException("This user is blocked ");
                }
            } else {
                throw new TestNotFoundException("test not found");
            }
        }

    /**
     * Getting the user that is logged in and currently in the session
     *
     * @return User
     */
    @Override
    public User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * User can search tests by subject filtering after checking if subject provided exists and getting all tests of that subject
     *
     * @param subjectId Id of a subject
     * @return List of TestWithSubjectDto (all information needed to get tests of a subject)
     */
    @Override
    @Transactional
    public List<TestWithSubjectDto> searchBySubject(Long subjectId) {
        log.info("Retrieving all tests with subjects from database: ");

        Optional<Subject> searchedSubject = subjectRepository.findById(subjectId);
        List<TestWithSubjectDto> testWithSubjectDtos = new ArrayList<>();
        if (searchedSubject.isPresent()) {
            List<Test> tests = searchedSubject.get().getTests();
            for (Test test : tests) {
                testWithSubjectDtos.add(mapTestAndSubjectToTestWithSubjectDto(test, searchedSubject.get()));
            }
            return testWithSubjectDtos;
        }else {
            throw new TestNotFoundException("Subject not found");
        }
    }

    /**
     * Retrieving all subjects from database
     *
     * @return List of all subjects
     */
    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    /**
     * User can sort searched results using a specific sorting parameter and the results are divided into pages by the pagination process
     * that JPA provides
     *
     * @param (pageNo, pageSize,sortBy) necessary information for pagination and sorting
     * @return TestsWithSubjectDto (all information needed to get tests with subjects sorted nad paged)
     */
    @Override
    @Transactional
    public TestsWithSubjectDto getAllTests(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("Retrieving all tests from the database with the possibility of sorting and paging, Page number:" + pageNo
                + "Page size:" + pageSize + "Sort by: " + sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<Subject> subjects = subjectRepository.findAll();
        Page<Test> tests = testRepository.findAll(paging);
        TestsWithSubjectDto testsWithSubjectDto = new TestsWithSubjectDto();
        for (Test test : tests.getContent()) {
            for (Subject subject : subjects) {
                for (Test subjectTest : subject.getTests()) {
                    if (subjectTest.getId().equals(test.getId())) {
                        testsWithSubjectDto.getTestWithSubjectDtos().add(mapTestAndSubjectToTestWithSubjectDto(test, subject));
                    }
                }
            }
        }
        int totalPages = (int) tests.getTotalElements() / pageSize;
        if (tests.getTotalElements() > totalPages * pageSize) {
            totalPages = totalPages + 1;
        }
        testsWithSubjectDto.setTotalPages(totalPages);
        return testsWithSubjectDto;
    }

    /**
     * Mapper method that converts a userEditDto to a User object
     *
     * @param registrationDto all information needed to convert to a User
     * @return User Object
     */
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
        user.setRole(Role.ROLE_USER);
        user.setBlocked(Boolean.FALSE);
        return user;
    }

    /**
     * Mapper method that converts a user from database to a UserInfoDto
     *
     * @param user User from database
     * @return UserInfoDto Object
     */
    private UserInfoDto mapUserToUserInfoDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getId());
        userInfoDto.setEnFirstName(user.getEnFirstName());
        userInfoDto.setEnLastName(user.getEnLastName());
        userInfoDto.setUaFirstName(user.getUaFirstName());
        userInfoDto.setUaLastName(user.getUaLastName());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setPhone(user.getPhone());
        userInfoDto.setPassword(user.getPassword());
        userInfoDto.setAge(user.getAge());

        return userInfoDto;
    }

    /**
     * Mapper method that converts a test and a subject to a TestWithSubjectDto object
     *
     * @param (test, subject) all information needed to convert a test and a subject to TestWithSubjectDto
     * @return TestWithSubjectDto Object
     */
    private TestWithSubjectDto mapTestAndSubjectToTestWithSubjectDto(Test test, Subject subject) {
        TestWithSubjectDto testWithSubjectDto = new TestWithSubjectDto();
        testWithSubjectDto.setEnNameTest(test.getEnName());
        testWithSubjectDto.setUaNameTest(test.getUaName());
        testWithSubjectDto.setEnNameSubject(subject.getEnName());
        testWithSubjectDto.setUaNameSubject(subject.getUaName());
        testWithSubjectDto.setDifficulty(test.getDifficulty());
        testWithSubjectDto.setQuestionAmount(test.getQuestionAmount());
        return testWithSubjectDto;
    }

    /**
     * Mapper method that converts a test, a subject and a mark to a TestWithResultsDto object
     *
     * @param (test, subject, mark) all information needed to convert a test, a subject and a mark to TestWithResultsDto
     * @return TestWithResultsDto Object
     */
    private TestWithResultsDto mapTestAndResultsToTestWithResultsDto(Test test, Subject subject, Integer mark) {
        TestWithResultsDto testWithResultsDto = new TestWithResultsDto();
        testWithResultsDto.setTestId(test.getId());
        testWithResultsDto.setTestEnName(test.getEnName());
        testWithResultsDto.setTestUaName(test.getUaName());
        testWithResultsDto.setSubjectEnName(subject.getEnName());
        testWithResultsDto.setSubjectUaName(subject.getUaName());
        testWithResultsDto.setMark(mark);
        return testWithResultsDto;
    }
}
