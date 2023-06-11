package com.example.rms.controllers;
 
import com.example.rms.config.AuthenticationProvider;  
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
import com.example.rms.dtos.UserDTO; 
import com.example.rms.models.UserModel;
import com.example.rms.services.UserService;
import jakarta.servlet.http.Cookie; 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List; 
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("user")
public class UserController {

private final UserService _userService;
private final AuthenticationProvider _authenticationProvider;

public UserController(AuthenticationProvider authenticationProvider,UserService userService){
  this._authenticationProvider=authenticationProvider;
  this._userService=userService;
}
  
  @PostMapping("logIn")
  public ResponseEntity<Void> logIn(@AuthenticationPrincipal int userId,
                                     HttpServletResponse response){
    String token=_authenticationProvider.generateToken(userId,"user");
    Cookie cookie=new Cookie("token",token);
    cookie.setMaxAge(960);
    cookie.setPath("/");
    cookie.setDomain("localhost");
    cookie.setHttpOnly(true);
    response.addCookie(cookie);     
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("logOut")
  public ResponseEntity<Void> logOut(HttpServletResponse response,HttpServletRequest request){
    Cookie cookie=new Cookie("token",null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setDomain("localhost");
    cookie.setHttpOnly(true);
    response.addCookie(cookie); 
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
  } 
  
  @GetMapping("refresh-token")
  public ResponseEntity<Void> refreshToken(@AuthenticationPrincipal int userId,
                                             HttpServletResponse response){ 
    String token=this._authenticationProvider.generateToken(userId,"user");
    Cookie cookie=new Cookie("token",token);
    cookie.setMaxAge(960);
    cookie.setPath("/");
    cookie.setDomain("localhost");
    cookie.setHttpOnly(true);
    response.addCookie(cookie);    
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
 }

  @GetMapping("list/all")
  public ResponseEntity<List<UserModel>> getAllUsers(){
    List<UserDTO> userDTOs=_userService.getAllUsers();
    List<UserModel> userModels=new ArrayList<>();
    for(var item:userDTOs){
      UserModel userModel=UserModel.builder()
                                      .id(item.id)
                                      .username(item.username)
                                      .password(null)
                                      .firstName(item.firstName)
                                      .lastName(item.lastName)
                                      .status(item.status)
                                    .build();
      userModels.add(userModel);
    }
    return ResponseEntity.ok(userModels);
  } 
  
  @PutMapping("change-status/{userId}")
  public ResponseEntity<Void> changeUserStatus(@PathVariable int userId){
    this._userService.changeUserStatus(userId);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
  
  @PostMapping("add")
  public ResponseEntity<UserModel> addUser(@RequestBody UserModel userModel) 
                                             throws InvalidKeyException, 
                                             NoSuchAlgorithmException, 
                                             NoSuchPaddingException, 
                                             IllegalBlockSizeException,
                                             BadPaddingException,
                                             InvalidAlgorithmParameterException{ 
    UserDTO userDTO=new UserDTO(null,
                                   userModel.username,
                                   userModel.password,
                                   userModel.firstName,
                                   userModel.lastName,
                                   null);
    userDTO=this._userService.addUser(userDTO);
    userModel.id=userDTO.id;
    userModel.password=userDTO.password;
    userModel.status=userDTO.status;
    return ResponseEntity.ok(userModel);
 }
  
  @PutMapping("update")
  public ResponseEntity<Void> updateUser(@RequestBody UserModel userModel)
                                           throws InvalidKeyException, 
                                           NoSuchAlgorithmException, 
                                           NoSuchPaddingException, 
                                           IllegalBlockSizeException,
                                           BadPaddingException,
                                           InvalidAlgorithmParameterException{
    UserDTO userDTO=new UserDTO(userModel.id,
                                   userModel.username,
                                   userModel.password,
                                   userModel.firstName,
                                   userModel.lastName,
                                   null);
    this._userService.updateUser(userDTO);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

}
