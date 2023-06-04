package com.example.rms.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.rms.dtos.ErrorDTO;
import com.example.rms.error.ErrorResponse;
import com.example.rms.services.ErrorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;  
import org.springframework.boot.autoconfigure.security.SecurityProperties; 
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

@Component 
@Order(SecurityProperties.DEFAULT_FILTER_ORDER+1)
public class JWTAuthFilter extends OncePerRequestFilter{
    
  private final AuthenticationProvider _authenticationProvider;
  private final ErrorService _errorService;
  private final ObjectMapper _objectMapper;

  public JWTAuthFilter(AuthenticationProvider authenticationProvider,
                              ErrorService errorService,ObjectMapper objectMapper){
    this._authenticationProvider=authenticationProvider;
    this._errorService=errorService;
    this._objectMapper=objectMapper;
  }
  
  @Override
  public void doFilterInternal(HttpServletRequest request,HttpServletResponse response,
                       FilterChain filterChain) throws IOException, ServletException{ 
    System.out.println("com.example.rms.config.JWTAuthFilter.doFilterInternal()");
    if(!request.getServletPath().equals("/admin/logIn") && !request.getServletPath().equals("/user/logIn")){
      try{                
        if(request.getCookies()!=null){  
          Cookie[] cookies=request.getCookies();
          String token=null;
          for(var cookie:cookies)
            if(cookie.getName().equals("token"))
              token=cookie.getValue(); 
          if(token!=null){
            System.out.println(token);
            SecurityContextHolder      	  
                    .getContext()
                    .setAuthentication(_authenticationProvider.validateToken(token)); 
            doFilter(request, response, filterChain);
          }          
          else
            throw new BadCredentialsException("Token not found...");
        }
        else
          throw new BadCredentialsException("Token not found...");
      }catch(BadCredentialsException | JWTVerificationException
                             | IOException | ServletException jwtException){
        SecurityContextHolder.clearContext();
        if(request.getCookies()!=null){
          System.out.println("cookie");
          Cookie[] cookies=request.getCookies();
          for(var item:cookies){
            if(item.getName().equals("token")){
              item.setMaxAge(0); 
              item.setPath("/");
              response.addCookie(item);
            }
          }
        } 
        
        String msg=jwtException.getMessage();
        ErrorDTO errorDTO=new ErrorDTO(null,msg,null);
        this._errorService.addError(errorDTO); 
        
        Integer statusCode=null;
        if(jwtException instanceof BadCredentialsException 
                || jwtException instanceof JWTVerificationException ){
          statusCode=HttpStatus.UNAUTHORIZED.value();
          msg="Unauthorized!!!";
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
        return;
      }
    }
  }
}
