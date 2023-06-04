package com.example.rms.error;
   
import com.example.rms.services.ErrorService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order; 
import com.example.rms.dtos.ErrorDTO;  
import java.util.LinkedHashMap; 
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice; 
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;  

@Order(value = -2147483644)
@ControllerAdvice
public class RestExceptionHandler extends ExceptionHandlerExceptionResolver{
	
  @Autowired
  ErrorService _errorService;
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<LinkedHashMap> handleException(Exception exception){
    String msg=exception.getMessage(); 
    System.out.println("In handleException -> "+msg);       
            
    ErrorDTO errorDTO=new ErrorDTO(null,msg,null);
    this._errorService.addError(errorDTO); 
    
    Integer statusCode=null;
    if(exception instanceof DataIntegrityViolationException){ 
      statusCode=HttpStatus.CONFLICT.value();    
      if(msg.contains("employees_.email_"))
        msg="That email already exists. Try another...";
      else if(msg.contains("employees_.phone_"))
        msg="That phone number already exists. Try another...";
      else
        msg="That * already exists.Try another...";
    }
    else{
      statusCode=HttpStatus.INTERNAL_SERVER_ERROR.value();
      msg="Unexpected error happened!!!";
    }
    
    ErrorResponse errorResponse=new ErrorResponse(statusCode,msg);
    LinkedHashMap<String,ErrorResponse> errorHashMap=new LinkedHashMap<>(1);
    errorHashMap.put("errorResponse",errorResponse);
    
    return ResponseEntity.ok(errorHashMap);
  } 
	
}
