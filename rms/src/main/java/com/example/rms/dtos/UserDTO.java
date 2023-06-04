package com.example.rms.dtos;

import jakarta.annotation.Nonnull; 
import lombok.AllArgsConstructor; 
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
  public Integer id;
  @Nonnull
  public String username;
  public String password;
  public String firstName;
  public String lastName;
  public Boolean status;
}
