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
@Table(name="admins_")
public class Admin {
  @Id
  @Column(name="id_")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name="username_")
  private String username;

  @Column(name="password_")
  private String password;
}
