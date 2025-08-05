package com.kavya.journalApp.services;

import com.kavya.journalApp.entity.JournalEntry;
import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.repository.JournalEntryRepository;
import com.kavya.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){

        userRepository.save(user);
    }
    public List<User> getAllEntry(){
        return userRepository.findAll();
    }
    public Optional<User> getEntryBYId(ObjectId Id){
        return userRepository.findById(Id);

    }
    public void deleteEntryById(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findUser(String userName){
        return userRepository.findByUserName(userName);
    }
}
