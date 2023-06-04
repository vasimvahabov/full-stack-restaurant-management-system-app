package com.example.rms.models;

import jakarta.annotation.Nonnull;
import lombok.Builder;

@Builder
public class EmployeeModel {
  public Integer id;
  @Nonnull
  public String firstName;
  @Nonnull
  public String lastName;
  @Nonnull
  public String email;
  @Nonnull
  public String phone;
  public Boolean status;
  @Nonnull
  public Integer posId;
  public String posTitle;
  public Boolean posStatus;
}
