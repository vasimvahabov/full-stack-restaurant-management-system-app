package com.example.rms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
 private String id;
 private String title;
 private String userId;
 private String userFullName;
 private String createdAt;
 private String updatedAt;
 private Double total;
}
