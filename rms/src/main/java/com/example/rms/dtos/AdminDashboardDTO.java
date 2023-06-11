package com.example.rms.dtos;
 
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDTO {
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
