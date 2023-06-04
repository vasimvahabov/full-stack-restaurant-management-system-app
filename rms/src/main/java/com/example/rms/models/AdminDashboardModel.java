package com.example.rms.models;

import jakarta.annotation.Nonnull; 
import lombok.Builder;

@Builder
public class AdminDashboardModel {
  @Nonnull
  public long categoriesCount;
  @Nonnull
  public long productsCount;
  @Nonnull
  public long completedOrdersCount;
  @Nonnull
  public long positionsCount;
  @Nonnull
  public long employeesCount;
  @Nonnull
  public long adminsCount;
  @Nonnull
  public long usersCount; 
}
