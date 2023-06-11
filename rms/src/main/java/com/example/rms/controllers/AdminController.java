package com.example.rms.controllers;
 
import com.example.rms.config.AuthenticationProvider;
import com.example.rms.dtos.AdminDTO; 
import com.example.rms.dtos.AdminDashboardDTO;
import com.example.rms.models.AdminDashboardModel;
import com.example.rms.models.AdminModel;
import com.example.rms.services.AdminService;
import jakarta.servlet.http.Cookie; 
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
import jakarta.servlet.http.HttpServletResponse; 
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController  
@RequestMapping("admin")
public class AdminController { 
  
  private final AuthenticationProvider _authenticationProvider;
  private final AdminService _adminService;
  
  public AdminController(AuthenticationProvider authenticationProvider,AdminService adminService){
    this._authenticationProvider=authenticationProvider;
    this._adminService=adminService;
  }

  @PostMapping("logIn")
  public ResponseEntity<Void> logIn(@AuthenticationPrincipal int userId,
                                     HttpServletResponse response){ 
    String token=this._authenticationProvider.generateToken(userId,"admin"); 
    Cookie cookie=new Cookie("token",token);
    cookie.setMaxAge(960);
    cookie.setDomain("localhost");
    cookie.setPath("/"); 
    cookie.setHttpOnly(true);
    response.addCookie(cookie);   
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
  } 
  
  @DeleteMapping("logOut")
  public ResponseEntity<Void> logOut(@AuthenticationPrincipal int userId,
                                       HttpServletResponse response,
                                       HttpServletRequest request){ 
    Cookie cookie=new Cookie("token",null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setDomain("localhost");
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
  }
  
  @GetMapping("refresh-token")
  public ResponseEntity<Void>refreshToken(@AuthenticationPrincipal int userId,
                                            HttpServletResponse response){  
    String token=this._authenticationProvider.generateToken(userId,"admin"); 
    Cookie cookie=new Cookie("token",token);
    cookie.setMaxAge(960);  
    cookie.setDomain("localhost");
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    response.addCookie(cookie);   
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);  
  } 

  @GetMapping("get")
  public ResponseEntity<AdminDTO> getAdmin(@AuthenticationPrincipal int userId){
    AdminDTO adminDTO=this._adminService.getAdminById(userId);
    AdminModel adminModel=AdminModel.builder()
                                          .id(adminDTO.id)
                                          .username(adminDTO.username) 
                                          .password(null)
                                        .build();
    return ResponseEntity.ok(adminDTO);
  }

  @GetMapping("dashboard")
  public ResponseEntity<AdminDashboardModel> getAdminDashboard(
                                               @AuthenticationPrincipal int userId){   
    AdminDashboardDTO adminDashboardDTO=this._adminService.getAdminDashboard();
    AdminDashboardModel adminDashboardModel=AdminDashboardModel
            .builder()
                .categoriesCount(adminDashboardDTO.categoriesCount)
                .productsCount(adminDashboardDTO.productsCount)
                .completedOrdersCount(adminDashboardDTO.completedOrdersCount)
                .positionsCount(adminDashboardDTO.positionsCount)
                .employeesCount(adminDashboardDTO.employeesCount)
                .adminsCount(adminDashboardDTO.adminsCount)
                .usersCount(adminDashboardDTO.usersCount)
            .build();
    return ResponseEntity.ok(adminDashboardModel);
  }

  @PutMapping("update")
  public ResponseEntity<Void> updateAdmin(@AuthenticationPrincipal Integer userId,
                                            @RequestBody AdminModel adminModel) 
                                            throws InvalidKeyException, 
                                            NoSuchAlgorithmException, 
                                            NoSuchPaddingException, 
                                            IllegalBlockSizeException, 
                                            BadPaddingException,
                                            InvalidAlgorithmParameterException{ 
    AdminDTO adminDTO=new AdminDTO(adminModel.id,adminModel.username,adminModel.password);
    this._adminService.updateAdmin(adminDTO);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}
