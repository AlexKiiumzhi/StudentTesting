package alone.studenttesting.controller;

import alone.studenttesting.entity.Answer;
import alone.studenttesting.entity.Subject;
import alone.studenttesting.entity.Test;

import alone.studenttesting.service.AdminService;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.*;
import alone.studenttesting.service.dto.Test.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Test> tests = adminService.getAllTests();
        model.addAttribute("tests", tests);
        return "adminhome";
    }

    @PostMapping("/blockuser")
    public String blockUser(@RequestParam(value = "id", required = false) @NotNull @Min(value=1) Long id, Model model, BindingResult bindingResult) {
        log.info("Block a user by his id, id:" + id);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.blockUser(id);
        model.addAttribute("id", id);
        return "adminhome";
    }

    @PostMapping("/unblockuser")
    public String unblockUser(@RequestParam(value = "id1", required = false) @NotNull @Min(value=1) Long id, Model model, BindingResult bindingResult) {
        log.info("Block a user by his id, id:" + id);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.unBlockUser(id);
        model.addAttribute("id1", id);
        return "adminhome";
    }

    @PostMapping("/user/edit")
    public String editUser(@Valid @ModelAttribute("userEditDto") UserEditDto userEditDto, BindingResult bindingResult)  {
        log.info("Edit user using UserEditDto:" + userEditDto.toString());
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.editUser(userEditDto);
        return "adminhome";
    }

    @GetMapping("/testPage")
    public String testsPage(Model model) {
        List<Subject> subjects = userService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "admintests";
    }
        @GetMapping("/test/searchBySubject")
    public String searchBySubject(@RequestParam(value = "searchedSubject") Long subjectId, Model model) {
        log.info("Retrieving all tests with subjects: " );
        List<Subject> subjects = userService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        List<TestWithSubjectDto> testWithSubjectDtos = userService.searchBySubject(subjectId);
        model.addAttribute("testWithSubjectDtos", testWithSubjectDtos);
        return "admintests";
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

        return "admintests";
    }

    @GetMapping("/test")
    public String testPage(Model model) {
        List<Answer> answers = adminService.getAllAnswers();
        model.addAttribute("answers", answers);
        return "admintestpage";
    }

    @PostMapping("/test/create")
    public String createTest(@Valid @ModelAttribute("testCreationDto") TestCreationDto testCreationDto, BindingResult bindingResult) {
        log.info("Create a test using  TestCreationDto:" + testCreationDto.toString());
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.createTest(testCreationDto);
        return "admintestpage";
    }

    @PostMapping("/test/edit")
    public String editTest(@Valid @ModelAttribute("testEditDto") TestEditDto testEditDto, BindingResult bindingResult) {
        log.info("Edit a test using TestEditDto:" + testEditDto.toString());
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.editTest(testEditDto);
        return "admintestpage";
    }

    @PostMapping("/test/delete")
    public String deleteTest(@RequestParam(value = "id", required = false) @NotNull @Min(value=1) Long id, Model model, BindingResult bindingResult) {
        log.info("Delete a test using its id, id:" + id);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.deleteTest(id);
        model.addAttribute("id", id);
        return "admintestpage";
    }

    @PostMapping("/question/create")
    public String createQuestion(@Valid @ModelAttribute("questionCreationDto") QuestionCreationDto questionCreationDto, BindingResult bindingResult) {
        log.info("Create a question for a test using QuestionCreationDto:" + questionCreationDto);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.createQuestion(questionCreationDto);
        return "admintestpage";
    }

    @PostMapping("/question/edit")
    public String editQuestion(@Valid @ModelAttribute("questionEditDto") QuestionEditDto questionEditDto, BindingResult bindingResult) {
        log.info("Edit a question using QuestionEditDto:" + questionEditDto);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.editQuestion(questionEditDto);
        return "admintestpage";
    }

    @PostMapping("/answer/create")
    public String createAnswer(@Valid @ModelAttribute("answerCreationDto") AnswerCreationDto answerCreationDto, BindingResult bindingResult) {
        log.info("Create a answer for a question using AnswerCreationDto:" + answerCreationDto);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.createAnswer(answerCreationDto);
        return "admintestpage";
    }

    @PostMapping("/answer/edit")
    public String editAnswer(@Valid @ModelAttribute("answerEditDto")AnswerEditDto answerEditDto, BindingResult bindingResult) {
        log.info("Edit a test using AnswerEditDto:" + answerEditDto);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        adminService.editAnswer(answerEditDto);
        return "admintestpage";
    }

}
