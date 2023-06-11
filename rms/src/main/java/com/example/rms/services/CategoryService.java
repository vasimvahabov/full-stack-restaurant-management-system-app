package com.example.rms.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.rms.dtos.CategoryDTO; 
import com.example.rms.entities.Category; 
import com.example.rms.repositories.CategoryRepository; 
import com.example.rms.repositories.ProductRepository;

@Service
public class CategoryService {

  private final CategoryRepository _categoryRepository;
  private final ProductRepository _productRepository;
    
  public CategoryService(CategoryRepository categoryRepository,ProductRepository productRepository){
    this._categoryRepository=categoryRepository;
    this._productRepository=productRepository;
  }

  public List<CategoryDTO> getAllCategories(){
    List<CategoryDTO> categoryDTOs=this._categoryRepository.getAllCategories();
    return categoryDTOs;
  }

  public List<CategoryDTO> getActiveCategories(){
    List<CategoryDTO> activeCategories=this._categoryRepository.getActiveCategories();
    return activeCategories;
  }  
  
  public CategoryDTO addCategory(CategoryDTO categoryDTO){
    Category category=new Category(null,categoryDTO.title,null);
    category=this._categoryRepository.save(category);
    categoryDTO.id=category.getId();
    categoryDTO.status=true;
    categoryDTO.prodCount=this._productRepository.getProductsCountByCategoryId(category.getId());
    return categoryDTO;
  }

  public void updateCategory(CategoryDTO categoryDTO){ 
    Category category=this._categoryRepository.findById(categoryDTO.id).orElse(null);
    category.setTitle(categoryDTO.title);
    this._categoryRepository.save(category);
  }

  public void changeCategoryStatus(int cateId){ 
    Category category=this._categoryRepository.findById(cateId).orElse(null); 
    category.setStatus(!category.getStatus());
    this._categoryRepository.save(category);
  }
}
