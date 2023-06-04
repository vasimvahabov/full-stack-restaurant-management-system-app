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
@Table(name="orders_")
public class Order{
  @Id
  @Column(name ="id_")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name="title_")
  private String title;

  @Column(name="user_id_")
  private Integer userId;

  @Column(name="created_at_",insertable = false)
  private LocalDateTime createdAt;

  @Column(name="updated_at_",insertable = false)
  private LocalDateTime updatedAt;

  @Column(name="completed_",insertable = false)
  private Boolean completed;
}
