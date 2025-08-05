package com.kavya.journalApp.services;

import com.kavya.journalApp.entity.JournalEntry;
import com.kavya.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAllEntry(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getEntryBYId(ObjectId Id){
        return journalEntryRepository.findById(Id);

    }
    public void deleteEntryById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }
}
