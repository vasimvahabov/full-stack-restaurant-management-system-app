package com.example.rms.models;

import jakarta.annotation.Nonnull;
import lombok.Builder;

@Builder
public class CategoryModel {
  public Integer id;
  @Nonnull
  public String title;
  public Boolean status;
  public Long prodCount;
}
