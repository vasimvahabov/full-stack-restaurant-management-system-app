package com.example.rms.models;
 
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public class OrderedProductModel {
  public Integer orderId; 
  public Integer prodId; 
  public String prodTitle;
  public Long prodCount;
  public String cateTitle;
  public BigDecimal total;
} 
