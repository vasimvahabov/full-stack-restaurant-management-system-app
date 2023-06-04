package com.example.rms.dtos;

import io.micrometer.common.lang.NonNull; 
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDTO {
  @NonNull
  public long categoriesCount;
  @NonNull
  public long productsCount;
  @NonNull
  public long completedOrdersCount;
  @NonNull
  public long positionsCount;
  @NonNull
  public long employeesCount;
  @NonNull
  public long adminsCount;
  @NonNull
  public long usersCount; 
}
