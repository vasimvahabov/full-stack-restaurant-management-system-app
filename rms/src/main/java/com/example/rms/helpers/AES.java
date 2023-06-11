package com.example.rms.helpers;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
  public static String encrypt(String message,String key,String iv) throws InvalidKeyException, 
                                                                NoSuchAlgorithmException, 
                                                                InvalidAlgorithmParameterException, 
                                                                NoSuchPaddingException, 
                                                                IllegalBlockSizeException,
                                                                BadPaddingException{
    SecretKeySpec secretKey=new SecretKeySpec(key.getBytes(),"AES");
    Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
    IvParameterSpec ivParameter=new IvParameterSpec(iv.getBytes());
    cipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParameter); 
    byte[] cipherText=cipher.doFinal(message.getBytes());
    return Base64.getEncoder().encodeToString(cipherText);  
  }
}
