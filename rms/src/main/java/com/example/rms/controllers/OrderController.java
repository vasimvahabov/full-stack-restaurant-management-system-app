package com.example.rms.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.rms.dtos.OrderDTO;
import com.example.rms.services.OrderService;

@RestController 
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  OrderService orderService;

  @GetMapping("list/dto/incomplete/getBy")
  public ResponseEntity<List<OrderDTO>> getInCompleteOrderDTOsByUserId(@RequestParam("userId") String userId){
    List<OrderDTO> inCompleteOrderDTOs=this.orderService.getInCompleteOrderDTOsByUserId(userId);
    return ResponseEntity.ok(inCompleteOrderDTOs);
  }

  @GetMapping("list/dto/completed/all")
  public ResponseEntity<List<OrderDTO>> getAllCompletedOrderDTOs(){
    List<OrderDTO> completedOrderDTOs=this.orderService.getAllCompletedOrderDTOs();
    return ResponseEntity.ok(completedOrderDTOs);
  }

  @GetMapping("count/completed/all")
  public ResponseEntity<Long> getAllCompletedOrdersCount(){
    Long count=this.orderService.getAllCompletedOrdersCount();
    return ResponseEntity.ok(count);
  }

  @PostMapping("add")
  public ResponseEntity<OrderDTO> addOrder(@RequestBody LinkedHashMap<String,String> orderDetails){
	OrderDTO orderDTO=this.orderService.addOrder(orderDetails);
    return ResponseEntity.ok(orderDTO);
  }

  @PutMapping("complete")
  public ResponseEntity<Void> completeOrder(@RequestBody String orderId) {
    this.orderService.completeOrder(orderId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updateOrder(@RequestBody LinkedHashMap<String,String> orderDetails){
    this.orderService.updateOrder(orderDetails);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("delete")
  public ResponseEntity<Void> deleteOrder(@RequestBody String orderId){
    this.orderService.deleteOrder(orderId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
