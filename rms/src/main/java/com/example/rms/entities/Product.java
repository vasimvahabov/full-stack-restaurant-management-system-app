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
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products_")
public class Product {

  @Id
  @Column(name ="id_")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title_",nullable = false,length = 100,unique = true)
  private String title;
  
  @Column(name="price_",nullable = false,precision = 6,scale = 2)
  private BigDecimal price;

  @ColumnDefault("true")
  @Column(name="status_",nullable = false,insertable = false)
  private Boolean status;

  @ManyToOne(targetEntity = Category.class,fetch =FetchType.EAGER,optional = false)
  @JoinColumn(name="cate_id_",nullable = false,referencedColumnName = "id_")
  private Category category;
}
