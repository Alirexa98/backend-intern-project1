package com.example.backendinternproject1.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"messages", "users", "creator"})
public class RoomEntity {
  @Id
  @Column(name = "room_id")
  private String roomId;
  @Column(length = 50, nullable = false)
  private String name;
  @Column(name = "users_count", nullable = false)
  private Integer usersCount;

  @OneToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
  private UserEntity creator;

  @ManyToMany
  @JoinTable(name = "user_room",
  joinColumns = @JoinColumn(name = "room_id"),
  inverseJoinColumns = @JoinColumn(name = "user_id"))
  @Setter(AccessLevel.NONE)
  private Set<UserEntity> users;

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
  @Setter(AccessLevel.NONE)
  private Set<MessageEntity> messages;

  public void addUser(UserEntity user) {
    users.add(user);
    usersCount = users.size();
  }

  public void removeUser(UserEntity user) {
    users.remove(user);
    usersCount = users.size();
  }

  public boolean haveUser(String userId) {
    for(var u : users) {
      if (u.getUserId().equals(userId)) {
        return true;
      }
    }
    return false;
  }
}
