package com.zmedu.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.http.DefaultImageHttpClient;
import com.qcloud.image.http.HttpContentType;
import com.qcloud.image.http.HttpMethod;
import com.qcloud.image.http.HttpRequest;
import com.qcloud.image.http.RequestBodyKey;
import com.qcloud.image.http.RequestHeaderKey;
import com.qcloud.image.sign.Credentials;
import com.qcloud.image.sign.Sign;
import com.youtu.sign.Base64Util;
import com.youtu.sign.YoutuSign;
import com.zmedu.request.FaceCompareRequest;
import com.zmedu.request.FaceDetectRequest;
import com.zmedu.request.LiveDetectRequest;
import com.zmedu.sign.Credentials_youtu;

/**
 * http 工具类
 */
public class HttpUtils {
	private ClientConfig config = new ClientConfig();
	private DefaultImageHttpClient httpClient = new DefaultImageHttpClient(config);

	/**
	 * 静态活体检测
	 * @param request 请求包
	 * @param cred 验证包
	 * @return 
	 * @throws Exception
	 */
	public String livedetectpicture(LiveDetectRequest request, Credentials cred) throws Exception {
		request.check_param();

		String sign = Sign.appSign(cred, request.getBucketName(), config.getSignExpired());
		String url = "http://" + config.getQCloudImageDomain() + "/face/livedetectpicture";

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setUrl(url);
		httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
		// httpRequest.addHeader(RequestHeaderKey.USER_AGENT,config.getUserAgent());
		httpRequest.setMethod(HttpMethod.POST);

		httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
		httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
		if (request.isUrl()) {
			httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
			httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
			httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
		} else {
			httpRequest.setImage(request.getImage());
			httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
		}

		return httpClient.sendHttpRequest(httpRequest);
	}
	
	/**
	 * 人脸对比
	 * @param request 请求包
	 * @param cred 验证包
	 * @return
	 * @throws Exception
	 */
	public static String facecompare(FaceCompareRequest request, Credentials_youtu cred) throws Exception {
		request.check_param();
		URL console = new URL("http://api.youtu.qq.com/youtu/api/facecompare");
		StringBuffer sign = new StringBuffer();
		YoutuSign.appSign(cred.getAPP_ID(), cred.getSECRET_ID(), cred.getSECRET_KEY(),
				(new Date().getTime() / 1000) + 259200, cred.getUSER_ID(), sign);

		JSONObject json = new JSONObject();
		json.put("app_id", cred.getAPP_ID());
		if (request.isUrl()) {
			json.put("urlA", request.getUrlA());
			json.put("urlB", request.getUrlB());
		} else {
			List<String> list = request.getImageList();
			if (list.size() < 2)
				throw new Exception("需要对比图片数量不够");
			json.put("imageA", list.get(0));
			json.put("imageB", list.get(1));
		}

		HttpURLConnection conn = (HttpURLConnection) console.openConnection();
		conn.setRequestProperty("Host", "api.youtu.qq.com");
		conn.setRequestProperty("Content-Length", json.length() + "");
		conn.setRequestProperty("Content-Type", "text/json");
		conn.setRequestProperty("Authorization", sign.toString());
		conn.setDoOutput(true);
		conn.connect();

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(json.toString().getBytes("utf-8"));
		// 刷新、关闭
		out.flush();
		out.close();
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String result = "";
		String getLine;
		while ((getLine = in.readLine()) != null) {
			result += getLine;
		}
		in.close();
		// System.err.println("result:" + result);
		return result;
	}
	/**
	 * 人脸识别
	 * @param request 请求包
	 * @param cred 认证包
	 * @return
	 * @throws Exception
	 */
	public static String FaceDetect(FaceDetectRequest request, Credentials_youtu cred) throws Exception {
		request.check_param();
		URL console = new URL("http://api.youtu.qq.com/youtu/api/detectface");
		StringBuffer sign = new StringBuffer();
		YoutuSign.appSign(cred.getAPP_ID(), cred.getSECRET_ID(), cred.getSECRET_KEY(),
				(new Date().getTime() / 1000) + 259200, cred.getUSER_ID(), sign);

		JSONObject json = new JSONObject();
		json.put("app_id", cred.getAPP_ID());
		if (request.isUrl()) {
			json.put("url", request.getUrl());
		} else {
			json.put("image",request.getImage());
		}

		HttpURLConnection conn = (HttpURLConnection) console.openConnection();
		conn.setRequestProperty("Host", "api.youtu.qq.com");
		conn.setRequestProperty("Content-Length", json.length() + "");
		conn.setRequestProperty("Content-Type", "text/json");
		conn.setRequestProperty("Authorization", sign.toString());
		conn.setDoOutput(true);
		conn.connect();

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(json.toString().getBytes("utf-8"));
		// 刷新、关闭
		out.flush();
		out.close();
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String result = "";
		String getLine;
		while ((getLine = in.readLine()) != null) {
			result += getLine;
		}
		in.close();
		// System.err.println("result:" + result);
		return result;

	}
}