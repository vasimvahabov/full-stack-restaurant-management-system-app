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
@Table(name = "logs_")
public class Log {
  @Id
  @Column(name ="id_")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
 
  @Column(name = "host_name_",nullable = false,columnDefinition = "text")
  private String hostName;
  
  @Column(name = "request_url_",nullable = false,length = 255)
  private String requestURL;
  
  @Column(name="date_",
           nullable = false,
           columnDefinition = "datetime default now()",
           insertable = false)
  private LocalDateTime date;
}
