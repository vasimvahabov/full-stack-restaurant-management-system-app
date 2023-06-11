package com.example.rms.services;

import com.example.rms.entities.Log;
import com.example.rms.repositories.LogRepository; 
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    
  @Autowired 
  LogRepository _logRepository;
  
  public void addLog(HashMap<String,String> hashMap){
    String hostName=hashMap.get("hostName");
    String requestURL=hashMap.get("requestURL");
    Log log=new Log(null,hostName,requestURL,null);
    this._logRepository.save(log);
  }  
}
