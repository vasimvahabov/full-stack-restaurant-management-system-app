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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

  @Column(name = "first_name_",nullable = false,length = 50)
  private String firstName;

  @Column(name = "last_name_",nullable = false,length = 50)
  private String lastName;

  @Column(name = "email_",nullable = false,length = 100,unique = true)
  private String email;

  @Column(name = "phone_",nullable = false,length = 16,unique = true)
  private String phone; 
  
  @ColumnDefault(value = "true")
  @Column(name = "status_",nullable =false,insertable = false)
  private Boolean status;
  
  @ManyToOne(targetEntity = Position.class,fetch = FetchType.EAGER,optional=false)
  @JoinColumn(name="pos_id_",nullable = false,referencedColumnName ="id_") 
  private Position position;
}
