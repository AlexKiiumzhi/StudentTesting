package alone.studenttesting.service.impl;

import alone.studenttesting.exception.UserNotFoundException;
import alone.studenttesting.repository.UserRepository;
import alone.studenttesting.security.JwtTokenResponse;
import alone.studenttesting.service.JwtTokenService;
import alone.studenttesting.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JwtTokenResponse generateJwtToken(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> password.equals(user.getPassword()))
                //TODO use passwordEncoder
               /* .filter(user -> passwordEncoder.matches(password, user.getPassword()))*/
                .map(user -> new JwtTokenResponse(jwtTokenService.generateToken(email)))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
