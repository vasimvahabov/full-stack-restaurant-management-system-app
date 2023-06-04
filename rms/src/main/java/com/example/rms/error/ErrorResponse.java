package com.example.rms.error;

import java.time.LocalDateTime; 
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;  
import jakarta.annotation.Nonnull;

public class ErrorResponse {
  @Nonnull
  public Integer statusCode;
  @JsonFormat(shape =Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss")
  @Nonnull
  public LocalDateTime date;
  @Nonnull
  public String msg;

  public ErrorResponse(){
    this.date=LocalDateTime.now();
  } 
  
  public ErrorResponse(Integer statusCode,String msg){
    this.date=LocalDateTime.now();
    this.statusCode=statusCode;
    this.msg=msg;
  }
}
