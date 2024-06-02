package com.bigGroup.jounralApp.controller.JournalEntry;

import com.bigGroup.jounralApp.entity.JournalEntry;
import com.bigGroup.jounralApp.repository.JournalEntryRepository;
import com.bigGroup.jounralApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/journal")

public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        System.out.println("-------------------------");
        System.out.println("this is from Abhishek updated one");
        return journalEntryService.getAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        System.out.println("this is from create entry\n");
        journalEntryService.saveEntry(myEntry);
        return true;
    }


    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId  myId){

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable  ObjectId  myId){
         journalEntryService.deleteById(myId);
         return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry){
        JournalEntry data = journalEntryService.findById(id).orElse(null);

        if(data != null){
            data.setTitle(data.getTitle() != null && data.getTitle().equals("") ? myEntry.getTitle() : data.getTitle());
            data.setContent(data.getContent() != null && data.getContent().equals("") ? myEntry.getContent() : data.getContent());
        }
        journalEntryService.saveEntry(data);
        return data;
    }
}
