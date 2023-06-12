package com.example.rms.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.example.rms.dtos.OrderDTO;
import com.example.rms.entities.Order;

public interface OrderRepository extends CrudRepository<Order,Integer>{

  @Query("select new com.example.rms.dtos.OrderDTO("
                                          +"o.id,"
                                          +"o.title,"
                                          +"cast(null as localdatetime),"
                                          +"cast(null as localdatetime),"
                                          +"cast(null as integer),"
                                          +"cast(null as string),"
                                          +"COALESCE(("
                                            +"select sum(op.product.price) from OrderedProduct op "
                                            +"where op.order.id=o.id),0"
                                          +")"
                                        +") from Order o where "
                                                +"o.user.id=:USER_ID and "
                                                +"o.completed=false order by o.updatedAt desc")
  public List<OrderDTO> getInCompleteOrdersByUserId(@Param("USER_ID") int userId); 

  @Query(value="select new com.example.rms.dtos.OrderDTO("
                                          +"o.id,"
                                          +"cast(null as string),"
                                          +"o.createdAt,"
                                          +"o.updatedAt,"
                                          +"cast(null as integer),"
                                          +"concat(o.user.firstName,' ',o.user.lastName),"
                                          +"COALESCE(("
                                            +"select sum(op.product.price) from OrderedProduct op "
                                            +"where op.order.id=o.id),0"
                                          +")"
                                        +") from Order as o where "
                                               +"o.completed=true "
                                               +"order by o.updatedAt asc")
  public List<OrderDTO> getCompletedOrders();

  @Modifying
  @Transactional
  @Query(value="update orders_ set "
                                  +"completed_=true,"
                                  +"updated_at_=now() "
                                    +"where id_=:ORDER_ID",nativeQuery = true)
  public void completeOrder(@Param("ORDER_ID") int orderId);

  @Query(value ="select user_id_ from orders_ where id_=:ORDER_ID",nativeQuery=true)
  public Integer getUserIdByOrderId(@Param("ORDER_ID") int orderId);
}
