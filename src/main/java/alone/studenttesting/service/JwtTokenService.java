package alone.studenttesting.service;

import java.util.Optional;

public interface JwtTokenService {
    String generateToken(String email);
    String getEmailFromToken(String token);
    Optional<Boolean> validateToken(String token);
}
