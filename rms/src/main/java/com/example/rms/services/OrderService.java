package com.example.rms.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rms.dtos.OrderDTO;
import com.example.rms.entities.Order; 
import com.example.rms.entities.User;
import com.example.rms.repositories.OrderRepository;
import java.math.BigDecimal; 

@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  public List<OrderDTO> getInCompleteOrdersByUserId(int userId){ 
    List<OrderDTO> orderDTOs=this.orderRepository.getInCompleteOrdersByUserId(userId);
    return orderDTOs;
  }

  public List<OrderDTO> getCompletedOrders(){
    List<OrderDTO> orderDTOs=this.orderRepository.getCompletedOrders(); 
    return orderDTOs;
  } 

  public OrderDTO addOrder(OrderDTO orderDTO){
    User user=new User(orderDTO.userId,null,null,null,null,null);
    Order order=new Order(null,orderDTO.title,null,null,null,user,null); 
    order=this.orderRepository.save(order);
    orderDTO.id=order.getId();
    orderDTO.total=new BigDecimal(0);
    return orderDTO;
  }

  public void updateOrder(OrderDTO orderDTO){ 
    Order order=this.orderRepository.findById(orderDTO.id).orElse(null);
    order.setTitle(orderDTO.title);
    this.orderRepository.save(order);
  }

  public void deleteOrder(int orderId) { 
    this.orderRepository.deleteById(orderId);
  }

  public void completeOrder(int orderId) {
    this.orderRepository.completeOrder(orderId);
  }
}
