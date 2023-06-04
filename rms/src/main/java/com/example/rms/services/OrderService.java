package com.example.rms.services;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rms.dtos.OrderDTO;
import com.example.rms.entities.Order;
import com.example.rms.helpers.AES;
import com.example.rms.repositories.OrderRepository;

@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  public List<OrderDTO> getInCompleteOrderDTOsByUserId(String userId){
	String keyUserId="key?!.user?><%id";
	Integer userIdAsInt=Integer.parseInt(AES.decrypt(userId,keyUserId));
	List<OrderDTO> orderDTOs=this.orderRepository.getInCompleteOrderDTOsByUserId(userIdAsInt);
	String keyOrderId="key?for<order!Id";
	for(var item:orderDTOs){
	  String encryptedOrderId=AES.encrypt(item.getId(),keyOrderId);
	  item.setId(encryptedOrderId);
	}
	return orderDTOs;
  }

  public List<OrderDTO> getAllCompletedOrderDTOs(){
	List<OrderDTO> orderDTOs=this.orderRepository.getAllCompletedOrderDTOs();

	String keyUserId="key?!.user?><%id";
	String keyOrderId="key?for<order!Id";

	for(var item:orderDTOs){
	  String encreptedUserId=AES.encrypt(item.getUserId(),keyUserId);
	  item.setUserId(encreptedUserId);
	  String encryptedOrderId=AES.encrypt(item.getId(),keyOrderId);
	  item.setId(encryptedOrderId);
	}

	return orderDTOs;
  }

  public Long getAllCompletedOrdersCount() {
    return this.orderRepository.getAllCompletedOrdersCount();
  }

  public OrderDTO addOrder(LinkedHashMap<String,String> orderDetails){
	String title=orderDetails.get("title");
	String userId=orderDetails.get("userId");

    String keyUserId="key?!.user?><%id";
	int userIdAsInt=Integer.parseInt(AES.decrypt(userId,keyUserId));

	Order order=new Order(null,title,userIdAsInt,null,null,null);
	order=this.orderRepository.save(order);

	String keyOrderId="key?for<order!Id";
	String encryptedOrderId=AES.encrypt(order.getId().toString(),keyOrderId);

	OrderDTO addedOrderDTO=this.orderRepository.getInCompleteOrderDTOByOrderId(order.getId());
	addedOrderDTO.setUserId(userId);
	addedOrderDTO.setId(encryptedOrderId);

	return addedOrderDTO;
  }

  public void updateOrder(LinkedHashMap<String,String> orderDetails){
	String orderId=orderDetails.get("id");
	String title=orderDetails.get("title");

	String keyOrderId="key?for<order!Id";
	int orderIdAsInt=Integer.parseInt(AES.decrypt(orderId,keyOrderId));

	Order order=this.orderRepository.findById(orderIdAsInt).orElse(null);
	order.setTitle(title);

    this.orderRepository.save(order);
  }

  public void deleteOrder(String orderId) {
    String keyOrderId="key?for<order!Id";
    int orderIdAsInt=Integer.parseInt(AES.decrypt(orderId,keyOrderId));
    this.orderRepository.deleteById(orderIdAsInt);
  }

  public void completeOrder(String orderId) {
    String keyOrderId="key?for<order!Id";
	int orderIdAsInt=Integer.parseInt(AES.decrypt(orderId,keyOrderId));
	this.orderRepository.completeOrder(orderIdAsInt);
  }
}
