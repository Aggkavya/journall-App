package com.kavya.journalApp.services;

import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void saveEntry(User user) {
        // encode if not already BCrypt (startsWith $2)
        String pwd = user.getPassword();
        if (pwd != null && !pwd.startsWith("$2")) {
            user.setPassword(encoder.encode(pwd));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Arrays.asList("USER")); // Spring adds ROLE_ automatically via .roles()
        }
        userRepository.save(user);
    }
    public void saveExisting(User user){
        userRepository.save(user);
    }
    public void saveAdmin(User user){
        String pwd = user.getPassword();
        if (pwd != null && !pwd.startsWith("$2")) {
            user.setPassword(encoder.encode(pwd));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Arrays.asList("USER" , "ADMIN")); // Spring adds ROLE_ automatically via .roles()
        }
        userRepository.save(user);
    }


    public List<User> getAllEntry() {
        return userRepository.findAll();
    }

    public Optional<User> getEntryBYId(ObjectId id) {
        return userRepository.findById(id);
    }


    public void deleteEntryById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findUser(String userName) {
        return userRepository.findByUserName(userName);
    }
}
