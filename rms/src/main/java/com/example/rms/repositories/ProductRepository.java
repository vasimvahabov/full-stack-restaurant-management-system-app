package com.example.rms.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.example.rms.dtos.ProductDTO;
import com.example.rms.entities.Product;

public interface ProductRepository extends CrudRepository<Product,Integer>{

  @Query("select new com.example.rms.dtos.ProductDTO(p.id,p.title,p.price,p.status,c.id,c.title,c.status)"
  		  +" from Product as p inner join Category as c on p.cateId=c.id")
  public List<ProductDTO> getAllProducts();

  @Query("select new com.example.rms.dtos.ProductDTO(p.id,p.title,p.price,"
          + "cast(null as boolean),c.id,c.title,cast(null as boolean)) from Product "
          + "as p inner join Category as c on p.cateId=c.id where p.status=true")
  public List<ProductDTO> getActiveProducts();

  @Query(value="select count(*) from products_ where cate_id_=:CATE_ID",nativeQuery = true)
  public Long getProductsCountByCategoryId(@Param("CATE_ID") Integer categoryId);
}
