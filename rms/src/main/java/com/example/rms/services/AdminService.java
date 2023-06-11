package com.example.rms.services;
 
import com.example.rms.dtos.AdminDTO;
import com.example.rms.dtos.AdminDashboardDTO;
import com.example.rms.entities.Admin;
import com.example.rms.helpers.AES;
import com.example.rms.repositories.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class AdminService {

  @Autowired
  private  AdminRepository _adminRepository;  
  
  public int login(AdminDTO adminDTO) throws InvalidKeyException, 
                                              NoSuchAlgorithmException, 
                                              BadPaddingException, 
                                              NoSuchPaddingException, 
                                              IllegalBlockSizeException,
                                              InvalidAlgorithmParameterException{
    String keyAdminPassword="key-for!password";
    String ivForPassword="iv-for?!password";
    String encryptedPassword=AES.encrypt(adminDTO.password,keyAdminPassword,ivForPassword); 
    
    Admin admin=this._adminRepository.checkLogin(adminDTO.username,encryptedPassword);
    if(admin==null)
      throw new EntityNotFoundException("Invalid username or password!!!"); 
    int userId=admin.getId(); 
    return userId;
  }
  
  public AdminDTO getAdminById(int userId){
    Admin admin=this._adminRepository.findById(userId).orElse(null); 
    AdminDTO adminDTO=new AdminDTO(admin.getId(),admin.getUsername(),null);
    return adminDTO;
  }

  public void updateAdmin(AdminDTO adminDTO) throws InvalidKeyException, 
                                                       NoSuchAlgorithmException, 
                                                       BadPaddingException, 
                                                       NoSuchPaddingException, 
                                                       IllegalBlockSizeException,
                                                       InvalidAlgorithmParameterException{
    Admin admin=this._adminRepository.findById(adminDTO.id).orElse(null);
    admin.setUsername(adminDTO.username);
    if(adminDTO.password!=null) {
      String keyForPassword="key-for!password";
      String ivForPassword="iv-for?!password";
      String encryptedPassword=AES.encrypt(adminDTO.password,keyForPassword,ivForPassword);
      admin.setPassword(encryptedPassword);
    }
    this._adminRepository.save(admin);
  }

  public AdminDashboardDTO getAdminDashboard(){
    return this._adminRepository.getAdminDashboard();
  }
}
