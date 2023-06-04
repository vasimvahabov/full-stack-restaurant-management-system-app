package com.example.rms.models;

import jakarta.annotation.Nonnull;
import lombok.Builder;

@Builder
public class UserModel {
  public Integer id;
  @Nonnull
  public String username;
  public String password;
  public String firstName;
  public String lastName;
  public Boolean status;
}
