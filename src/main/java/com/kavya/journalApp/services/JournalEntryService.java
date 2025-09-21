package com.kavya.journalApp.services;

import com.kavya.journalApp.entity.JournalEntry;
import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.repository.JournalEntryRepository;
import com.kavya.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository,
                               UserRepository userRepository,
                               UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) throw new IllegalArgumentException("User not found: " + userName);

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);

        user.getUserJournalEntries().add(saved);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryBYId(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteEntryById(ObjectId id, String userName) {
        try{
            User user = userRepository.findByUserName(userName);
            if (user == null) return;

            boolean present = user.getUserJournalEntries().removeIf(x -> x.getId().equals(id));
            if(present){userService.saveEntry(user);
                journalEntryRepository.deleteById(id);}

        } catch (Exception e) {

            throw new RuntimeException("entry not found" ,e);
        }

    }
}
