package com.example.backendinternproject1.dto;

import com.example.backendinternproject1.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDTO {
  @JsonProperty("user_id")
  @NotBlank(message = "user Id should not be blank")
  private String userId;
  @NotBlank(message = "user name should not be blank")
  private String name;
  private String avatar;
  private String bio;

  public static UserDTO of(UserEntity user) {
    if (user == null) {
      return null;
    }
    return new UserDTO(
        user.getUserId(),
        user.getName(),
        user.getAvatar(),
        user.getBio()
    );
  }
}
