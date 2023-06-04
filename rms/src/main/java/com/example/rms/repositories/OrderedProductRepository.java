package com.example.rms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

//import com.example.rms.dtos.OrderedProductDTO;
import com.example.rms.entities.OrderedProduct;

public interface OrderedProductRepository extends CrudRepository<OrderedProduct,Integer>{
  @Query(value = "select new com.example.rms.dtos.OrderedProductDTO(cast(null as string),cast(p.id as string),"
  		       + "p.name,c.name,count(p.id),sum(p.price)) from OrderedProduct op inner join Product p on "
  		       + "op.prodId=p.id inner join Category c on p.cateId=c.id where op.orderId=:ORDER_ID group by(p.id) "
  		        +"order by p.id ASC")
  public List<OrderedProductDTO> getOrderedProductDTOsByOrderId(@Param("ORDER_ID") Integer orderID);

  @Modifying
  @Transactional
  @Query(value="delete from ordered_products_ where prod_id_=:PROD_ID "
  		      +"and order_id_=:ORDER_ID order by id_ DESC limit 1",nativeQuery = true)
  public void minusOrderedProductByOrderIdAndProdId(@Param("ORDER_ID") Integer orderId,@Param("PROD_ID") Integer prodId);

  @Modifying
  @Transactional
  @Query(value="delete from ordered_products_ where prod_id_=:PROD_ID and order_id_=:ORDER_ID",nativeQuery = true)
  public void deleteOrderedProductsByOrderIdAndProdId(@Param("ORDER_ID") Integer orderId,@Param("PROD_ID") Integer prodId);
}
