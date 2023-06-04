package com.example.rms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductDTO {
	public int orderId;
	  public int prodId; 
	  public String prodName;
	  public String cateName;
	  public int prodCount;
	  public double total;
}
