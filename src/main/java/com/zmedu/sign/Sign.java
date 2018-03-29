package com.zmedu.sign;

import java.util.Date;
import java.util.Random;

import com.youtu.sign.Base64Util;
import com.youtu.sign.HMACSHA1;

public class Sign {
    public static String getSign(String userQQ,String AppID,String SecretID,String SecretKey) throws Exception{  
        long tnowTimes = new Date().getTime()/1000;  
        long enowTimes = tnowTimes+2592000;  
        String rRandomNum = genRandomNum(10);  
        String param = "u=" + userQQ + "&a=" + AppID + "&k=" + SecretID + "&e="  
                + enowTimes + "&t=" + tnowTimes + "&r=" + rRandomNum + "&f=";  
        byte [] hmacSign = HMACSHA1.getSignature(param, SecretKey);  
        byte[] all = new byte[hmacSign.length+param.getBytes().length];  
        System.arraycopy(hmacSign, 0, all, 0, hmacSign.length);  
        System.arraycopy(param.getBytes(), 0, all, hmacSign.length, param.getBytes().length);  
        String sign = Base64Util.encode(all);  
        return sign;  
    }  
    public static String genRandomNum(int length){    
        int  maxNum = 62;    
        int i;    
        int count = 0;    
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};        
        StringBuffer pwd = new StringBuffer("");    
        Random r = new Random();    
        while(count < length){    
         i = Math.abs(r.nextInt(maxNum));       
         if (i >= 0 && i < str.length) {    
          pwd.append(str[i]);    
          count ++;    
         }    
        }    
        return pwd.toString();    
      } 
}
