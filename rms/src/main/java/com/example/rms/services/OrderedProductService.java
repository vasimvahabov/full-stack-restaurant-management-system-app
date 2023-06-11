package com.example.rms.services;

import java.awt.Color;
import java.text.DateFormat; 
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.util.List; 
import org.springframework.stereotype.Service;
import com.example.rms.dtos.OrderedProductDTO;
import com.example.rms.entities.Order;
import com.example.rms.entities.OrderedProduct; 
import com.example.rms.entities.Product;
import com.example.rms.repositories.OrderRepository;
import com.example.rms.repositories.OrderedProductRepository;
import com.example.rms.repositories.UserRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter; 
import java.io.ByteArrayOutputStream;
import java.io.IOException;  
import java.math.BigDecimal;
import java.math.RoundingMode; 
import java.time.LocalDateTime;

@Service
public class OrderedProductService {

  private final OrderedProductRepository _opRepository;
  private final UserRepository _userRepository;;
  private final OrderRepository _orderRepository;
  
  public OrderedProductService(UserRepository userRepository,
                              OrderRepository orderRepository,
                              OrderedProductRepository opRepository){
    this._opRepository=opRepository;
    this._userRepository=userRepository;
    this._orderRepository=orderRepository;
  }

  public List<OrderedProductDTO> getOPsByOrderId(int orderId){
    List<OrderedProductDTO> orderedProductDTOs=this._opRepository.getOPsByOrderId(orderId);
    return orderedProductDTOs;
  }

  public void addOP(OrderedProductDTO opDTO){  
    Order order=new Order(opDTO.orderId,null,null,null,null,null,null);
    Product product=new Product(opDTO.prodId,null,null,null,null);
    OrderedProduct orderedProduct=new OrderedProduct(null,null,order,product);
    this._opRepository.save(orderedProduct);

    order=this._orderRepository.findById(order.getId()).orElse(null);
    LocalDateTime ldt=LocalDateTime.now();
    order.setUpdatedAt(ldt);
    this._orderRepository.save(order);
  }

  public void minusOPByOrderIdAndProdId(OrderedProductDTO opDTO){ 
    this._opRepository.minusOPByOrderIdAndProdId(opDTO.orderId,opDTO.prodId);   
    
    Order order=this._orderRepository.findById(opDTO.orderId).orElse(null);
    LocalDateTime ldt=LocalDateTime.now();
    order.setUpdatedAt(ldt);
    this._orderRepository.save(order);
  }

  public void deleteOPByOrderIdAndProdId(OrderedProductDTO opDTO){ 
    this._opRepository.deleteOPByOrderIdAndProdId(opDTO.orderId,opDTO.prodId);   
    
    Order order=this._orderRepository.findById(opDTO.orderId).orElse(null);
    LocalDateTime ldt=LocalDateTime.now();
    order.setUpdatedAt(ldt);
    this._orderRepository.save(order);
  }

  public ByteArrayOutputStream getPDF(int orderId) throws DocumentException, IOException{
    BigDecimal total=new BigDecimal(0);
    total.setScale(2,RoundingMode.HALF_DOWN); 
    
    List<OrderedProductDTO> opDTOs=getOPsByOrderId(orderId); 
    for(var item : opDTOs){
      total=total.add(item.total); 
    }

    Document document=new Document(PageSize.A4);
    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
    PdfWriter.getInstance(document,outputStream);
    
    document.open();
    
    Font font=FontFactory.getFont(FontFactory.TIMES_ROMAN);
    font.setSize(23);
    font.setColor(Color.black);
      
    Paragraph pTitle=new Paragraph("Le Restaurant | Too Hotel",font);
    pTitle.setAlignment(Element.ALIGN_CENTER);
    pTitle.setSpacingAfter(14);

    document.add(pTitle);
    font.setSize(15);
      
    int userId=this._orderRepository.getUserIdByOrderId(orderId);
    String userFullName=this._userRepository.getUserFullNameById(userId);

    Paragraph pOrderId=new Paragraph("Order : "+orderId,font);
    pOrderId.setAlignment(Element.ALIGN_LEFT);

    Paragraph pUserFullName=new Paragraph("Waiter : "+userFullName,font);
    pUserFullName.setAlignment(Element.ALIGN_LEFT);

    Paragraph pTotal=new Paragraph("Total : $"+total,font);
    pTotal.setAlignment(Element.ALIGN_LEFT);
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());
      
    Paragraph pDate=new Paragraph("Date : "+currentDateTime,font);
    pDate.setAlignment(Element.ALIGN_LEFT);
    pDate.setSpacingAfter(5);

    document.add(pOrderId);
    document.add(pUserFullName);
    document.add(pTotal);
    document.add(pDate);

    PdfPTable pTable=new PdfPTable(4);
    pTable.setWidthPercentage(100f);
    pTable.setWidths(new int[]{3,3,1,1});
    pTable.setSpacingBefore(10);
      
    PdfPCell pCell=new PdfPCell();
    pCell.setBackgroundColor(Color.WHITE);
    pCell.setPadding(5);
    font=FontFactory.getFont(FontFactory.TIMES_ROMAN);
    font.setColor(Color.BLACK);
    font.setSize(15);

    pCell.setPhrase(new Phrase("Product",font));
    pTable.addCell(pCell);

    pCell.setPhrase(new Phrase("Category",font));
    pTable.addCell(pCell);
       
    pCell.setPhrase(new Phrase("Count",font));
    pTable.addCell(pCell);

    pCell.setPhrase(new Phrase("Price",font));
    pTable.addCell(pCell);

    for(var item:opDTOs){
      pTable.addCell(String.valueOf(item.prodTitle));
      pTable.addCell(String.valueOf(item.cateTitle));
      pTable.addCell(String.valueOf(item.prodCount));
      pTable.addCell(String.valueOf("$"+item.total));
    }
        
    document.add(pTable);
    document.close();
    outputStream.close();
    return outputStream;
  }
}
