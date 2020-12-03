package alone.studenttesting.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/admin")
public class AdminController {

    public static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @PutMapping("/blockuser/{id}")
    public ResponseEntity<Long> blockUser(@PathVariable("id")@NotBlank @Size(min = 1, max = 50) Long id) {
        log.info("Block a user by his id, id:" + id);
        return ResponseEntity.ok()
                .body(adminService.blockUser(id));
    }

    @PutMapping("/unblockuser/{id}")
    public ResponseEntity<Long> unblockUser(@PathVariable("id")@NotBlank @Size(min = 1, max = 50) Long id) {
        log.info("Unblock a user by his id, id:" + id);
        return ResponseEntity.ok()
                .body(adminService.unBlockUser(id));
    }

    @PostMapping("/createtest")
    public void createTest(@Valid @RequestBody TestCreationDto testCreationDto) {
        log.info("Create a test using  TestCreationDto:" + testCreationDto);
        adminService.createTest(testCreationDto);
    }

    @PostMapping("/createquestion")
    public void createQuestion(@Valid @RequestBody QuestionCreationDto questionCreationDto) {
        log.info("Create a question for a test using  QuestionCreationDto:" + questionCreationDto);
        adminService.createQuestion(questionCreationDto);
    }

    @PostMapping("/createanswer")
    public void createAnswer(@Valid @RequestBody AnswerCreationDto answerCreationDto) {
        log.info("Create a answer for a question using  AnswerCreationDto:" + answerCreationDto);
        adminService.createAnswer(answerCreationDto);
    }

    @DeleteMapping("/deletetest/{id}")
    public void deleteTest(@PathVariable("id")@NotBlank @Size(min = 1, max = 50) Long id) {
        log.info("Delete a test using its id, id:" + id);
        adminService.deleteTest(id);
    }

    @PostMapping("/edittest")
    public void editTest(@Valid @RequestBody TestEditDto testEditDto) {
        log.info("Edit a test using TestEditDto:" + testEditDto);
        adminService.editTest(testEditDto);
    }

    @PostMapping("/editquestion")
    public void editQuestion(@Valid @RequestBody QuestionEditDto questionEditDto) {
        log.info("Edit a question using QuestionEditDto:" + questionEditDto);
        adminService.editQuestion(questionEditDto);
    }

    @PostMapping("/edituser")
    public void editUser(@Valid @RequestBody UserEditDto userEditDto) {
        log.info("Edit user using UserEditDto:" + userEditDto);
        adminService.editUser(userEditDto);
    }

}
