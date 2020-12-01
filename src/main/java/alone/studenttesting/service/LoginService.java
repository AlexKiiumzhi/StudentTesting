package alone.studenttesting.service;

import alone.studenttesting.security.JwtTokenResponse;

public interface LoginService {
    JwtTokenResponse generateJwtToken(String email, String password);
}
