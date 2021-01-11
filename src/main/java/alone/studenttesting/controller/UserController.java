package alone.studenttesting.controller;


import alone.studenttesting.entity.Question;
import alone.studenttesting.entity.Subject;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import alone.studenttesting.service.dto.Test.TestsWithSubjectDto;
import alone.studenttesting.service.dto.TestWithResultsDto;
import alone.studenttesting.service.dto.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Autowired
    ParameterValidator parameterValidator;

    @GetMapping("/home")
    public String home(Model model) {
        UserInfoDto userInfoDto = userService.getUserRegistrationInfo();
        model.addAttribute("userInfoDto", userInfoDto);
        List<TestWithResultsDto> testsWithResultsDtos = userService.getUserTests();
        model.addAttribute("testsWithResultsDtos", testsWithResultsDtos);
        return "userhome";
    }

    @GetMapping("/testPage")
    public String testPage(Model model) {
        List<Subject> subjects = userService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "usertests";
    }

    @GetMapping("/test/searchBySubject")
    public String searchBySubject(@RequestParam(value = "searchedSubject") Long subjectId, Model model, BindingResult bindingResult) {
        log.info("Retrieving all tests with subjects: " );
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        List<Subject> subjects = userService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        List<TestWithSubjectDto> testWithSubjectDtos = userService.searchBySubject(subjectId);
        model.addAttribute("testWithSubjectDtos", testWithSubjectDtos);
        return "usertests";
    }

    @GetMapping("/test/all")
    public String getAllTests(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "enName") String sortBy, Model model) {
        log.info("Retrieving all tests with the possibility of sorting and paging, Page number:" + pageNo
                + "Page size:" + pageSize + "Sort by: " + sortBy);
        List<Subject> subjects = userService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        TestsWithSubjectDto testsWithSubjectDto = userService.getAllTests(pageNo, pageSize, sortBy);
        model.addAttribute("testsWithSubjectDto", testsWithSubjectDto);
        model.addAttribute("pageNumber", pageNo);

        return "usertests";
    }

    @GetMapping("/testPassingPage")
    public String testPassingPage() {
        return "usertestpassingpage";
    }

    @PostMapping("/testPassingPage/select")
    public String testSelection(@RequestParam(value = "id", required = false)Long id, Model model) {
        log.info("Selecting a test and saving it in user, test:" + id);
        if(!parameterValidator.validateNullNumber(id)) {
            model.addAttribute("enErrorMessage", ResourceBundle.getBundle("outputs", Locale.getDefault()).getString("Id.validation_error"));
            return "idvalidationerror";
        }

        List<Question> questions = userService.testSelection(id);
        model.addAttribute("id", id);
        model.addAttribute("questions", questions);
        return "usertestpassingpage";
    }

    @PostMapping("/testPassingPage/pass")
    public String testPassing(@RequestParam(value = "id1", required = false) @NotNull @Min(value=1) Long id, Model model) {
        log.info("Passing the test and retrieving results using testPassingDto:" + id);

        List<List<Long>> studentAnswers = new ArrayList<>();
        List<Long> answers1 = new ArrayList<>();
        Random rand = new Random();
        int low = 1;
        int high = 4;
        for (int i = 0; i < 2; i++)
        {
            long result = rand.nextInt(high - low);
            answers1.add(result);
        }
        List<Long> answers2 = new ArrayList<>();
        List<Long> answers3 = new ArrayList<>();
        for (int i = 0; i < 1; i++)
        {
            long result1 = rand.nextInt(high - low);
            answers2.add(result1);
            long result2 = rand.nextInt(high - low);
            answers3.add(result2);
        }
        studentAnswers.add(answers1);
        studentAnswers.add(answers2);
        studentAnswers.add(answers3);

        if(!parameterValidator.validateNullNumber(id)) {
            model.addAttribute("enErrorMessage", ResourceBundle.getBundle("outputs", Locale.getDefault()).getString("Id.validation_error"));
            return "idvalidationerror";
        }

        TestPassingDto testPassingDto = new TestPassingDto();
        model.addAttribute("id1", id);
        testPassingDto.setTestId(id);
        testPassingDto.setAnswerIds(studentAnswers);
        userService.testPassing(testPassingDto);
        return "usertestpassingpage";

    }
}
