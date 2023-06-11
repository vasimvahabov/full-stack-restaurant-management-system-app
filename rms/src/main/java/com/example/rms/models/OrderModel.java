package com.example.rms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import java.math.BigDecimal; 
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class OrderModel {
  public Integer id;
  public String title;
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss")
  public LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss")
  public LocalDateTime updatedAt;
  public Integer userId;
  public String userFullName;
  @Nonnull
  public BigDecimal total;
}
