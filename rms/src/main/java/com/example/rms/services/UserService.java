package com.example.rms.services;  

import com.example.rms.dtos.UserDTO;
import com.example.rms.helpers.AES;
import com.example.rms.entities.User;
import com.example.rms.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository _userRepository;
  
  public int login(UserDTO userDTO) throws InvalidKeyException, 
                                          NoSuchAlgorithmException, 
                                          BadPaddingException, 
                                          NoSuchPaddingException, 
                                          IllegalBlockSizeException,
                                          InvalidAlgorithmParameterException{
    String keyUserPassword="key!userpassword";
    String ivUserPassword="iv!useR?password";
    String encryptedPassword=AES.encrypt(userDTO.password,keyUserPassword,ivUserPassword); 
   
    User user=_userRepository.login(userDTO.username,encryptedPassword);
    if(user==null)  
      throw new EntityNotFoundException("Invalid username or password!!!");  
    int userId=user.getId();
    return userId;
  } 

  public List<UserDTO> getAllUsers(){
    List<UserDTO> userDTOs=this._userRepository.getAllUsers(); 
    return userDTOs;
  } 

  public void changeUserStatus(int userId){  
    User user=this._userRepository.findById(userId).orElse(null);
    user.setStatus(!user.getStatus());
    this._userRepository.save(user);
  }

  public void updateUser(UserDTO userDTO) throws InvalidKeyException, 
                                                 NoSuchAlgorithmException, 
                                                 BadPaddingException, 
                                                 NoSuchPaddingException, 
                                                 IllegalBlockSizeException,
                                                 InvalidAlgorithmParameterException{
    User user=this._userRepository.findById(userDTO.id).orElse(null);
    user.setFirstName(userDTO.firstName);
    user.setLastName(userDTO.lastName);
    user.setUsername(userDTO.username);
    if(userDTO.password!=null){
      String keyUserPassword="key!userpassword";
      String ivUserPassword="iv!useR?password";
      String encryptedPassword=AES.encrypt(userDTO.password,keyUserPassword,ivUserPassword);
      user.setPassword(encryptedPassword);
    }
    this._userRepository.save(user);
  }

  public UserDTO addUser(UserDTO userDTO) throws InvalidKeyException, 
                                                  NoSuchAlgorithmException, 
                                                  BadPaddingException, 
                                                  NoSuchPaddingException, 
                                                  IllegalBlockSizeException,
                                                  InvalidAlgorithmParameterException{
    String keyUserPassword="key!userpassword";
    String ivUserPassword="iv!useR?password";
    String encryptedPassword=AES.encrypt(userDTO.password,keyUserPassword,ivUserPassword);
    User user=new User(null,userDTO.username,encryptedPassword,
			userDTO.firstName,userDTO.lastName,null);
    user=this._userRepository.save(user);
    userDTO.id=user.getId(); 
    userDTO.status=true;
    userDTO.password=null;
    return userDTO;
  }
}
