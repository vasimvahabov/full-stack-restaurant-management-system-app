package com.example.rms.config;

import com.example.rms.dtos.ErrorDTO;
import com.example.rms.error.ErrorResponse; 
import com.example.rms.services.ErrorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException; 
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException; 
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;  
import java.util.LinkedHashMap;  
import org.springframework.http.HttpStatus; 
import org.springframework.security.core.context.SecurityContextHolder;

@Component 
public class AuthEntryPoint implements AuthenticationEntryPoint{

  private final ObjectMapper _objectMapper;
  private final ErrorService _errorService;
  
  public AuthEntryPoint(ObjectMapper objectMapper,ErrorService errorService){
    this._objectMapper=objectMapper;
    this._errorService=errorService;
  }
    
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException, ServletException{ 
    SecurityContextHolder.clearContext();
    if(request.getCookies()!=null){ 
      Cookie cookie=new Cookie("token",null);
      cookie.setMaxAge(0);
      cookie.setPath("/");
      cookie.setDomain("localhost");
      cookie.setHttpOnly(true);
      response.addCookie(cookie);
    }
    
    String msg=authException.getMessage();  
    ErrorDTO errorDTO=new ErrorDTO(null,msg,null);
    this._errorService.addError(errorDTO);
    
    int statusCode=HttpStatus.UNAUTHORIZED.value();
    msg="Unauthorized!!!";
    ErrorResponse errorResponse=new ErrorResponse(statusCode,msg);
    
    LinkedHashMap<String,ErrorResponse> errorHashMap=new LinkedHashMap<>(1);
    errorHashMap.put("errorResponse", errorResponse);
    
    String jsonErrResponse=_objectMapper.writeValueAsString(errorHashMap); 
    response.setStatus(HttpStatus.OK.value());
    response.getWriter().write(jsonErrResponse);  
  }
}
