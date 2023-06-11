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

  @Column(name="username_",nullable = false,length = 100,unique = true)
  private String username;

  @Column(name="password_",nullable = false,length = 280)
  private String password;

  @Column(name="first_name_",nullable = false,length = 50)
  private String firstName;

  @Column(name="last_name_",nullable = false,length = 50)
  private String lastName;

  @ColumnDefault(value = "true")
  @Column(name="status_",nullable = false,insertable = false)
  private Boolean status;
}
