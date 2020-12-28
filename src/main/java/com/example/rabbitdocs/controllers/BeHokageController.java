package com.example.rabbitdocs.controllers;

import com.example.rabbitdocs.models.User;
import com.example.rabbitdocs.producers.HokageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeHokageController {

    @Autowired
    private HokageProducer hokageProducer;

    @PostMapping("/hokage/be_hokage")
    public ResponseEntity<String> getPdfForDismissal(@RequestBody User user) {
        hokageProducer.produce(user);
        return ResponseEntity.ok("I'm hokage " + user.getLastName() + "!");
    }
}
