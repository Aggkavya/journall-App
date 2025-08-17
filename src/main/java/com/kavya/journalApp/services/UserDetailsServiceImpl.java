package com.kavya.journalApp.services;

import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUserName(username);
        if (u == null) throw new UsernameNotFoundException("User not found: " + username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(u.getUserName())
                .password(u.getPassword()) // must be encoded in UserService
                .roles(u.getRoles() != null ? u.getRoles().toArray(new String[0]) : new String[]{})
                .build();
    }
}
