package com.example.rms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data; 
import lombok.NoArgsConstructor;  
import org.hibernate.annotations.ColumnDefault; 

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categories_")
public class Category {
  @Id
  @Column(name="id_")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name ="title_",nullable = false,length = 100,unique = true)
  private String title;

  @ColumnDefault(value = "true")
  @Column(name="status_",nullable = false,insertable = false)
  private Boolean status;
}
