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
@Table(name="employees_")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_")
  private Integer id;

  @Column(name = "first_name_")
  private String firstName;

  @Column(name = "last_name_")
  private String lastName;

  @Column(name = "email_")
  private String email;

  @Column(name = "phone_")
  private String phone;

  @Column(name = "pos_id_")
  private Integer posId;

  @Column(name = "status_",insertable = false)
  private Boolean status;
}
