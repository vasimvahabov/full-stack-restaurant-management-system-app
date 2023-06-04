package com.example.rms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rms.dtos.ProductDTO;
import com.example.rms.entities.Product;
import com.example.rms.helpers.AES;
import com.example.rms.repositories.ProductRepository;
import com.example.rms.repositories.UserRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired 
  UserRepository userRepository;
  
  public List<ProductDTO> getAllActiveProductDTOs(){
	List<ProductDTO> activeProducts=this.productRepository.getAllActiveProductDTOs();
    String keyProdId="key+)product+:Id";
    String keyCategoryId="key-$category%id";
	for(var item:activeProducts){
	  String enctryptedProdId=AES.encrypt(item.getId(),keyProdId);
	  String encryptedCategoryId=AES.encrypt(item.getCategoryDTO().getId(),keyCategoryId);
	  item.setId(enctryptedProdId);
	  item.getCategoryDTO().setId(encryptedCategoryId);
	}
	return activeProducts;
  }

  public void changeProductStatus(String productId){
    String keyProdId="key+)product+:Id";
    Integer prodIdAsInteger=Integer.parseInt(AES.decrypt(productId,keyProdId));

    Product product=this.productRepository.findById(prodIdAsInteger).orElse(null);
    boolean productIsActive=product.getIsActive();
    product.setIsActive(!productIsActive);
    this.productRepository.save(product);
  }

  public void updateProduct(ProductDTO productDTO){
	String keyProdId="key+)product+:Id";
	Integer prodIdAsInteger=Integer.parseInt(AES.decrypt(productDTO.getId(),keyProdId));

	String keyCategoryId="key-$category%id";
	Integer categoryIdAsInt=Integer.parseInt(AES.decrypt(productDTO.getCategoryDTO().getId(),keyCategoryId));

	Product product=this.productRepository.findById(prodIdAsInteger).orElse(null);
	product.setName(productDTO.getName());
	product.setPrice(productDTO.getPrice());
	product.setCateId(categoryIdAsInt);

	this.productRepository.save(product);
  }

  public Long getAllProductsCount() {
    return this.productRepository.count();
  }

  public List<ProductDTO> getAllProductDTOs(){
	List<ProductDTO> productDTOs=this.productRepository.getAllProductDTOs();
	String keyProdId="key+)product+:Id";
	String keyCategoryId="key-$category%id";
	for(var item:productDTOs) {
	  String enctryptedProdId=AES.encrypt(item.getId(),keyProdId);
	  String encryptedCategoryId=AES.encrypt(item.getCategoryDTO().getId(),keyCategoryId);
	  item.setId(enctryptedProdId);
	  item.getCategoryDTO().setId(encryptedCategoryId);
	}
	return productDTOs;
  }

  public ProductDTO addProduct(ProductDTO productDTO){
    String keyCategoryId="key-$category%id";
    Integer categoryIdAsInteger=Integer.parseInt(AES.decrypt(productDTO.getCategoryDTO().getId(),keyCategoryId));

    Product product=new Product(null,productDTO.getName(),null,productDTO.getPrice(),categoryIdAsInteger);
    product=this.productRepository.save(product);
    String keyProdId="key+)product+:Id";
    String encryptedProdId=AES.encrypt(product.getId().toString(),keyProdId);

    ProductDTO addedProductDTO=new ProductDTO(encryptedProdId,product.getName(),product.getPrice(),true,null,null,null);
    return addedProductDTO;
  }

}
