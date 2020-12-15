package alone.studenttesting.controller;

import alone.studenttesting.entity.Question;
import alone.studenttesting.entity.Test;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestDto;
import alone.studenttesting.service.dto.Test.TestEditDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @GetMapping("/registrationPage")
    public String testPage(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid  @ModelAttribute("registrationDto") RegistrationDto registrationDto) {
        log.info("Creating new user using Registration Dto:" + registrationDto.toString());
        userService.registerUser(registrationDto);
        return "home";
    }

    @ResponseBody
    @GetMapping("/test/allwithsubjects")
    public ResponseEntity<List<TestWithSubjectDto>> getAllTestsWithSubjects() {
        log.info("Retrieving all tests with subjects: " );
        return ResponseEntity.ok()
                .body(userService.getAllTestsWithSubjects());
    }

    @PostMapping("/test/select")
    public String testSelection(@RequestParam(value = "id", required = false) @NotBlank @Size(min = 1, max = 50) Long id, Model model) {
        log.info("Selecting a test and saving it in user, test:" + id);
        userService.testSelection(id);
        model.addAttribute("id", id);
        return "user";
    }

    @GetMapping("/test/preparing")
    public String testPreparing(@RequestParam(value = "id", required = false) @NotBlank @Size(min = 1, max = 50) Long id,  Model model) {
        log.info("Retrieving and Preparing the test and for the user, test:" + id);
        userService.testPreparing(id);
        model.addAttribute("id", id);
        return "user";
    }

    @PostMapping("/test/passing")
    public ResponseEntity<Integer> testPassing(@Valid @RequestBody TestPassingDto testPassingDto) {
        log.info("Passing the test and retrieving results using testPassingDto:" + testPassingDto.toString());
        return ResponseEntity.ok()
                .body(userService.testPassing(testPassingDto));
    }

    @GetMapping("/test/all")
    public ResponseEntity<List<TestDto>> getAllTests(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        log.info("Retrieving all tests with the possibility of sorting and paging, Page number:" + pageNo
                + "Page size:" + pageSize + "Sort by: " + sortBy);
        List<TestDto> list = userService.getAllTests(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
