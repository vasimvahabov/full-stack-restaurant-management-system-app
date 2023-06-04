package com.example.rms.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.rms.dtos.CategoryDTO;
import com.example.rms.entities.Category;

public interface CategoryRepository extends CrudRepository<Category,Integer>{

  @Query("select new com.example.rms.dtos.CategoryDTO(c.id,c.title,c.status,count(p))"
  		   +" from Category c left join Product p on c.id=p.cateId group by(c)")
  public List<CategoryDTO> getAllCategories();

  @Query(value ="select new com.example.rms.dtos.CategoryDTO(id,title,"
          + "status,cast(null as int)) from Category where status=true")
  public List<CategoryDTO> getActiveCategories();
}
