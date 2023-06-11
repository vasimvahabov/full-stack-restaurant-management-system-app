package com.example.rms.repositories;

import com.example.rms.dtos.OrderedProductDTO;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional; 
import com.example.rms.entities.OrderedProduct;

public interface OrderedProductRepository extends CrudRepository<OrderedProduct,Integer>{
  @Query(value = "select new com.example.rms.dtos.OrderedProductDTO("
                                                +"cast(null as integer),"
                                                +"op.product.id,"
                                                +"op.product.title,"
                                                +"count(op.product.id),"
                                                +"op.product.category.title,"
                                                +"sum(op.product.price)"
                                          +") from OrderedProduct op where "
                                             +"op.order.id=:ORDER_ID "
                                               +"group by(op.product.id) order by op.product.id ASC")
  public List<OrderedProductDTO> getOPsByOrderId(@Param("ORDER_ID") int orderId);

  @Modifying
  @Transactional
  @Query(value="delete from ordered_products_ where "
                                              +"prod_id_=:PROD_ID and"
  		                          +"order_id_=:ORDER_ID "
                                              +"order by id_ DESC limit 1",nativeQuery = true)
  public void minusOPByOrderIdAndProdId(@Param("ORDER_ID") int orderId,
                                        @Param("PROD_ID") int prodId);

  @Modifying
  @Transactional
  @Query(value="delete from ordered_products_ where "
                                              +"prod_id_=:PROD_ID and "
                                              +"order_id_=:ORDER_ID",nativeQuery = true)
  public void deleteOPByOrderIdAndProdId(@Param("ORDER_ID") int orderId,
                                         @Param("PROD_ID") int prodId);  
}
