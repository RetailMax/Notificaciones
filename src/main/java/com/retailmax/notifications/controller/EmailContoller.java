package com.retailmax.notifications.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailmax.notifications.model.EmailNotification;

import com.retailmax.notifications.service.EmailNotificationService;

@RestController
@RequestMapping("/api/v1/emailnotificacions")
public class EmailContoller {


    @Autowired
    private EmailNotificationService emailService;

    @GetMapping("/listar")
    public ResponseEntity<List<EmailNotification>> listar() {
        List<EmailNotification> lista = emailService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

     @PostMapping
    public ResponseEntity<EmailNotification> crear(@RequestBody EmailNotification emailNotification) {
        EmailNotification guardado = emailService.save(emailNotification);
        return ResponseEntity.ok(guardado);
    }


}





