package alone.studenttesting.controller;

import alone.studenttesting.service.AdminService;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.AnswerCreationDto;
import alone.studenttesting.service.dto.QuestionCreationDto;
import alone.studenttesting.service.dto.QuestionEditDto;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;
import alone.studenttesting.service.dto.UserEditDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @PostMapping("/blockuser")
    public String blockUser(@RequestParam(value = "id", required = false) @NotBlank @Size(min = 1, max = 50) Long id, Model model) {
        log.info("Block a user by his id, id:" + id);
        adminService.blockUser(id);
        model.addAttribute("id", id);
        return "admin";
    }

    @PostMapping("/unblockuser")
    public String unblockUser(@RequestParam(value = "id", required = false) @NotBlank @Size(min = 1, max = 50) Long id, Model model) {
        log.info("Block a user by his id, id:" + id);
        adminService.unBlockUser(id);
        model.addAttribute("id", id);
        return "admin";
    }

    @GetMapping("/test")
    public String testPage(Model model) {
        model.addAttribute("testEditDto", new TestEditDto());
        return "test";
    }

    @GetMapping("/test/creationPage")
    public String testCreationPage(Model model) {
        model.addAttribute("testCreationDto", new TestCreationDto());
        return "testcreate";
    }

    @PostMapping("/test/create")
    public String createTest(@Valid @ModelAttribute("testCreationDto") TestCreationDto testCreationDto) {
        log.info("Create a test using  TestCreationDto:" + testCreationDto);
        adminService.createTest(testCreationDto);
        return "test";
    }

    @GetMapping("/test/editPage")
    public String testEditPage(Model model) {
        model.addAttribute("testEditDto", new TestEditDto());
        return "testedit";
    }

    @PostMapping("/test/edit")
    public String editTest(@Valid @ModelAttribute("testEditDto") TestEditDto testEditDto) {
        log.info("Edit a test using TestEditDto:" + testEditDto);
        adminService.editTest(testEditDto);
        return "test";
    }

    @PostMapping("/test/delete")
    public String deleteTest(@RequestParam(value = "id", required = false) @NotBlank @Size(min = 1, max = 50) Long id, Model model) {
        log.info("Delete a test using its id, id:" + id);
        adminService.deleteTest(id);
        model.addAttribute("id", id);
        return "test";
    }

    @GetMapping("/question/creationPage")
    public String questionCreationPage(Model model) {
        model.addAttribute("questionCreationDto", new QuestionCreationDto());
        return "questioncreate";
    }

    @PostMapping("/question/create")
    public String createQuestion(@Valid @ModelAttribute("questionCreationDto") QuestionCreationDto questionCreationDto) {
        log.info("Create a question for a test using QuestionCreationDto:" + questionCreationDto);
        adminService.createQuestion(questionCreationDto);
        return "test";
    }

    @GetMapping("/answer/creationPage")
    public String answerCreationPage(Model model) {
        model.addAttribute("answerCreationDto", new AnswerCreationDto());
        return "answercreate";
    }

    @PostMapping("/answer/create")
    public String createAnswer(@Valid @ModelAttribute("answerCreationDto") AnswerCreationDto answerCreationDto) {
        log.info("Create a answer for a question using AnswerCreationDto:" + answerCreationDto);
        adminService.createAnswer(answerCreationDto);
        return "test";
    }

    @GetMapping("/question/editPage")
    public String questionEditPage(Model model) {
        model.addAttribute("questionEditDto", new QuestionEditDto());
        return "questionedit";
    }

    @PostMapping("question/edit")
    public String editQuestion(@Valid @ModelAttribute("questionEditDto") QuestionEditDto questionEditDto) {
        log.info("Edit a question using QuestionEditDto:" + questionEditDto);
        adminService.editQuestion(questionEditDto);
        return "test";
    }

    @GetMapping("/user/editPage")
    public String userEditPage(Model model) {
        model.addAttribute("userEditDto", new UserEditDto());
        return "useredit";
    }

    @PostMapping("user/edit")
    public String editUser(@Valid @ModelAttribute("userEditDto") UserEditDto userEditDto) {
        log.info("Edit user using UserEditDto:" + userEditDto);
        adminService.editUser(userEditDto);
        return "test";
    }

}
