package com.example.rms.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.rms.dtos.OrderedProductDTO;
import com.example.rms.services.OrderedProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;

@RestController 
@RequestMapping("/ordered-products")
public class OrderedProductController {

  @Autowired
  OrderedProductService orderedProductService;

  @GetMapping("/list/dto/getBy")
  public ResponseEntity<List<OrderedProductDTO>> getOrderedProductDTOsByOrderId(@RequestParam("orderId") String orderId){
    List<OrderedProductDTO> orderedProductDTOs=this.orderedProductService.getOrderedProductDTOsByOrderId(orderId);
    return ResponseEntity.ok(orderedProductDTOs);
  }

  @PostMapping("/add")
  public ResponseEntity<Void> addOrderedProduct(@RequestBody LinkedHashMap<String,String> orderedProductDetails) {
    this.orderedProductService.addOrderedProduct(orderedProductDetails);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/minus")
  public ResponseEntity<Void> minusOrderedProductByOrderIdAndProdId(
           @RequestBody LinkedHashMap<String,String> orderedProductDetails){
    this.orderedProductService.minusOrderedProductByOrderIdAndProdId(orderedProductDetails);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteOrderedProductsByOrderIdAndProdId(
		  @RequestBody LinkedHashMap<String,String> orderedProductDetails){
    this.orderedProductService.deleteOrderedProductsByOrderIdAndProdId(orderedProductDetails);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/get-pdf")
  public ResponseEntity<Void> getPDF(@RequestHeader(name = "orderDetails") String json,
		  HttpServletResponse response) throws DocumentException, IOException{
	ObjectMapper mapper=new ObjectMapper();

	@SuppressWarnings(value = "unchecked")
	LinkedHashMap<String,String> orderDetails=mapper.readValue(json,LinkedHashMap.class);

	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());

	String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=bill_" + currentDateTime + ".pdf";
    response.setHeader (headerKey,headerValue);

	this.orderedProductService.getPDF(orderDetails,response);
	response.getOutputStream().flush();
	response.getOutputStream().close();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
