package com.retailmax.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retailmax.notifications.model.EmailNotification;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

}
