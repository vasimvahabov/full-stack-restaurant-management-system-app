package com.example.rms.dtos;

import jakarta.annotation.Nonnull; 
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
  public Integer id;
  @Nonnull
  public String msg;
  public LocalDateTime date;
}
