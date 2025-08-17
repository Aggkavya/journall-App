package com.kavya.journalApp.controller;

import com.kavya.journalApp.entity.JournalEntry;
import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.services.JournalEntryService;
import com.kavya.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private  JournalEntryService journalEntryService;

    @Autowired
    private  UserService userService;


    @GetMapping()
    public ResponseEntity<?> getAllEntriesOfUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUser(userName);
        List<JournalEntry> all = user.getUserJournalEntries();

        if(all != null && !all.isEmpty() ){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();

            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntryByID(@PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUser(userName);
        List<JournalEntry> collect = user.getUserJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
           Optional<JournalEntry> entryBYId = journalEntryService.getEntryBYId(myId);
           if(entryBYId.isPresent()){
               return new ResponseEntity<>(entryBYId, HttpStatus.OK);
           }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        journalEntryService.deleteEntryById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntry(

            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUser(userName);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<JournalEntry> collect = user.getUserJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> entryBYId = journalEntryService.getEntryBYId(myId);
            if (entryBYId.isPresent()) {
                JournalEntry old = journalEntryService.getEntryBYId(myId).orElse(null);
                if (old == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

                if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
                    old.setTitle(newEntry.getTitle());
                }
                if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
                    old.setContent(newEntry.getContent());
                }
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}
