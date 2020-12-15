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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Admin can block a user by providing an id of the user and saves the changes in database
     *
     * @param id User ID
     * @return Id of the user that has been blocked
     */
    @Override
    @Transactional
    public Long blockUser(Long id) {
        log.info("Block a user by his id and save the changes, id:" + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToBeBlocked = optionalUser.get();
            userToBeBlocked.setBlocked(Boolean.TRUE);
            userRepository.save(userToBeBlocked);
            return userToBeBlocked.getId();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Admin can unblock a user by providing an id of the user and saves the changes in database
     *
     * @param id User ID
     * @return Id of the user that has been unblocked
     */
    @Override
    @Transactional
    public Long unBlockUser(Long id) {
        log.info("Unblock a user by his id and save the changes, id:" + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToBeunBlocked = optionalUser.get();
            userToBeunBlocked.setBlocked(Boolean.FALSE);
            userRepository.save(userToBeunBlocked);
            return userToBeunBlocked.getId();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Admin can create a test by providing the information needed according to the testCreationDto
     * * and if the test's english and ukrainian name is not already present in the database then it is saved
     *
     * @param testCreationDto all information needed to create a test
     * @return void nothing is returning
     */
    @Override
    @Transactional
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

    /**
     * Admin can create a question for a test by providing the information needed according to the questionCreationDto
     * * and if the question's english and ukrainian description is not already present in the database then it is saved
     *
     * @param questionCreationDto all information needed to create a question
     * @return void nothing is returning
     */
    @Override
    @Transactional
    public void createQuestion(QuestionCreationDto questionCreationDto) {
        log.info("Create a question for a test save the changes to the database using QuestionCreationDto:" + questionCreationDto);
        Optional<Test> optionalTest = testRepository.findById(questionCreationDto.getTestId());
        if (optionalTest.isPresent()) {
            if (!questionRepository.findByEnText(questionCreationDto.getEnText()).isPresent() &&
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

    /**
     * Admin can create a answer for a question by providing the information needed according to the answerCreationDto
     * and if the answer's english and ukrainian name is not already present in the database then it is saved
     *
     * @param answerCreationDto all information needed to create a answer
     * @return void nothing is returning
     */
    @Override
    @Transactional
    public void createAnswer(AnswerCreationDto answerCreationDto) {
        log.info("Create an answer for a question and save the changes to the database using AnswerCreationDto:" + answerCreationDto);
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
     * @return void nothing is returning
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
     * and after checking the id existence in the database, the changes is saved
     *
     * @param testEditDto all information needed to edit a test
     * @return void nothing is returning
     */
    @Override
    @Transactional
    public void editTest(TestEditDto testEditDto) {
        log.info("Edit a test and saving the changes in database using TestEditDto:" + testEditDto);
        Optional<Test> optionalTest = testRepository.findById(testEditDto.getTestId());
        if (optionalTest.isPresent()) {
            testRepository.save(mapTestEditDtoToTest(testEditDto, optionalTest.get()));
        } else {
            throw new TestNotFoundException("Test not found");
        }
    }

    /**
     * Admin can edit a question and changing its answers ids by providing the information needed according to the questionEditDto
     * and after checking the id existence in the database, the changes is saved
     *
     * @param questionEditDto all information needed to edit a question and answer ids related to it
     * @return void nothing is returning
     */
    /*@Override
    @Transactional
    public void editQuestion(QuestionEditDto questionEditDto) {
        log.info("Edit a question and its answers, and saving the changes in database using QuestionEditDto:" + questionEditDto);
        Optional <Test> optionalTest = testRepository.findById(questionEditDto.getTestId());
        if (optionalTest.isPresent()) {
            Question question = optionalTest.get().getQuestions().stream()
                    .filter(q -> q.getId().equals(questionEditDto.getQuestionId()))
                    .map(q -> mapQuestionEditDtoToQuestion(questionEditDto, q))
                    .collect(Collectors.toList()).get(0);
            if (questionEditDto.getAnswerIds() != null) {
                List<Answer> questionAnswers = new ArrayList<>();
                for (Long id : questionEditDto.getAnswerIds()){
                    questionAnswers.add(answerRepository.findById(id).get());
                }
                question.setAnswers(questionAnswers);
            }else {
                throw new NoTestProvidedException("There is no answers provided, please include an answer or more!");
            }
            testRepository.save(optionalTest.get());
        } else {
            throw new QuestionNotFoundException("Question not found");
        }
    }*/
    @Override
    @Transactional
    public void editQuestion(QuestionEditDto questionEditDto) {
        log.info("Edit Question and save the changes in database using QuestionEditDto:" + questionEditDto);
        Optional<Question> optionalQuestion = questionRepository.findById(questionEditDto.getQuestionId());
        if (optionalQuestion.isPresent()) {
            Question question = mapQuestionEditDtoToQuestion(questionEditDto, optionalQuestion.get());
            if (questionEditDto.getAnswerIds().size() != 0) {
                //TODO go to database and delete all old answers (answer table)
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
     * Admin can edit a user and changing the test ids selected bu the user by providing the information needed according
     * to the userEditDto and after checking the id existence in the database, the changes is saved
     *
     * @param userEditDto all information needed to edit a user
     * @return void nothing is returning
     */
    @Override
    @Transactional
    public void editUser(UserEditDto userEditDto) {
        log.info("Edit user and save the changes in database using UserEditDto:" + userEditDto);
        Optional<User> optionalUser = userRepository.findById(userEditDto.getId());
        if (optionalUser.isPresent()) {
            User user = mapUserEditDtoToUser(userEditDto, optionalUser.get());
            if (userEditDto.getTestIds().size() != 0) {
                //TODO Maybe you want to delete the old tests that the user has done (user_test table)
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
