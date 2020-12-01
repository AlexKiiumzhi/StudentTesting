package alone.studenttesting.controller;

import alone.studenttesting.entity.Test;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestDto;
import alone.studenttesting.service.dto.Test.TestPassingDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Long> registerUser(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok()
                .body(userService.registerUser(registrationDto));
    }

    @GetMapping("/test/allwithsubjects")
    public ResponseEntity<List<TestWithSubjectDto>> getAllTestsWithSubjects() {
        return ResponseEntity.ok()
                .body(userService.getAllTestsWithSubjects());
    }

    @PutMapping("/test/select/{id}")
    public ResponseEntity<List<Test>> testSelection(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(userService.testSelection(id));
    }

    @PostMapping("/test/passing")
    public ResponseEntity<Integer> testPassing(@RequestBody TestPassingDto testPassingDto) {
        return ResponseEntity.ok()
                .body(userService.testPassing(testPassingDto));
    }

    @GetMapping("/test/all")
    public ResponseEntity<List<TestDto>> getAllTests(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<TestDto> list = userService.getAllTests(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
