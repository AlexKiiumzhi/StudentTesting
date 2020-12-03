package alone.studenttesting.controller;

import alone.studenttesting.exception.UserNotFoundException;
import alone.studenttesting.security.JwtTokenResponse;
import alone.studenttesting.service.LoginService;
import alone.studenttesting.service.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    public static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<JwtTokenResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        log.info("Authenticate a user using loginDto:" + loginDto);
        return new ResponseEntity<>(loginService.generateJwtToken(loginDto.getEmail(), loginDto.getPassword()), HttpStatus.OK);
    }
    }
