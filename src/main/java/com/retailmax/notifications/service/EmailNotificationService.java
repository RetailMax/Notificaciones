package com.retailmax.notifications.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailmax.notifications.model.EmailNotification;
import com.retailmax.notifications.repository.EmailNotificationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailNotificationService {

    @Autowired
    private EmailNotificationRepository emailRepository;

    public List<EmailNotification> findAll() {
        return emailRepository.findAll();
    }

}
