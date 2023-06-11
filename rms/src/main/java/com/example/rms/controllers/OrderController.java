package com.example.rms.controllers;
 
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
import com.example.rms.models.OrderModel;
import com.example.rms.services.OrderService; 
import java.util.ArrayList;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController 
@RequestMapping("order")
public class OrderController {

  @Autowired
  OrderService _orderService;

  @GetMapping("list/incomplete")
  public ResponseEntity<List<OrderModel>> getInCompleteOrdersByUserId(
                                           @AuthenticationPrincipal int userId){
    List<OrderDTO> inCompleteOrderDTOs=this._orderService.getInCompleteOrdersByUserId(userId);
    List<OrderModel> incompleteOrderModels=new ArrayList<>();
    for(var item:inCompleteOrderDTOs){
      OrderModel orderModel=OrderModel.builder()
                                          .id(item.id)
                                          .title(item.title)
                                          .createdAt(null)
                                          .updatedAt(null)
                                          .userId(null)
                                          .userFullName(null)
                                          .total(item.total)
                                        .build();
      incompleteOrderModels.add(orderModel);
    }
    return ResponseEntity.ok(incompleteOrderModels);
  }

  @GetMapping("list/completed")
  public ResponseEntity<List<OrderModel>> getCompletedOrders(){
    List<OrderDTO> completedOrderDTOs=this._orderService.getCompletedOrders();
    List<OrderModel> completedOrderModels=new ArrayList<>();
    for(var item:completedOrderDTOs){
      OrderModel orderModel=OrderModel.builder()
                                          .id(item.id)
                                          .title(null)
                                          .userId(null)
                                          .userFullName(item.userFullName)
                                          .createdAt(null)
                                          .updatedAt(item.updatedAt)
                                          .total(item.total)
                                        .build();
      completedOrderModels.add(orderModel);
    }
    return ResponseEntity.ok(completedOrderModels);
  } 

  @PostMapping("add")
  public ResponseEntity<OrderModel> addOrder(@AuthenticationPrincipal int userId,
                                              @RequestBody OrderModel orderModel){
    OrderDTO orderDTO=new OrderDTO(null,orderModel.title,null,null,userId,null,null);
    orderDTO=this._orderService.addOrder(orderDTO);
    orderModel.id=orderDTO.id;
    orderModel.total=orderDTO.total;
    return ResponseEntity.ok(orderModel);
  }

  @PutMapping("complete")
  public ResponseEntity<Void> completeOrder(@RequestParam int orderId) {
    this._orderService.completeOrder(orderId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updateOrder(@RequestBody OrderModel orderModel){
    OrderDTO orderDTO=new OrderDTO(orderModel.id,orderModel.title,null,null,null,null,null);
    this._orderService.updateOrder(orderDTO);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("delete")
  public ResponseEntity<Void> deleteOrder(@RequestParam int orderId){
    this._orderService.deleteOrder(orderId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
