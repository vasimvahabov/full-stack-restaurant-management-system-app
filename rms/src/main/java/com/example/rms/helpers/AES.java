package com.example.rms.helpers;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

  public static String encrypt(String message,String key,String iv){
	try {

	  SecretKeySpec secretKey=new SecretKeySpec(key.getBytes(),"AES");
	  Cipher cipher;
//	  if(iv.length!=0) {
	    cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec ivParameter=new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParameter);
//	  }
//	  else{
//		cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
//		cipher.init(Cipher.ENCRYPT_MODE,secretKey);
//	  }
	  byte[] cipherText=cipher.doFinal(message.getBytes());
      return Base64.getEncoder().encodeToString(cipherText);

	}catch(Exception exception){
	  System.out.println(exception.getMessage());
	}

	return null;
  }

  public static String decrypt(String cipherText,String key,String iv){
	try{

	  SecretKeySpec secretKey=new SecretKeySpec(key.getBytes(),"AES");
	  Cipher cipher;
//	  if(iv.length!=0) {
	    cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec ivParameter=new IvParameterSpec(iv.getBytes());
	    cipher.init(Cipher.DECRYPT_MODE,secretKey,ivParameter);
//	  }
//	  else{
//	    cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
//	    cipher.init(Cipher.DECRYPT_MODE,secretKey);
//	  }
	  byte[] plainTextAsByte=cipher.doFinal(Base64.getDecoder().decode(cipherText));
	  return new String(plainTextAsByte);

	}catch (Exception exception){
	  System.out.println(exception.getMessage());
	}

	return null;
  }
}
