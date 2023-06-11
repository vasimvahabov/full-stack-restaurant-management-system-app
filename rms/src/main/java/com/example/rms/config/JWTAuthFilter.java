package com.example.rms.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.rms.dtos.ErrorDTO;
import com.example.rms.error.ErrorResponse;
import com.example.rms.services.ErrorService;
import com.fasterxml.jackson.databind.ObjectMapper; 
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
import org.springframework.http.HttpStatus;

@Component  
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
    try{                
      if(!"/admin/logIn".equals(request.getServletPath()) && !"/user/logIn".equals(request.getServletPath())){ 
        if(request.getCookies()!=null){  
          Cookie[] cookies=request.getCookies();
          String token=null;
          for(var cookie:cookies)
            if(cookie.getName().equals("token"))
              token=cookie.getValue(); 
          if(token!=null){  
            SecurityContextHolder      	  
                    .getContext()
                    .setAuthentication(_authenticationProvider.validateToken(token)); 
          }          
          else
            throw new BadCredentialsException("Token not found...");
        }
        else
          throw new BadCredentialsException("Token not found...");
      }
      doFilter(request, response, filterChain);
    }catch(BadCredentialsException | JWTVerificationException 
                         | IOException | ServletException exception){
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
      if(exception instanceof BadCredentialsException || exception instanceof JWTVerificationException){
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
    }
  }
}
