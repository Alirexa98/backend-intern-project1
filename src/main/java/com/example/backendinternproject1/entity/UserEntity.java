package com.example.backendinternproject1.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"rooms"})
public class UserEntity {
  @Id
  @Column(length = 50, name = "user_id")
  private String userId;
  @Column(length = 50, nullable = false)
  private String name;
  @Column(length = 100)
  private String avatar;
  @Column(length = 100)
  private String bio;

  @ManyToMany
  @JoinTable(name = "user_room",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "room_id"))
  @Setter(AccessLevel.NONE)
  private Set<RoomEntity> rooms;
}
