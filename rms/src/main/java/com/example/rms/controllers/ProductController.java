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
import com.example.rms.dtos.ProductDTO;
import com.example.rms.models.ProductModel;
import com.example.rms.services.ProductService;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("product")
public class ProductController {

  @Autowired
  private ProductService _productService;

  @GetMapping("list/all")
  public ResponseEntity<List<ProductModel>> getAllProducts(){
    List<ProductDTO> productDTOs=this._productService.getAllProducts();
    List<ProductModel> productModels=new ArrayList<>();
    for(var item:productDTOs){
      ProductModel productModel=ProductModel.builder()
                                                .id(item.id)
                                                .title(item.title)
                                                .price(item.price)
                                                .status(item.status)
                                                .cateId(item.cateId)
                                                .cateTitle(item.cateTitle)
                                                .cateStatus(item.cateStatus)
                                              .build();
      productModels.add(productModel);
    }
    return ResponseEntity.ok(productModels);
  }
  
  @GetMapping("list/active")
  public ResponseEntity<List<ProductModel>> getActiveProducts(){
    List<ProductDTO> activeProductDTOs=this._productService.getActiveProducts();
    List<ProductModel> activeProductModels=new ArrayList<>();
    for(var item:activeProductDTOs){
      ProductModel productModel=ProductModel.builder()
                                                .id(item.id)
                                                .title(item.title)
                                                .price(item.price)
                                                .status(null)
                                                .cateId(item.cateId)
                                                .cateTitle(null)
                                                .cateStatus(null)
                                              .build();
      activeProductModels.add(productModel);
    }
    return ResponseEntity.ok(activeProductModels);
  }

  @PutMapping("change-status/{prodId}")
  public ResponseEntity<Void> changeProductStatus(@PathVariable int prodId){
    this._productService.changeProductStatus(prodId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updateProduct(@RequestBody ProductModel productModel){
    ProductDTO productDTO=new ProductDTO(productModel.id,
                                             productModel.title,
                                             productModel.price,
                                             null,
                                             productModel.cateId,
                                             null,
                                             null);
    this._productService.updateProduct(productDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("add")
  public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel productModel) {
    ProductDTO productDTO=new ProductDTO(null,
                                            productModel.title,
                                            productModel.price,
                                            null,
                                            productModel.cateId,
                                            null,
                                            null);
    productDTO=this._productService.addProduct(productDTO);
    productModel.id=productDTO.id;
    productModel.status=productDTO.status;
    return ResponseEntity.ok(productModel);
  }
}




