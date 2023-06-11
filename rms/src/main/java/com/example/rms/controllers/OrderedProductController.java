package com.example.rms.controllers;

import java.io.IOException; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.rms.dtos.OrderedProductDTO;
import com.example.rms.models.OrderedProductModel;
import com.example.rms.services.OrderedProductService; 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;   

@RestController 
@RequestMapping("ordered-product")
public class OrderedProductController {

  @Autowired
  OrderedProductService _opService;

  @GetMapping("list")
  public ResponseEntity<List<OrderedProductModel>> getOPsByOrderId(
                                                    @RequestParam("orderId") int orderId){ 
    List<OrderedProductDTO> opDTOs=this._opService.getOPsByOrderId(orderId);
    List<OrderedProductModel> opModels=new ArrayList<>();
    for(var item:opDTOs){
      OrderedProductModel opModel=OrderedProductModel.builder()
                                                          .orderId(item.orderId)
                                                          .prodId(item.prodId)
                                                          .prodTitle(item.prodTitle)
                                                          .prodCount(item.prodCount)
                                                          .cateTitle(item.cateTitle)
                                                          .total(item.total)
                                                        .build();
      opModels.add(opModel);
    }
    return ResponseEntity.ok(opModels);
  }

  @PostMapping("add")
  public ResponseEntity<Void> addOP(@RequestBody OrderedProductModel opModel){
    OrderedProductDTO opDTO=new OrderedProductDTO(opModel.orderId,opModel.prodId,null,null,null,null); 
    this._opService.addOP(opDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("minus")
  public ResponseEntity<Void> minusOPByOrderIdAndProdId(
                               @RequestBody OrderedProductModel opModel){ 
    OrderedProductDTO opDTO=new OrderedProductDTO(opModel.orderId,opModel.prodId,null,null,null,null); 
    this._opService.minusOPByOrderIdAndProdId(opDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("delete")
  public ResponseEntity<Void> deleteOPsByOrderIdAndProdId(
                               @RequestBody OrderedProductModel opModel){
    OrderedProductDTO opDTO=new OrderedProductDTO(opModel.orderId,opModel.prodId,null,null,null,null); 
    this._opService.deleteOPByOrderIdAndProdId(opDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("get-pdf")
  public ResponseEntity<String> getPDF(@RequestParam int orderId) 
                                throws DocumentException, IOException{  
    ByteArrayOutputStream outputStream=this._opService.getPDF(orderId);
    ObjectMapper objectMapper=new ObjectMapper();
    String json=objectMapper.writeValueAsString(outputStream.toByteArray());
    return ResponseEntity.ok(json);
  }
}
