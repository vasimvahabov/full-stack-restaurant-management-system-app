package com.example.rms.error;
   
import com.example.rms.services.ErrorService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order; 
import com.example.rms.dtos.ErrorDTO;   
import java.sql.SQLException;
import java.util.LinkedHashMap;  
import org.springframework.core.Ordered; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice; 
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;  

@Order(value =Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ExceptionHandlerExceptionResolver{
	
  @Autowired
  ErrorService _errorService;
  
  @ExceptionHandler(SQLException.class)
  public ResponseEntity<LinkedHashMap> handleSQLException(SQLException exception){
    String msg=exception.getMessage();                
    ErrorDTO errorDTO=new ErrorDTO(null,msg,null);
    this._errorService.addError(errorDTO); 
    
    Integer statusCode=null;
    if(exception.getErrorCode()==1062){  
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
