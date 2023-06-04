package com.example.rms.dtos;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor; 
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
  public Integer id;
  @Nonnull
  public String title;
  public Boolean status;
}
