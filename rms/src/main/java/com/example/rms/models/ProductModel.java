package com.example.rms.models;

import jakarta.annotation.Nonnull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public class ProductModel {
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

