package com.kavya.journalApp.controller;

import com.kavya.journalApp.entity.JournalEntry;
import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.services.JournalEntryService;
import com.kavya.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user" )
public class UserControllerV2 {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(){
       return userService.getAllEntry();
    }
    @PostMapping
    public void addNewUser(@RequestBody User newUser){
        userService.saveEntry(newUser);
    }
    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUser(@RequestBody User user , @PathVariable String userName){
        User userInDB = userService.findUser(userName);
        if(userInDB != null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}