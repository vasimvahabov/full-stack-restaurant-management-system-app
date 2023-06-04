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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController  
@RequestMapping("/admin")
public class AdminController { 
  
  private final AuthenticationProvider _authenticationProvider;
  private final AdminService _adminService;
  
  public AdminController(AuthenticationProvider authenticationProvider,AdminService adminService){
    this._authenticationProvider=authenticationProvider;
    this._adminService=adminService;
  }

  @PostMapping("logIn")
  public ResponseEntity<Void> logIn(
                  @AuthenticationPrincipal Integer userId,HttpServletResponse response){
    String token=this._authenticationProvider.generateToken(userId,"admin"); 
    Cookie cookie=new Cookie("token",token);
    cookie.setMaxAge(960);  
    cookie.setPath("/");
//    cookie.isHttpOnly(); 
    response.addCookie(cookie);   
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
  } 
  
  @DeleteMapping("logOut")
  public ResponseEntity<Void> logOut(@AuthenticationPrincipal Integer userId,
                                         HttpServletResponse response,HttpServletRequest request){ 
    Cookie[] cookies=request.getCookies();
    for(var item:cookies)
      if(item.getName().equals("token")){
        item.setMaxAge(0);
        item.setPath("/");
//        item.isHttpOnly();
        response.addCookie(item);
      }  
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
  }
  
  @GetMapping("refresh-token")
  public ResponseEntity<Void>refreshToken(
                    @AuthenticationPrincipal Integer userId,HttpServletResponse response){ 
    String token=this._authenticationProvider.generateToken(userId,"admin"); 
    Cookie cookie=new Cookie("token",token);
    cookie.setMaxAge(960);  
    cookie.setPath("/");
//    cookie.isHttpOnly(); 
    response.addCookie(cookie);   
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);  
  } 

  
  @GetMapping("get")
  public ResponseEntity<AdminDTO> getAdmin(@AuthenticationPrincipal Integer userId){
    AdminDTO adminDTO=this._adminService.getAdminById(userId);
    AdminModel adminModel=AdminModel.builder()
                                            .id(adminDTO.id)
                                            .username(adminDTO.username) 
                                        .build();
    return ResponseEntity.ok(adminDTO);
  }

  @GetMapping("dashboard")
  public ResponseEntity<AdminDashboardModel> 
                             getAdminDashboard(@AuthenticationPrincipal Integer userId){ 
    System.out.println("in admin controller dashboard");    
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
                                                       @RequestBody AdminModel adminModel){
    System.out.println("in admin controller update admin");    
    AdminDTO adminDTO=new AdminDTO(adminModel.id,adminModel.username,adminModel.password);
    this._adminService.updateAdmin(adminDTO);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}
