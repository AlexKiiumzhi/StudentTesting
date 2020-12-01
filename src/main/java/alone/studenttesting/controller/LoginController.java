package alone.studenttesting.controller;

import alone.studenttesting.exception.UserNotFoundException;
import alone.studenttesting.security.JwtTokenResponse;
import alone.studenttesting.service.LoginService;
import alone.studenttesting.service.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<JwtTokenResponse> createCustomer(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(loginService.generateJwtToken(loginDto.getEmail(), loginDto.getPassword()), HttpStatus.OK);
    }

    //TODO add to controller advise
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
