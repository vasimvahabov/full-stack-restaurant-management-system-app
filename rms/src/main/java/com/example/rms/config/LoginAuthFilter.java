package com.example.rms.config;

import com.example.rms.dtos.AdminDTO;
import com.example.rms.dtos.ErrorDTO;
import com.example.rms.dtos.UserDTO;
import com.example.rms.error.ErrorResponse;
import com.example.rms.models.AdminModel;
import com.example.rms.models.UserModel;
import com.example.rms.services.ErrorService;
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
import java.util.LinkedHashMap; 
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.context.SecurityContextHolder;

@Component 
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class LoginAuthFilter extends OncePerRequestFilter{    

  private final ObjectMapper _objectMapper;
  private final AuthenticationProvider _authenticationProvider;
  private final ErrorService _errorService; 
    
  public LoginAuthFilter(AuthenticationProvider authenticationProvider,
                                 ErrorService errorService,ObjectMapper objectMapper){
    this._objectMapper=objectMapper;
    this._authenticationProvider=authenticationProvider;
    this._errorService=errorService;
  }
    
  @Override
  protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,FilterChain filterChain )throws IOException, ServletException{ 
    try{
      if(request.getServletPath().equals("/admin/logIn") 
                                     && HttpMethod.POST.matches(request.getMethod())){      
        System.out.println("com.example.rms.config.LoginAuthFilter.doFilterInternal() admin");
        AdminModel adminModel=_objectMapper.readValue(request.getInputStream(),AdminModel.class); 
        AdminDTO adminDTO=new AdminDTO(null,adminModel.username,adminModel.password); 
        SecurityContextHolder
               .getContext()
	     .setAuthentication(_authenticationProvider.validateAdmin(adminDTO));   
      }
      else if(request.getServletPath().equals("/user/logIn")  
                                     && HttpMethod.POST.matches(request.getMethod())){
        System.out.println("com.example.rms.config.LoginAuthFilter.doFilterInternal() user");
        UserModel userModel=_objectMapper.readValue(request.getInputStream(),UserModel.class); 
        UserDTO userDTO=new UserDTO(null,userModel.username
                                           ,userModel.password,null,null,null);
        SecurityContextHolder
             .getContext()
             .setAuthentication(_authenticationProvider.validateUser(userDTO));  
      }
      doFilter(request, response, filterChain); 
    }catch(EntityNotFoundException | IOException | ServletException loginException){
      System.out.println("com.example.rms.config.LoginAuthFilter.doFilterInternal() user");
      
      SecurityContextHolder.clearContext();
      if(request.getCookies()!=null){
        Cookie[] cookies=request.getCookies();
        for(var item:cookies)
          if(item.getName().equals("token")){
            item.setMaxAge(0); 
            item.setPath("/");
            response.addCookie(item);
          } 
      }
      
      String msg=loginException.getMessage();       
      ErrorDTO errorDTO=new ErrorDTO(null,msg,null);
      this._errorService.addError(errorDTO);
      
      Integer statusCode=null;
      if(loginException instanceof EntityNotFoundException)
        statusCode=HttpStatus.NOT_FOUND.value();
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
      return;
    }
  } 
}


