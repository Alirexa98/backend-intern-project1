package com.example.backendinternproject1.repository;

import com.example.backendinternproject1.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
