package alone.studenttesting.controller;

import alone.studenttesting.service.AdminService;
import alone.studenttesting.service.dto.AnswerCreationDto;
import alone.studenttesting.service.dto.QuestionCreationDto;
import alone.studenttesting.service.dto.QuestionEditDto;
import alone.studenttesting.service.dto.Test.TestCreationDto;
import alone.studenttesting.service.dto.Test.TestEditDto;
import alone.studenttesting.service.dto.UserEditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PutMapping("/blockuser/{id}")
    public void blockUser(@PathVariable("id") Long id) {
        adminService.blockUser(id);
    }

    @PutMapping("/unblockuser/{id}")
    public void unblockUser(@PathVariable("id") Long id) {
        adminService.unblockUser(id);
    }

    @PostMapping("/createtest")
    public void createTest(@RequestBody TestCreationDto testCreationDto) {
        adminService.createTest(testCreationDto);
    }

    @PostMapping("/createquestion")
    public void createQuestion(@RequestBody QuestionCreationDto questionCreationDto) {
        adminService.createQuestion(questionCreationDto);
    }

    @PostMapping("/createanswer")
    public void createAnswer(@RequestBody AnswerCreationDto answerCreationDto) {
        adminService.createAnswer(answerCreationDto);
    }

    @DeleteMapping("/deletetest/{id}")
    public void deleteTest(@PathVariable("id") Long id) {
        adminService.deleteTest(id);
    }

    @PostMapping("/edittest")
    public void editTest(@RequestBody TestEditDto testEditDto) {
        adminService.editTest(testEditDto);
    }

    @PostMapping("/editquestion")
    public void editQuestion(@RequestBody QuestionEditDto questionEditDto) {
        adminService.editQuestion(questionEditDto);
    }

    @PostMapping("/edituser")
    public void editUser(@RequestBody UserEditDto userEditDto) {
        adminService.editUser(userEditDto);
    }

}
