package alone.studenttesting.controller;


import alone.studenttesting.entity.enums.Role;
import alone.studenttesting.service.LoginService;
import alone.studenttesting.service.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@Valid @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult) {
        log.info("Authenticate a user using loginDto:" + loginDto);
        if (bindingResult.hasErrors()) {
            return "validationerror";
        }
        loginService.loadUserByUsername(loginDto.getEmail());
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_USER)) {
            return "redirect:/user/home";
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_ADMIN)) {
            return "redirect:/admin/home";
        }
        return "error";
    }

    @RequestMapping(value = "/login/failure")
    public String loginFailure() {
        return "tests";
    }

    @PostMapping("/signout")
    public String logOut() {
        return "login";
    }

}
