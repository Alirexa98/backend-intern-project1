package com.example.backendinternproject1.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"room", "sender"})
public class MessageEntity {
  @Id
  @Column(name = "message_id")
  private Long messageId;
  @Column(length = 500, nullable = false)
  private String text;
  @Column(name = "send_time", nullable = false)
  private LocalDateTime sendTime;

  @OneToOne
  @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
  private UserEntity sender;

  @ManyToOne
  @JoinColumn(name = "room_id")
  private RoomEntity room;
}
