package com.example.rms.models;
 
import jakarta.annotation.Nonnull; 
import lombok.Builder;

@Builder
public class AdminDashboardModel {
  @Nonnull
  public Long categoriesCount;
  @Nonnull
  public Long productsCount;
  @Nonnull
  public Long completedOrdersCount;
  @Nonnull
  public Long positionsCount;
  @Nonnull
  public Long employeesCount;
  @Nonnull
  public Long adminsCount;
  @Nonnull
  public Long usersCount; 
}
