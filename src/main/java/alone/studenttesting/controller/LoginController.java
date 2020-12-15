package alone.studenttesting.controller;


import alone.studenttesting.entity.enums.Role;
import alone.studenttesting.service.LoginService;
import alone.studenttesting.service.UserService;
import alone.studenttesting.service.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
public class LoginController {

    public static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@Valid @ModelAttribute("loginDto") LoginDto loginDto, Model model) {
        log.info("Authenticate a user using loginDto:" + loginDto);
        model.addAttribute("token", loginService.generateJwtToken(loginDto.getEmail(), loginDto.getPassword()).getToken());
        String userRole = userService.getUserRole(loginDto.getEmail());
        if (userRole.equals(Role.USER.name())) {
            return "user";
        } else if (userRole.equals(Role.ADMIN.name())) {
            return "admin";
        }
        return "user";
    }

    @PostMapping("/signout")
    public String logOut() {
        return "login";
    }

}
