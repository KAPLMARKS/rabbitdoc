package com.example.rabbitdocs.controllers;

import com.example.rabbitdocs.models.User;
import com.example.rabbitdocs.producers.WarProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMoneyController {

    @Autowired
    private WarProducer warProducer;

    @PostMapping("/shinobi_war/getmoney")
    public ResponseEntity<String> getPdfForMoney(@RequestBody User user) {
        warProducer.produce(user, "shinobi_war.getmoney");
        return ResponseEntity.ok("Get Money PDF for " + user.getLastName() + " created");
    }
}
