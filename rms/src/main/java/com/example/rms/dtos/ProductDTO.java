package com.example.rms.dtos;

import jakarta.annotation.Nonnull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  public Integer id;
  @Nonnull
  public String title;
  @Nonnull
  public BigDecimal price;
  public Boolean status;
  @Nonnull
  public Integer cateId;
  public String cateTitle;
  public Boolean cateStatus;
}
