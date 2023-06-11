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
@Table(name = "errors_")
public class Error {	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name ="id_")
  private Integer id;
  
  @Column(name="msg_",nullable = false,length = 255)
  private String msg;
  
  @Column(name="date_",nullable = false,columnDefinition = "datetime default now()",insertable = false)
  private LocalDateTime date;
}
