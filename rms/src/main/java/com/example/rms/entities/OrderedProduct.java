package com.example.rms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ordered_products_")
public class OrderedProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_")
  private Integer id;

  @Column(name="prod_id_")
  private Integer prodId;

  @Column(name="order_id_")
  private Integer orderId;

  @Column(name="created_at_",insertable=false)
  private LocalDateTime createdAt;
}
