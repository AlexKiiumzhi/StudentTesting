package alone.studenttesting.security;

import alone.studenttesting.entity.User;
import alone.studenttesting.repository.UserRepository;
import alone.studenttesting.service.JwtTokenService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(tokenHeader);

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String authToken = requestHeader.substring(7);
            JwtAuthentication authentication = new JwtAuthentication(authToken);
            try {
                String token = (String) authentication.getCredentials();
                String email = jwtService.getEmailFromToken(token);
                Optional<User> optionalUser = userRepository.findByEmail(email);

                JwtAuthenticationProfile profile = jwtService.validateToken(token)
                        .map(aBoolean -> new JwtAuthenticationProfile(optionalUser.get()))
                        //TODO create and replace with JwtTokenException
                        .orElseThrow(() -> new RuntimeException("JWT Token validation failed"));
                SecurityContextHolder.getContext().setAuthentication(profile);

            } catch (JwtException ex) {
                //TODO repalce with JwtTokenException
                throw new RuntimeException("Failed to verify token");
            }
        }
        chain.doFilter(request, response);
    }
}
