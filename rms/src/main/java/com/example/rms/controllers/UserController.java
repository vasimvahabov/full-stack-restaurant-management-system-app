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
import jakarta.servlet.http.HttpServletResponse; 
import java.util.ArrayList;
import java.util.List; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

private final UserService _userService;
private final AuthenticationProvider _authenticationProvider;

public UserController(AuthenticationProvider authenticationProvider,UserService userService){
  this._authenticationProvider=authenticationProvider;
  this._userService=userService;
}
  
  @PostMapping("logIn")
  public ResponseEntity<Void> logIn(@AuthenticationPrincipal Integer userId,
                                                       HttpServletResponse response){
    String token=_authenticationProvider.generateToken(userId,"user");
    Cookie cookie=new Cookie("token",token);
    cookie.setMaxAge(960000);
    cookie.setPath("/");
//    cookie.isHttpOnly(); 
    response.addCookie(cookie);     
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
  
//  @DeleteMapping("logOut")
//  public ResponseEntity<Void> logOut(HttpServletResponse response,HttpServletRequest request){
//    System.out.println("in user controller");
//    Cookie[] cookies=request.getCookies();
//    for(var item:cookies)
//      if(item.getName().equals("token")){
//        item.setMaxAge(960);
//        item.setPath("/");
////        item.isHttpOnly();
//        response.addCookie(item);
//      } 
//    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
//  } 

  @GetMapping("list/all")
  public ResponseEntity<List<UserModel>> getAllUsers(){
    List<UserDTO> userDTOs=_userService.getAllUsers();
    List<UserModel> userModels=new ArrayList<>();
    for(var item:userDTOs){
      UserModel userModel=UserModel.builder()
                                        .id(item.id)
                                        .username(item.username)
                                        .firstName(item.firstName)
                                        .lastName(item.lastName)
                                        .status(item.status)
                                    .build();
      userModels.add(userModel);
    }
    return ResponseEntity.ok(userModels);
  } 
  
  @PutMapping("/change-status/{userId}")
  public ResponseEntity<Void> changeUserStatus(@PathVariable Integer userId){
    this._userService.changeUserStatus(userId);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
  
  @PostMapping("add")
  public ResponseEntity<UserModel> addUser(@RequestBody UserModel userModel){ 
    UserDTO userDTO=new UserDTO(userModel.id,userModel.username,userModel.password,
                        userModel.firstName,userModel.lastName,userModel.status);
    userDTO=this._userService.addUser(userDTO);
    userModel.id=userDTO.id;
    userModel.password=userDTO.password;
    userModel.status=userDTO.status;
    return ResponseEntity.ok(userModel);
 }
  
  @PutMapping("/update")
  public ResponseEntity<Void> updateUser(@RequestBody UserModel userModel){
    UserDTO userDTO=new UserDTO(userModel.id,userModel.username,userModel.password,
                        userModel.firstName,userModel.lastName,userModel.status);
    this._userService.updateUser(userDTO);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

}
