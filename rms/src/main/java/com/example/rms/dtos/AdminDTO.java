package com.example.rms.dtos;

import jakarta.annotation.Nonnull; 
import lombok.AllArgsConstructor; 
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
  public Integer id;
  @Nonnull
  public String username; 
  public String password;
}
