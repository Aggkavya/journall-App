package com.kavya.journalApp.controller;

import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.repository.UserRepository;
import com.kavya.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserControllerV2 {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User inDB = userService.findUser(userName);
        if (inDB == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // allow changing username + password (password will be re-encoded in service)
        if (user.getUserName() != null && !user.getUserName().isBlank()) {
            inDB.setUserName(user.getUserName());
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            inDB.setPassword(user.getPassword());
        }
        userService.saveEntry(inDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(){
        Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteUserByUserName(auth.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
