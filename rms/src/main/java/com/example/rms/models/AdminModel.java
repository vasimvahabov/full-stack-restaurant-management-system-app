package com.example.rms.models;

import jakarta.annotation.Nonnull;  
import lombok.Builder;

@Builder
public class AdminModel { 
  public Integer id;
  @Nonnull
  public String username; 
  public String password;
}

