package com.example.rms.models;

import jakarta.annotation.Nonnull;
import lombok.Builder;

@Builder
public class PositionModel {
  public Integer id;
  @Nonnull
  public String title;
  public Boolean status;
}
