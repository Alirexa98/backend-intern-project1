package com.example.backendinternproject1.repository;

import com.example.backendinternproject1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
