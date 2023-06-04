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
import com.example.rms.services.ProductService;

@RestController 
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/list/dto/all")
  public ResponseEntity<List<ProductDTO>> getAllProductDTOs(){
	List<ProductDTO> productDTOs=this.productService.getAllProductDTOs();
    return ResponseEntity.ok(productDTOs);
  }

  @GetMapping("/count/all")
  public ResponseEntity<Long> getAllProductsCount(){
    Long count=this.productService.getAllProductsCount();
    return ResponseEntity.ok(count);
  }

  @GetMapping("/list/dto/active/all")
  public ResponseEntity<List<ProductDTO>> getAllActiveProductDTOs(){
	List<ProductDTO> activeProducts=this.productService.getAllActiveProductDTOs();
    return ResponseEntity.ok(activeProducts);
  }

  @PutMapping("change-status")
  public ResponseEntity<Void> changeProductStatus(@RequestBody String productId){
	this.productService.changeProductStatus(productId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updateProduct(@RequestBody ProductDTO productDTO){
	this.productService.updateProduct(productDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/add")
  public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
	ProductDTO addedProductDTO=this.productService.addProduct(productDTO);
	return ResponseEntity.ok(addedProductDTO);
  }
}




