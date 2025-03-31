package ks.training.sportsShop.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ks.training.sportsShop.entity.User user = this.userService.getUserByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid email or password");
        }
        if (!user.isEnabled()) {
            throw new DisabledException("Tài khoản của bạn đã bị khóa");
        }
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(user.getRole().getName()))
                .accountLocked(!user.isEnabled())
                .build();
    }
}
