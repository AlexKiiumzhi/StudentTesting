package alone.studenttesting.service.impl;

import alone.studenttesting.entity.*;
import alone.studenttesting.exception.*;
import alone.studenttesting.repository.*;
import alone.studenttesting.service.AdminService;
import alone.studenttesting.service.dto.AnswerCreationDto;
import alone.studenttesting.service.dto.QuestionCreationDto;
import alone.studenttesting.service.dto.QuestionEditDto;
import alone.studenttesting.service.dto.AnswerEditDto;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;
import alone.studenttesting.service.dto.UserEditDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleksandr Kiiumzhi
 * This class is the implementation of all the actions that an admin can perform
 */
@Service
public class AdminServiceImpl implements AdminService {

    public static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    TestRepository testRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retrieving all tests from database
     *
     * @return List of all tests
     */
    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    /**
     * Admin can block a user by providing an id of the user and saves the changes in database
     *
     * @param id User ID
     */
    @Override
    @Transactional
    public void blockUser(Long id) {
        log.info("Block a user by his id and save the changes, id:" + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToBeBlocked = optionalUser.get();
            userToBeBlocked.setBlocked(Boolean.TRUE);
            userRepository.save(userToBeBlocked);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Admin can unblock a user by providing an id of the user and saves the changes in database
     *
     * @param id User ID
     */
    @Override
    @Transactional
    public void unBlockUser(Long id) {
        log.info("Unblock a user by his id and save the changes, id:" + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToBeunBlocked = optionalUser.get();
            userToBeunBlocked.setBlocked(Boolean.FALSE);
            userRepository.save(userToBeunBlocked);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Retrieving all answers from database
     *
     * @return List of all answers
     */
    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    /**
     * Admin can create a test by providing the information needed according to the testCreationDto
     *  and if the test's english and ukrainian name is not already present in the database then it is saved
     *
     * @param testCreationDto all information needed to create a test
     */
    @Override
    @Transactional
    public void createTest(TestCreationDto testCreationDto) {
        log.info("Create a test and save the changes to the database using TestCreationDto:" + testCreationDto.toString());
        Optional<Subject> optionalSubject = subjectRepository.findById(testCreationDto.getSubjectId());
        if (optionalSubject.isPresent()) {
            if (!testRepository.findByEnName(testCreationDto.getEnName()).isPresent() &&
                    !testRepository.findByUaName(testCreationDto.getUaName()).isPresent()) {
                Subject subject = optionalSubject.get();
                Test newTest = mapTestCreationDtoToTest(testCreationDto);
                subject.getTests().add(newTest);
                subjectRepository.save(subject);
            } else {
                throw new TestAlreadyExistsException("Test with this name already exists, Please choose another name");
            }
        } else {
            throw new SubjectNotFoundException("Subject not found");
        }
    }

    /**
     * Admin can create a question for a test by providing the information needed according to the questionCreationDto
     *  and if the question's english and ukrainian description is not already present in the database then it is saved
     *
     * @param questionCreationDto all information needed to create a question
     */
    @Override
    @Transactional
    public void createQuestion(QuestionCreationDto questionCreationDto) {
        log.info("Create a question for a test save the changes to the database using QuestionCreationDto:" + questionCreationDto.toString());
        Optional<Test> optionalTest = testRepository.findById(questionCreationDto.getTestId());
        if (optionalTest.isPresent()) {
            if (!questionRepository.findByEnText(questionCreationDto.getEnText()).isPresent() &&
                    !questionRepository.findByUaText(questionCreationDto.getUaText()).isPresent()) {
                Test test = optionalTest.get();
                Question newQuestion = mapQuestionCreationDtoToQuestion(questionCreationDto);
                test.getQuestions().add(newQuestion);
                Integer questionAmount = test.getQuestionAmount() + 1;
                test.setQuestionAmount(questionAmount);
                testRepository.save(test);
            } else {
                throw new QuestionAlreadyExistsException("Question with this name already exists, Please choose another name");
            }
        } else {
            throw new TestNotFoundException("Test not found");
        }
    }

    /**
     * Admin can create a answer for a question by providing the information needed according to the answerCreationDto
     * and if the answer's english and ukrainian name is not already present in the database then it is saved
     *
     * @param answerCreationDto all information needed to create a answer
     */
    @Override
    @Transactional
    public void createAnswer(AnswerCreationDto answerCreationDto) {
        log.info("Create an answer for a question and save the changes to the database using AnswerCreationDto:" + answerCreationDto.toString());
        Optional<Question> optionalQuestion = questionRepository.findById(answerCreationDto.getQuestionId());
        if (optionalQuestion.isPresent()) {
            if (!answerRepository.findByEnAnswer(answerCreationDto.getEnAnswer()).isPresent() &&
                    !answerRepository.findByUaAnswer(answerCreationDto.getUaAnswer()).isPresent()) {
                Question question = optionalQuestion.get();
                Answer newAnswer = mapAnswerCreationDtoToAnswer(answerCreationDto);
                question.getAnswers().add(newAnswer);
                questionRepository.saveAndFlush(question);
            } else {
                throw new AnswerAlreadyExistsException("Answer with this name already exists, Please choose another name");
            }
        } else {
            throw new QuestionNotFoundException("Question not found");
        }
    }

    /**
     * Admin can delete a test by providing an id of a test and saves the changes in database
     *
     * @param id Test id
     */
    @Override
    @Transactional
    public void deleteTest(Long id) {
        log.info("Delete a test using its id and save the changes in database, id:" + id);
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent()) {
            testRepository.deleteById(id);
        } else {
            throw new TestNotFoundException("Test not found");
        }
    }

    /**
     * Admin can edit a test by providing the information needed according to the testEditDto
     * and after checking the id existence in the database, the changes are saved
     *
     * @param testEditDto all information needed to edit a test
     */
    @Override
    @Transactional
    public void editTest(TestEditDto testEditDto) {
        log.info("Edit a test and saving the changes in database using TestEditDto:" + testEditDto.toString());
        Optional<Test> optionalTest = testRepository.findById(testEditDto.getTestId());
        if (optionalTest.isPresent()) {
            testRepository.save(mapTestEditDtoToTest(testEditDto, optionalTest.get()));
        } else {
            throw new TestNotFoundException("Test not found");
        }
    }

    /**
     * Admin can edit a question and changing its answers ids by providing the information needed according to the questionEditDto
     * and after checking the id existence in the database, the changes are saved
     *
     * @param questionEditDto all information needed to edit a question and answer ids related to it
     */
    @Override
    @Transactional
    public void editQuestion(QuestionEditDto questionEditDto) {
        log.info("Edit Question and save the changes in database using QuestionEditDto:" + questionEditDto.toString());
        Optional<Question> optionalQuestion = questionRepository.findById(questionEditDto.getQuestionId());
        if (optionalQuestion.isPresent()) {
            Question question = mapQuestionEditDtoToQuestion(questionEditDto, optionalQuestion.get());
            if (questionEditDto.getAnswerIds().size() != 0) {
                answerRepository.deleteByQuestionId(questionEditDto.getQuestionId());
                List<Answer> questionAnswers = new ArrayList<>();
                for (Long id : questionEditDto.getAnswerIds()) {
                    questionAnswers.add(answerRepository.findById(id).get());
                }
                question.setAnswers(questionAnswers);
            }
            questionRepository.save(optionalQuestion.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Admin can edit a answer by providing the information needed according to the userEditDto and after checking the id existence
     * in the database, the changes are saved
     *
     * @param answerEditDto all information needed to edit an answer
     */
    @Override
    @Transactional
    public void editAnswer(AnswerEditDto answerEditDto) {
        log.info("Edit an Answer and saving the changes in database using AnswerEditDto:" + answerEditDto.toString());
        Optional<Answer> optionalAnswer = answerRepository.findById(answerEditDto.getAnswerId());
        if (optionalAnswer.isPresent()) {
            answerRepository.save(mapAnswerEditDtoToAnswer(answerEditDto, optionalAnswer.get()));
        } else {
            throw new AnswerNotFoundException("Answer not found");
        }
    }
    /**
     * Admin can edit a user and changing the test ids selected bu the user by providing the information needed according
     * to the userEditDto and after checking the id existence in the database, the changes are saved
     *
     * @param userEditDto all information needed to edit a user
     */
    @Override
    @Transactional
    public void editUser(UserEditDto userEditDto) {
        log.info("Edit user and save the changes in database using UserEditDto:" + userEditDto.toString());
        Optional<User> optionalUser = userRepository.findById(userEditDto.getUserId());
        if (optionalUser.isPresent()) {
            User user = mapUserEditDtoToUser(userEditDto, optionalUser.get());
            if (userEditDto.getTestIds().size() != 0) {
                List<Test> userTests = new ArrayList<>();
                for (Long id : userEditDto.getTestIds()) {
                    userTests.add(testRepository.findById(id).get());
                }
                user.setTests(userTests);
            }
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Mapper method that converts a testCreationDto to a test object
     *
     * @param testCreationDto all information needed to convert a testCreationDto to a test
     * @return User Object
     */
    private Test mapTestCreationDtoToTest(TestCreationDto testCreationDto) {
        Test test = new Test();
        test.setEnName(testCreationDto.getEnName());
        test.setUaName(testCreationDto.getUaName());
        test.setDifficulty(testCreationDto.getDifficulty());
        test.setQuestionAmount(testCreationDto.getQuestionAmount());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        test.setTestDate(LocalDateTime.parse(testCreationDto.getTestDate(), formatter));
        return test;
    }

    /**
     * Mapper method that converts a testEditDto to a test object
     *
     * @param (testEditDto, testInDB) all information needed to update a test in database using testEditDto
     * @return Test Object
     */
    private Test mapTestEditDtoToTest(TestEditDto testEditDto, Test testInDB) {
        testInDB.setEnName(testEditDto.getEnName());
        testInDB.setUaName(testEditDto.getUaName());
        testInDB.setDifficulty(testEditDto.getDifficulty());
        testInDB.setQuestionAmount(testEditDto.getQuestionAmount());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        testInDB.setTestDate(LocalDateTime.parse(testEditDto.getTestDate(), formatter));
        return testInDB;
    }

    /**
     * Mapper method that converts a questionCreationDto to a question object
     *
     * @param questionCreationDto all information needed to convert to a Question
     * @return Question Object
     */
    private Question mapQuestionCreationDtoToQuestion(QuestionCreationDto questionCreationDto) {
        Question question = new Question();
        question.setEnText(questionCreationDto.getEnText());
        question.setUaText(questionCreationDto.getUaText());
        question.setPass(Boolean.FALSE);
        return question;
    }

    /**
     * Mapper method that converts a answerCreationDto to an answer object
     *
     * @param answerCreationDto all information needed to convert to a Answer
     * @return Answer Object
     */
    private Answer mapAnswerCreationDtoToAnswer(AnswerCreationDto answerCreationDto) {
        Answer answer = new Answer();
        answer.setEnAnswer(answerCreationDto.getEnAnswer());
        answer.setUaAnswer(answerCreationDto.getUaAnswer());
        answer.setCorrectnessState(answerCreationDto.getCorrectnessState());
        return answer;
    }

    /**
     * Mapper method that converts a question to a questionEditDto object
     *
     * @param (questionEditDto, questionInDB) all information needed to update a question in database using questionEditDto
     * @return Question Object
     */
    private Question mapQuestionEditDtoToQuestion(QuestionEditDto questionEditDto, Question questionInDB) {
        questionInDB.setEnText(questionEditDto.getEnText());
        questionInDB.setUaText(questionEditDto.getUaText());
        return questionInDB;
    }

    /**
     * Mapper method that converts a question to a answerEditDto object
     *
     * @param (answerEditDto, answerInDB) all information needed to update a answer in database using answerEditDto
     * @return Answer Object
     */
    private Answer mapAnswerEditDtoToAnswer(AnswerEditDto answerEditDto, Answer answerInDB) {
        answerInDB.setEnAnswer(answerEditDto.getEnAnswer());
        answerInDB.setUaAnswer(answerEditDto.getUaAnswer());
        answerInDB.setCorrectnessState(answerEditDto.getCorrectnessState());

        return answerInDB;
    }

    /**
     * Mapper method that converts a question to a userEditDto object
     *
     * @param (userEditDto, userInDB) all information needed to update a user in database using userEditDto
     * @return User Object
     */
    private User mapUserEditDtoToUser(UserEditDto userEditDto, User userInDB) {
        userInDB.setEnFirstName(userEditDto.getEnFirstName());
        userInDB.setUaFirstName(userEditDto.getUaFirstName());
        userInDB.setEnLastName(userEditDto.getEnLastName());
        userInDB.setUaLastName(userEditDto.getLastName_ua());
        userInDB.setEmail(userEditDto.getEmail());
        userInDB.setPhone(userEditDto.getPhone());
        userInDB.setPassword(passwordEncoder.encode(userEditDto.getPassword()));
        userInDB.setAge(userEditDto.getAge());
        return userInDB;
    }


}
