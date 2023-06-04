package com.example.rms.services;

import java.awt.Color;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rms.dtos.OrderedProductDTO;
import com.example.rms.entities.OrderedProduct;
import com.example.rms.helpers.AES;
import com.example.rms.repositories.OrderedProductRepository;
import com.example.rms.repositories.UserRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class OrderedProductService {

  @Autowired
  OrderedProductRepository orderedProductRepository;

  @Autowired
  UserRepository userRepository;

  public List<OrderedProductDTO> getOrderedProductDTOsByOrderId(String orderId){
	String keyOrderId="key?for<order!Id";
    int orderIdAsInt=Integer.parseInt(AES.decrypt(orderId,keyOrderId));
    List<OrderedProductDTO> orderedProductDTOs=this.orderedProductRepository.getOrderedProductDTOsByOrderId(orderIdAsInt);
    String keyProdId="key+)product+:Id";
    for(var item:orderedProductDTOs){
      String encryptedProdId=AES.encrypt(item.getProdId(),keyProdId);
      item.setProdId(encryptedProdId);
    }
    return orderedProductDTOs;
  }

  public void addOrderedProduct(LinkedHashMap<String,String> orderedProductDetails){
    String keyOrderId="key?for<order!Id";
	int orderIdAsInt=Integer.parseInt(AES.decrypt(orderedProductDetails.get("orderId"),keyOrderId));

    String keyProdId="key+)product+:Id";
    int prodIdAsInt=Integer.parseInt(AES.decrypt(orderedProductDetails.get("prodId"),keyProdId));

    OrderedProduct orderedProduct=new OrderedProduct(null,prodIdAsInt,orderIdAsInt,null);
    this.orderedProductRepository.save(orderedProduct);
  }

  public void minusOrderedProductByOrderIdAndProdId(LinkedHashMap<String,String> orderedProductDetails) {
    String keyOrderId="key?for<order!Id";
	int orderIdAsInt=Integer.parseInt(AES.decrypt(orderedProductDetails.get("orderId"),keyOrderId));

	String keyProdId="key+)product+:Id";
	int prodIdAsInt=Integer.parseInt(AES.decrypt(orderedProductDetails.get("prodId"),keyProdId));
    this.orderedProductRepository.minusOrderedProductByOrderIdAndProdId(orderIdAsInt,prodIdAsInt);
  }

  public void deleteOrderedProductsByOrderIdAndProdId(LinkedHashMap<String,String> orderedProductDetails) {
    String keyOrderId="key?for<order!Id";
	int orderIdAsInt=Integer.parseInt(AES.decrypt(orderedProductDetails.get("orderId"),keyOrderId));

	String keyProdId="key+)product+:Id";
	int prodIdAsInt=Integer.parseInt(AES.decrypt(orderedProductDetails.get("prodId"),keyProdId));

    orderedProductRepository.deleteOrderedProductsByOrderIdAndProdId(orderIdAsInt,prodIdAsInt);
  }

  public void getPDF(LinkedHashMap<String,String> orderDetails,HttpServletResponse response){
    List<OrderedProductDTO> orderedProductDTOs=getOrderedProductDTOsByOrderId(orderDetails.get("orderId"));

	double total=0;
	for (OrderedProductDTO element : orderedProductDTOs)
		total+=element.getTotal();

	DecimalFormat df=new DecimalFormat();
	df.setMaximumFractionDigits(2);
	total=Double.parseDouble(df.format(total));

	try{
	  Document document=new Document(PageSize.A4);
	  PdfWriter.getInstance(document,response.getOutputStream());

	  document.open();

	  Font font=FontFactory.getFont(FontFactory.TIMES_ROMAN);
	  font.setSize(23);
	  font.setColor(Color.black);

	  Paragraph pTitle=new Paragraph("Le Restaurant | Too Hôtel",font);
	  pTitle.setAlignment(Element.ALIGN_CENTER);
	  pTitle.setSpacingAfter(14);

	  document.add(pTitle);
	  font.setSize(15);

	  String keyUserId="key?!.user?><%id";
	  int userIdAsInt=Integer.parseInt(AES.decrypt(orderDetails.get("userId"), keyUserId));
	  String userFullName=this.userRepository.getUserFullNameById(userIdAsInt);

	  Paragraph pOrderId=new Paragraph("Order : "+orderDetails.get("orderId"),font);
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

	  PdfPTable table=new PdfPTable(4);
	  table.setWidthPercentage(100f);
	  table.setWidths(new int[]{3,3,1,1});
	  table.setSpacingBefore(10);

	  PdfPCell cell=new PdfPCell();
	  cell.setBackgroundColor(Color.WHITE);
	  cell.setPadding(5);

	  font=FontFactory.getFont(FontFactory.TIMES_ROMAN);
	  font.setColor(Color.BLACK);
	  font.setSize(15);

	  cell.setPhrase(new Phrase("Product",font));
	  table.addCell(cell);

	  cell.setPhrase(new Phrase("Category",font));
	  table.addCell(cell);

	  cell.setPhrase(new Phrase("Count",font));
	  table.addCell(cell);

	  cell.setPhrase(new Phrase("Price",font));
	  table.addCell(cell);

	  for(var item:orderedProductDTOs){
	    table.addCell(String.valueOf(item.getProdName()));
	    table.addCell(String.valueOf(item.getCateName()));
	    table.addCell(String.valueOf(item.getProdCount()));
	    table.addCell(String.valueOf("$"+item.getTotal()));
	  }

	  document.add(table);
	  document.close();
	}
	catch(Exception ex){
	  System.out.println(ex.getMessage());
	}

  }
}
