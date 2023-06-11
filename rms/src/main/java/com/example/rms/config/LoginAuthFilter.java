package com.example.rms.config;

import com.example.rms.dtos.AdminDTO;
import com.example.rms.dtos.ErrorDTO;
import com.example.rms.dtos.UserDTO;
import com.example.rms.error.ErrorResponse;
import com.example.rms.models.AdminModel;
import com.example.rms.models.UserModel;
import com.example.rms.services.ErrorService;
import com.example.rms.services.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;  
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;  
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException; 
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;  
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus; 
import org.springframework.security.core.context.SecurityContextHolder;

@Component  
public class LoginAuthFilter extends OncePerRequestFilter{    

  private final ObjectMapper _objectMapper;
  private final AuthenticationProvider _authenticationProvider;
  private final ErrorService _errorService; 
  private final LogService _logService; 
    
  public LoginAuthFilter(AuthenticationProvider authenticationProvider,
                       LogService logService,ErrorService errorService,ObjectMapper objectMapper){
    this._objectMapper=objectMapper;
    this._authenticationProvider=authenticationProvider;
    this._errorService=errorService;
    this._logService=logService;
  }
    
  @Override
  protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,FilterChain filterChain )throws IOException, ServletException{ 
    InetAddress address = InetAddress.getByName(request.getRemoteAddr());
    String hostName = address.getHostName();    
    String requestURL=request.getRequestURL().toString();
    
    HashMap<String,String> hashMap=new HashMap<>(2);
    hashMap.put("hostName",hostName);
    hashMap.put("requestURL",requestURL);
    this._logService.addLog(hashMap);
    
    try{
      if("/admin/logIn".equals(request.getServletPath()) && 
        HttpMethod.POST.matches(request.getMethod())){   
        AdminModel adminModel=_objectMapper.readValue(request.getInputStream(),AdminModel.class); 
        AdminDTO adminDTO=new AdminDTO(null,adminModel.username,adminModel.password); 
        SecurityContextHolder
               .getContext()
	     .setAuthentication(_authenticationProvider.validateAdmin(adminDTO));   
      }
      else if("/user/logIn".equals(request.getServletPath()) && 
            HttpMethod.POST.matches(request.getMethod())){ 
        UserModel userModel=_objectMapper.readValue(request.getInputStream(),UserModel.class); 
        UserDTO userDTO=new UserDTO(null,userModel.username,userModel.password,null,null,null);
        SecurityContextHolder
             .getContext()
             .setAuthentication(_authenticationProvider.validateUser(userDTO));  
      }
      doFilter(request, response, filterChain); 
    }catch(EntityNotFoundException | 
           IOException | 
           ServletException | 
           InvalidKeyException| 
           NoSuchAlgorithmException | 
           NoSuchPaddingException | 
           IllegalBlockSizeException | 
           InvalidAlgorithmParameterException | 
           BadPaddingException exception){       
      SecurityContextHolder.clearContext();
      if(request.getCookies()!=null){ 
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
      } 
      
      String msg=exception.getMessage();       
      ErrorDTO errorDTO=new ErrorDTO(null,msg,null);
      this._errorService.addError(errorDTO);
      
      Integer statusCode=null;
      if(exception instanceof EntityNotFoundException)
        statusCode=HttpStatus.NOT_FOUND.value();
      else if(exception instanceof InvalidKeyException || 
            exception instanceof NoSuchAlgorithmException ||
            exception instanceof NoSuchPaddingException || 
            exception instanceof IllegalBlockSizeException ||  
            exception instanceof InvalidAlgorithmParameterException ||
            exception instanceof BadPaddingException){
        statusCode=HttpStatus.INTERNAL_SERVER_ERROR.value();
        msg="Unexpected error happened!!!"; 
      }
      else{
        statusCode=HttpStatus.BAD_REQUEST.value();
        msg="Bad request!!!";
      }
      
      ErrorResponse errorResponse=new ErrorResponse(statusCode,msg);
      LinkedHashMap<String,ErrorResponse> errorHashMap=new LinkedHashMap<>(1);
      errorHashMap.put("errorResponse", errorResponse);
      
      String jsonErrResponse=_objectMapper.writeValueAsString(errorHashMap); 
      response.setStatus(HttpStatus.OK.value());
      response.getWriter().write(jsonErrResponse);   
    }
  } 
}


