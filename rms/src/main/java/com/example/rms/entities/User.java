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

@Data
@Entity  
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users_")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_")
  private Integer id;

  @Column(name="username_")
  private String username;

  @Column(name="password_")
  private String password;

  @Column(name="first_name_")
  private String firstName;

  @Column(name="last_name_")
  private String lastName;

  @Column(name="status_",insertable = false)
  private Boolean status;
}
