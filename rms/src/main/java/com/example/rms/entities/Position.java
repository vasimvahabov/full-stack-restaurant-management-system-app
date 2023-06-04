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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="positions_")
public class Position {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_")
  private Integer id;

  @Column(name="title_")
  private String title;

  @Column(name="status_",insertable = false)
  private Boolean status;
}


