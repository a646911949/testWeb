package com.zmedu.pic.detectface.baidu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.youtu.sign.Base64Util;
import com.zmedu.utils.AuthService;
import com.zmedu.utils.FileUtils;
import com.zmedu.utils.GetProfile;

public class liveTest {
	public static void main(String[] args) throws Exception {
		String auth = AuthService.getAuth();
		System.out.println(auth);
		String url="https://aip.baidubce.com/rest/2.0/face/v2/faceverify";

        String params =  URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode( Base64Util.encode(FileUtils.readFileByBytes(GetProfile.getValue("dllPath")+"/zp.bmp")), "UTF-8")+"&face_fields=faceliveness,qualities";  
        
        post(url,auth,params);
	}
	public static String post(String requestUrl, String accessToken, String params) throws Exception {  
        String generalUrl = requestUrl + "?access_token=" + accessToken;  
        URL url = new URL(generalUrl);  
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
        connection.setRequestMethod("POST");  
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
        connection.setRequestProperty("Connection", "Keep-Alive");  
        connection.setUseCaches(false);  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
  
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());  
        out.writeBytes(params);  
        out.flush();  
        out.close();  
  
        connection.connect();  
//        Map<String, List<String>> headers = connection.getHeaderFields();  
//        for (String key : headers.keySet()) {  
//           System.out.println(key + "--->" + headers.get(key));  
//        }  
        BufferedReader in = null;  
        if (requestUrl.contains("nlp"))  
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));  
        else  
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));  
        String result = "";  
        String getLine;  
        while ((getLine = in.readLine()) != null) {  
            result += getLine;  
        }  
        in.close();  
//        System.out.println("baiduresult:" + result);  
        return result;  
    }  
}
