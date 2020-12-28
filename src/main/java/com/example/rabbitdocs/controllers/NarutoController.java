package com.example.rabbitdocs.controllers;

import com.example.rabbitdocs.models.User;
import com.example.rabbitdocs.producers.NarutoProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NarutoController {

    @Autowired
    private NarutoProducer narutoProducer;

    @PostMapping("/naruto")
    public ResponseEntity<String> getPdfForNaruto(@RequestBody User user) {
        narutoProducer.produce(user, "naruto");
        return ResponseEntity.ok("Naruto PDF for " + user.getLastName() + " created");
    }
}