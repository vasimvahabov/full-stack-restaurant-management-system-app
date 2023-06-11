package com.example.rms.config;
  
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;  
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rms.dtos.AdminDTO;
import com.example.rms.dtos.UserDTO; 
import com.example.rms.services.AdminService;
import com.example.rms.services.UserService;
import java.util.Base64; 
import java.util.Date; 
import org.springframework.beans.factory.annotation.Value;    
import org.springframework.stereotype.Component; 
import jakarta.annotation.PostConstruct;    
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;   
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class AuthenticationProvider{
  
  @Value("${secret-key-jwt}")
  private String secretKey; 
  
  private final AdminService _adminService;
  private final UserService _userService;
  
  public AuthenticationProvider(AdminService adminService,UserService userService){
    this._adminService=adminService;
    this._userService=userService;
  }
  
  @PostConstruct
  public void encodeSecretKey(){
    this.secretKey=Base64.getEncoder().encodeToString(secretKey.getBytes());
  }
  
  public String generateToken(int userId,String role){
    Date now=new Date();
    Date valid=new Date(now.getTime()+960000); 
    Algorithm algorithm=Algorithm.HMAC256(secretKey);  
    
    return JWT
            .create()
            .withIssuer("http://localhost:8080")
	  .withAudience("http://localhost:4200")
	  .withClaim("role", "ROLE_"+role)
	  .withClaim("userId",userId) 
	  .withIssuedAt(now)
	  .withExpiresAt(valid)  
	.sign(algorithm);
  }
  
  public Authentication validateToken(String token){
    try{
      Algorithm algorithm=Algorithm.HMAC256(secretKey);
      JWTVerifier verifier=JWT.require(algorithm).build();
      DecodedJWT decoded=verifier.verify(token); 
      
      Claim role=decoded.getClaim("role");
      List<GrantedAuthority> claims=Arrays.asList(
        new SimpleGrantedAuthority(role.asString())
      );   
      int userId=decoded.getClaim("userId").asInt(); 
      return new UsernamePasswordAuthenticationToken(userId,null,claims);
    }catch(JWTVerificationException jwtException){ 
      throw jwtException;
    }
  }
  
  public Authentication validateAdmin(AdminDTO adminDTO) throws 
                                                          InvalidAlgorithmParameterException,
                                                          InvalidKeyException,
                                                          NoSuchPaddingException,
                                                          BadPaddingException,
                                                          IllegalBlockSizeException, 
                                                          NoSuchAlgorithmException{
    int userId=this._adminService.login(adminDTO);   
    return new UsernamePasswordAuthenticationToken(userId,null,null);
  }
  
  public Authentication validateUser(UserDTO userDTO) throws 
                                                          InvalidAlgorithmParameterException,
                                                          InvalidKeyException,
                                                          NoSuchPaddingException,
                                                          BadPaddingException,
                                                          IllegalBlockSizeException, 
                                                          NoSuchAlgorithmException{
    int userId = this._userService.login(userDTO); 
    return new UsernamePasswordAuthenticationToken(userId,null,null);
  }
}