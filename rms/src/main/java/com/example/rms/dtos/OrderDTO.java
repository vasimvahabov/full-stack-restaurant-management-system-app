package com.example.rms.dtos;

import jakarta.annotation.Nonnull;
import java.math.BigDecimal; 
import java.time.LocalDateTime;
import lombok.AllArgsConstructor; 
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
  public Integer id;
  public String title;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;
  public Integer userId;
  public String userFullName;
  @Nonnull
  public BigDecimal total;
}
