package com.example.rms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

//import com.example.rms.dtos.OrderDTO;
import com.example.rms.entities.Order;

public interface OrderRepository extends CrudRepository<Order,Integer>{

  @Query(value="select new com.example.rms.dtos.OrderDTO("
  		+"cast(o.id as string),"
  		+"o.title,"
  		+"cast(null as string),"
  		+"cast(null as string),"
  		+"o.createdAt,"
  		+"o.updatedAt,"
 +"COALESCE((select sum(p.price) from OrderedProduct op inner join Product p on op.prodId=p.id where op.orderId=o.id),0))"
  		+"from Order o where o.userId=:USER_ID and o.completed=false order by o.updatedAt desc")
  public List<OrderDTO> getInCompleteOrderDTOsByUserId(@Param("USER_ID") Integer userId);

  @Query(value="select new com.example.rms.dtos.OrderDTO("
  		          +"cast(o.id as string),"
  		          +"o.title,"
  		          +"cast(null as string),"
  		          +"cast(null as string),"
  		          +"o.createdAt,"
  		          +"o.updatedAt,"
  +"COALESCE((select sum(p.price) from OrderedProduct op inner join Product p on op.prodId=p.id where op.orderId=o.id),0))"
  				  +"from Order o inner join User as u on o.userId=u.id where o.id=:ORDER_ID and o.completed=false")
  public OrderDTO getInCompleteOrderDTOByOrderId(@Param("ORDER_ID") Integer orderId);

  @Query(value="select new com.example.rms.dtos.OrderDTO("
  		  		  +"cast(o.id as string),"
  		  		  +"cast(null as string),"
  		  		  +"cast(u.id as string),"
  		  		  +"concat(u.firstName,' ',u.lastName),"
  		  		  +"cast(null as string),"
  		  		  +"o.updatedAt,"
  +"COALESCE((select sum(p.price) from OrderedProduct op inner join Product p on op.prodId=p.id where op.orderId=o.id),0))"
  			+"from Order as o inner join User as u on o.userId=u.id where o.completed=true order by o.updatedAt asc")
  public List<OrderDTO> getAllCompletedOrderDTOs();

  @Modifying
  @Transactional
  @Query(value="update orders_ set completed_=true,updated_at_=now() where id_=:ORDER_ID",nativeQuery = true)
  public void completeOrder(@Param("ORDER_ID") Integer orderId);

  @Query(value ="select count(*) from orders_ where completed_=true",nativeQuery=true)
  public Long getAllCompletedOrdersCount();
}
