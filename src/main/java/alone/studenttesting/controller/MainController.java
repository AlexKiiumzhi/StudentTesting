package alone.studenttesting.controller;

import alone.studenttesting.entity.Subject;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.RegistrationDto;
import alone.studenttesting.service.dto.Test.TestWithSubjectDto;
import alone.studenttesting.service.dto.Test.TestsWithSubjectDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
public class MainController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @GetMapping("/home")
        public String home() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "home";
        }

    @GetMapping("/registrationPage")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationDto") RegistrationDto registrationDto, BindingResult bindingResult) {
        log.info("Creating new user using Registration Dto:" + registrationDto.toString());
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        userService.registerUser(registrationDto);
        return "login";
    }

    @GetMapping("/testPage")
    public String testPage(Model model) {

        List<Subject> subjects = userService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "tests";
    }

    @GetMapping("/test/searchBySubject")
    public String searchBySubject(@RequestParam(value = "searchedSubject") Long subjectId, Model model) {
        log.info("Retrieving all tests with subjects: " );
        List<Subject> subjects = userService.getAllSubjects();

        model.addAttribute("subjects", subjects);
        List<TestWithSubjectDto> testWithSubjectDtos = userService.searchBySubject(subjectId);
        model.addAttribute("testWithSubjectDtos", testWithSubjectDtos);
        return "tests";
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

        return "tests";
    }
}




