package com.example.rms.dtos;
 
import java.math.BigDecimal;
import lombok.AllArgsConstructor; 
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductDTO {
  public Integer orderId; 
  public Integer prodId; 
  public String prodTitle;
  public Long prodCount;
  public String cateTitle;
  public BigDecimal total;
}
