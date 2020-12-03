package alone.studenttesting.controller;

import alone.studenttesting.entity.Question;
import alone.studenttesting.entity.Test;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Long> registerUser(@Valid  @RequestBody RegistrationDto registrationDto) {
        log.info("Creating new user using Registration Dto:" + registrationDto.toString());
        return ResponseEntity.ok()
                .body(userService.registerUser(registrationDto));
    }

    @GetMapping("/test/allwithsubjects")
    public ResponseEntity<List<TestWithSubjectDto>> getAllTestsWithSubjects() {
        log.info("Retrieving all tests with subjects: " );
        return ResponseEntity.ok()
                .body(userService.getAllTestsWithSubjects());
    }

    @PutMapping("/test/select/{id}")
    public ResponseEntity<List<Test>> testSelection(@PathVariable("id")@NotBlank @Size(min = 1, max = 50) Long id) {
        log.info("Selecting a test and saving it in user, test:" + id);
        return ResponseEntity.ok()
                .body(userService.testSelection(id));
    }

    @GetMapping("/test/preparing/{id}")
    public ResponseEntity<List<Question>> testPreparing(@PathVariable("id")@NotBlank @Size(min = 1, max = 50) Long id) {
        log.info("Retrieving and Preparing the test and for the user, test:" + id);
        return ResponseEntity.ok()
                .body(userService.testPreparing(id));
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
