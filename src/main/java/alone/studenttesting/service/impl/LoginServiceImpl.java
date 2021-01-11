package alone.studenttesting.service.impl;


import alone.studenttesting.entity.User;
import alone.studenttesting.repository.UserRepository;
import alone.studenttesting.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements UserDetailsService, LoginService {
    public static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Login authentication method that checks if the user exists in the system
     *
     * @param email checking of user is done by email
     */
    @Override
    public UserDetails loadUserByUsername(String email){
        log.info("check for user by email:" + email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            Authentication authentication = new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return optionalUser.get().getAuthorities();
                }
                @Override
                public Object getCredentials() {
                    return null;
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return optionalUser.get();
                }

                @Override
                public boolean isAuthenticated() {
                    return true;
                }

                @Override
                public void setAuthenticated(boolean b) throws IllegalArgumentException {
                }

                @Override
                public String getName() {
                    return null;
                }
            };
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return optionalUser.get();
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }
}
