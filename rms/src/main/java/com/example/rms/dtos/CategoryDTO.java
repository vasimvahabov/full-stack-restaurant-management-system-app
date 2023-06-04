package com.example.rms.dtos;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor; 
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
  public Integer id;
  @Nonnull
  public String title;
  public Boolean status;
  public Long prodCount;
}
