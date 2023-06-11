package com.example.rms.services;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.example.rms.dtos.ProductDTO;
import com.example.rms.entities.Category;
import com.example.rms.entities.Product; 
import com.example.rms.repositories.ProductRepository; 

@Service
public class ProductService {

  @Autowired
  private ProductRepository _productRepository; 
  
   public List<ProductDTO> getAllProducts(){
    List<ProductDTO> productDTOs=this._productRepository.getAllProducts(); 
    return productDTOs;
  }
  
  public List<ProductDTO> getActiveProducts(){
    List<ProductDTO> activeProducts=this._productRepository.getActiveProducts();
    return activeProducts;
  }

  public void changeProductStatus(int productId){
    Product product=this._productRepository.findById(productId).orElse(null);
    product.setStatus(!product.getStatus());
    this._productRepository.save(product);
  }

  public void updateProduct(ProductDTO productDTO){
    Product product=this._productRepository.findById(productDTO.id).orElse(null);
    product.setTitle(productDTO.title);
    product.setPrice(productDTO.price);
    Category category=new Category(productDTO.cateId,null,null);
    product.setCategory(category);  
    this._productRepository.save(product);
  } 

  public ProductDTO addProduct(ProductDTO productDTO){
    Category category=new Category(productDTO.cateId,null,null);
    Product product=new Product(null,productDTO.title,productDTO.price,null,category);
    product=this._productRepository.save(product);
    productDTO.id=product.getId();
    productDTO.status=true; 
    return productDTO;
  }
}
