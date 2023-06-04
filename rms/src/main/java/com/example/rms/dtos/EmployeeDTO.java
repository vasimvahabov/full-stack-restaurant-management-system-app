package com.example.rms.dtos;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
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
