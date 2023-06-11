package com.example.rms.entities;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table; 
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  @Column(name="title_",nullable = false,length = 100)
  private String title;
 
  @Column(name="created_at_",
           nullable = false,
           columnDefinition = "datetime default now()",
           insertable = false)
  private LocalDateTime createdAt;
 
  @Column(name="updated_at_",
           nullable = false, 
           columnDefinition = "datetime default now()",
           insertable = false)
  private LocalDateTime updatedAt;
  
  @ColumnDefault(value ="false")
  @Column(name="completed_",nullable = false,insertable = false)
  private Boolean completed;

  @ManyToOne(targetEntity = User.class,fetch =FetchType.EAGER,optional = false)
  @JoinColumn(name="user_id_",nullable = false,referencedColumnName ="id_")
  private User user;
  
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OneToMany(fetch = FetchType.LAZY,targetEntity =OrderedProduct.class,mappedBy ="order")
  public List<OrderedProduct> orderedProducts;
}
