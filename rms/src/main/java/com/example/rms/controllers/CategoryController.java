package com.example.rms.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rms.dtos.CategoryDTO;
import com.example.rms.models.CategoryModel;
import com.example.rms.services.CategoryService;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("category")
public class CategoryController {

  @Autowired
  private CategoryService _categoryService;

  @GetMapping("list/all")
  public ResponseEntity<List<CategoryModel>>getAllCategories(){
    List<CategoryDTO> categoryDTOs=this._categoryService.getAllCategories();
    List<CategoryModel> categoryModels=new ArrayList<>();
    for(var item:categoryDTOs){
      CategoryModel categoryModel=CategoryModel.builder()
                                                   .id(item.id)
                                                   .title(item.title)
                                                   .status(item.status)
                                                   .prodCount(item.prodCount)
                                                 .build();
      categoryModels.add(categoryModel);
    }
    return ResponseEntity.ok(categoryModels);
  }

  @GetMapping("list/active")
  public ResponseEntity<List<CategoryModel>> getActiveCategories(){
    List<CategoryDTO> activeCategoryDTOs=this._categoryService.getActiveCategories();
    List<CategoryModel> activeCategoryModels=new ArrayList<>();
    for(var item:activeCategoryDTOs){
      CategoryModel categoryModel=CategoryModel.builder()
                                                   .id(item.id)
                                                   .title(item.title)
                                                   .status(item.status)
                                                   .prodCount(null)
                                                 .build();
      activeCategoryModels.add(categoryModel);
    }
    return ResponseEntity.ok(activeCategoryModels);
  }

  @PostMapping("add")
  public ResponseEntity<CategoryModel> addCategory(@RequestBody CategoryModel categoryModel){
    CategoryDTO categoryDTO=new CategoryDTO(null,categoryModel.title,null,null);
    categoryDTO=this._categoryService.addCategory(categoryDTO);
    categoryModel.id=categoryDTO.id;
    categoryModel.status=categoryDTO.status;
    categoryModel.prodCount=categoryDTO.prodCount;
    return ResponseEntity.ok(categoryModel);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updateCategory(@RequestBody CategoryModel categoryModel) {
    CategoryDTO categoryDTO=new CategoryDTO(categoryModel.id,categoryModel.title,null,null);
    this._categoryService.updateCategory(categoryDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("change-status/{cateId}")
  public ResponseEntity<Void> changeCategoryStatus(@PathVariable int cateId) {
    this._categoryService.changeCategoryStatus(cateId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
