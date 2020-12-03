package alone.studenttesting.service.impl;

import alone.studenttesting.entity.*;
import alone.studenttesting.exception.*;
import alone.studenttesting.repository.*;
import alone.studenttesting.service.AdminService;
import alone.studenttesting.service.dto.AnswerCreationDto;
import alone.studenttesting.service.dto.QuestionCreationDto;
import alone.studenttesting.service.dto.QuestionEditDto;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;
import alone.studenttesting.service.dto.UserEditDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleksandr Kiiumzhi
 * This class is the implementations of all the actions that an admin can perform
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
     * description
     * @param id User ID
     * @return Id of the user that has been blocked
     */
    @Override
    public Long blockUser(Long id) {
        log.info("Block a user by his id and save the changes, id:" + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToBeBlocked = optionalUser.get();
            userToBeBlocked.setBlocked(Boolean.TRUE);
            userRepository.save(userToBeBlocked);
            return userToBeBlocked.getId();
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public Long unBlockUser(Long id) {
        log.info("Unblock a user by his id and save the changes, id:" + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToBeunBlocked = optionalUser.get();
            userToBeunBlocked.setBlocked(Boolean.FALSE);
            userRepository.save(userToBeunBlocked);
            return userToBeunBlocked.getId();
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void createTest(TestCreationDto testCreationDto) {
        log.info("Create a test and save the changes to the database using TestCreationDto:" + testCreationDto);
        Optional<Subject> optionalSubject = subjectRepository.findById(testCreationDto.getSubjectID());
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

    @Override
    public void createQuestion(QuestionCreationDto questionCreationDto) {
        log.info("Create a question for a test save the changes to the database using QuestionCreationDto:" + questionCreationDto);
        Optional<Test> optionalTest = testRepository.findById(questionCreationDto.getTestId());
        if (optionalTest.isPresent()) {
            if (!questionRepository.findByEnText(questionCreationDto.getEnText()).isPresent()&&
                    !questionRepository.findByUaText(questionCreationDto.getUaText()).isPresent()) {
                Test test = optionalTest.get();
                Question newQuestion = mapQuestionCreationDtoToQuestion(questionCreationDto);
                test.getQuestions().add(newQuestion);
                testRepository.save(test);
            } else {
                throw new QuestionAlreadyExistsException("Question with this name already exists, Please choose another name");
            }
        } else {
            throw new TestNotFoundException("Test not found");
        }
    }

    @Override
    public void createAnswer(AnswerCreationDto answerCreationDto) {
        log.info("Create a answer for a question and save the changes to the database using AnswerCreationDto:" + answerCreationDto);
        Optional<Question> optionalQuestion = questionRepository.findById(answerCreationDto.getQuestionId());
        if (optionalQuestion.isPresent()) {
            if (!answerRepository.findByEnAnswer(answerCreationDto.getEnAnswer()).isPresent()&&
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

    @Override
    public void deleteTest(Long id) {
        log.info("Delete a test using its id and save the changes in database, id:" + id);
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent()) {
            testRepository.deleteById(id);
        } else {
            throw new TestNotFoundException("Test not found");
        }
    }

    @Override
    public void editTest(TestEditDto testEditDto) {
        log.info("Edit a test and saving the changes in database using TestEditDto:" + testEditDto);
        Optional<Test> optionalTest = testRepository.findById(testEditDto.getId());
        if (optionalTest.isPresent()) {
            testRepository.save(mapTestEditDtoToTest(testEditDto, optionalTest.get()));
        } else {
            throw new TestNotFoundException("Test not found");
        }
    }

    @Override
    public void editQuestion(QuestionEditDto questionEditDto) {
        log.info("Edit a question and its answers, and saving the changes in database using QuestionEditDto:" + questionEditDto);
        Optional<Question> optionalQuestion = questionRepository.findById(questionEditDto.getQuestionId());
        if (optionalQuestion.isPresent()) {
            Question question = mapQuestionEditDtoToQuestion(questionEditDto, optionalQuestion.get());
            if (questionEditDto.getAnswerIds() != null) {
                List<Answer> questionAnswers = new ArrayList<>();
                for (Long id : questionEditDto.getAnswerIds()){
                    questionAnswers.add(answerRepository.findById(id).get());
                }
                question.setAnswers(questionAnswers);
            }else {
                throw new NoTestProvidedException("There is no answers provided, please include an answer or more!");
            }
            questionRepository.save(question);
        } else {
            throw new QuestionNotFoundException("Question not found");
        }
    }

    @Override
    public void editUser(UserEditDto userEditDto) {
        log.info("Edit user and save the changes in database using UserEditDto:" + userEditDto);
        Optional<User> optionalUser = userRepository.findById(userEditDto.getId());
        if (optionalUser.isPresent()) {
            User user = mapUserEditDtoToUser(userEditDto, optionalUser.get());
            if (userEditDto.getTestIds() != null) {
                List<Test> userTests = new ArrayList<>();
                for (Long id : userEditDto.getTestIds()){
                    userTests.add(testRepository.findById(id).get());
                }
                user.setTests(userTests);
            }else {
                throw new NoTestProvidedException("There is no tests provided, please include a test or more!");
            }
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    private Test mapTestCreationDtoToTest(TestCreationDto testCreationDto) {
        Test test = new Test();
        test.setEnName(testCreationDto.getEnName());
        test.setUaName(testCreationDto.getUaName());
        test.setDifficulty(testCreationDto.getDifficulty());
        test.setQuestionAmount(testCreationDto.getQuestionAmount());
        test.setTestDate(testCreationDto.getTestDate());
        return test;
    }

    private Test mapTestEditDtoToTest(TestEditDto testEditDto, Test testInDB) {
        testInDB.setEnName(testEditDto.getEnName());
        testInDB.setUaName(testEditDto.getUaName());
        testInDB.setDifficulty(testEditDto.getDifficulty());
        testInDB.setQuestionAmount(testEditDto.getQuestionAmount());
        testInDB.setTestDate(testEditDto.getTestDate());
        return testInDB;
    }
    private Question mapQuestionCreationDtoToQuestion(QuestionCreationDto questionCreationDto) {
        Question question = new Question();
        question.setEnText(questionCreationDto.getEnText());
        question.setUaText(questionCreationDto.getUaText());
        return question;
    }

    private Answer mapAnswerCreationDtoToAnswer(AnswerCreationDto answerCreationDto) {
        Answer answer = new Answer();
        answer.setEnAnswer(answerCreationDto.getEnAnswer());
        answer.setUaAnswer(answerCreationDto.getUaAnswer());
        answer.setCorrectnessState(answerCreationDto.getCorrectnessState());
        return answer;
    }

        private Question mapQuestionEditDtoToQuestion(QuestionEditDto questionEditDto, Question questionInDB) {
        questionInDB.setEnText(questionEditDto.getEnText());
        questionInDB.setUaText(questionEditDto.getUaText());
        return questionInDB;
    }

    private User mapUserEditDtoToUser(UserEditDto userEditDto, User userInDB) {
        userInDB.setEnFirstName(userEditDto.getEnFirstName());
        userInDB.setUaFirstName(userEditDto.getUaFirstname());
        userInDB.setEnLastName(userEditDto.getEnLastname());
        userInDB.setUaLastName(userEditDto.getLastName_ua());
        userInDB.setEmail(userEditDto.getEmail());
        userInDB.setPhone(userEditDto.getPhone());
        userInDB.setPassword(passwordEncoder.encode(userEditDto.getPassword()));
        userInDB.setAge(userEditDto.getAge());
        return userInDB;
    }




}
