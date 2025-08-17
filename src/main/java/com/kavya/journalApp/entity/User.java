package com.kavya.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String userName;

    private String password;

    @DBRef(lazy = true)
    @Builder.Default
    private List<JournalEntry> userJournalEntries = new ArrayList<>();

    @Builder.Default
    private List<String> roles = new ArrayList<>();
}
