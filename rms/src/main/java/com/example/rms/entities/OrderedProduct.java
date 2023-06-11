package com.example.rms.entities;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 
  @Column(name="created_at_",
           nullable = false,
           columnDefinition = "datetime default now()",
           insertable = false)
  private LocalDateTime createdAt;
  
  @ManyToOne(targetEntity = Order.class,fetch =FetchType.EAGER,optional = false)
  @JoinColumn(name="order_id_",nullable = false,referencedColumnName ="id_")
  private Order order;
  
  @ManyToOne(targetEntity = Product.class,fetch = FetchType.EAGER,optional = false)
  @JoinColumn(name="prod_id_",nullable = false,referencedColumnName ="id_")
  private Product product;
}
