package com.kavya.journalApp.controller;

import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.repository.UserRepository;
import com.kavya.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUse(){
        List<User> allEntry = userService.getAllEntry();
        if(allEntry != null && !allEntry.isEmpty()){
            return new ResponseEntity<>(allEntry , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/create-new-admin")
    public void createNewAdmin(@RequestBody User user){
        userService.saveAdmin(user);
    }
}
