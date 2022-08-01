package com.example.backendinternproject1.repository;


import com.example.backendinternproject1.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {
}
