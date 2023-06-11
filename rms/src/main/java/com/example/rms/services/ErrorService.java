package com.example.rms.services;

import com.example.rms.dtos.ErrorDTO;
import com.example.rms.repositories.ErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rms.entities.Error; 

@Service
public class ErrorService {
  @Autowired
  ErrorRepository _errorRepository;
  
  public void addError(ErrorDTO errorDTO){
    Error error=new Error(null,errorDTO.msg,null);
    this._errorRepository.save(error);
  }
}
