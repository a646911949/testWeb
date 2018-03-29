package com.zmedu.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.qcloud.image.sign.Credentials;
import com.youtu.sign.Base64Util;
import com.zmedu.request.FaceCompareRequest;
import com.zmedu.request.FaceDetectRequest;
import com.zmedu.request.LiveDetectRequest;
import com.zmedu.sign.Credentials_youtu;
import com.zmedu.utils.FileUtils;
import com.zmedu.utils.GetProfile;
import com.zmedu.utils.HttpUtils;

public class servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Credentials cred = new Credentials("1255807687", "AKID9FJNJ2OAarxscBHm074vvouI1d9UnsCQ",
			"viNxN1VrFW9iAgHjXAsKcj3FMSTNB93F");
	private Credentials_youtu youtu_cred = new Credentials_youtu();
	private String BUCKET_NAME = "mybucket";
	private HttpUtils http = new HttpUtils();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	// protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	// throws ServletException, IOException {
	// String result;
	// String str = req.getParameter("img"); // System.out.println(str);
	//
	// // 百度活体识别API
	// String auth = AuthService.getAuth();
	// String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceverify";
	//
	// String params = URLEncoder.encode("image", "UTF-8") + "="
	// + URLEncoder.encode(str.replace("data:image/png;base64,", ""), "UTF-8") +
	// "&face_fields=faceliveness";
	//
	// try {
	// JSONObject baidu_result = new JSONObject(liveTest.post(url, auth,
	// params));
	// baidu_result = (JSONObject) baidu_result.getJSONArray("result").get(0);
	// double faceliveness =
	// Double.parseDouble(baidu_result.get("faceliveness").toString());
	//
	// // 腾讯人脸对比API
	// if (faceliveness > 0.9) {
	// String sign;
	// sign = Sign.getSign(YouTuAppContants.userQQ, YouTuAppContants.AppID,
	// YouTuAppContants.SecretID,
	// YouTuAppContants.SecretKey);
	// String image =
	// Base64Util.encode(FileUtils.readFileByBytes(GetProfile.getValue("dllPath")
	// + "/zp.bmp"));
	//
	// JSONObject json = new JSONObject();
	// json.put("app_id", YouTuAppContants.AppID);
	// json.put("imageA", image);
	// json.put("imageB", str.replace("data:image/png;base64,", ""));
	//
	// result = HttpUtils.post(DETECTFACE_URL, json.toString(), "UTF-8", sign);
	// result = "匹配度是:" + new JSONObject(result).get("similarity");
	// } else {
	// result = "不是活人,活体分数为：" + faceliveness;
	// }
	// resp.setCharacterEncoding("utf-8");
	// PrintWriter writer = resp.getWriter();
	// writer.print(result);
	// writer.flush();
	// writer.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// // detectface(str);//腾讯人脸识别api
	//
	// }

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String img = req.getParameter("img").replace("data:image/png;base64,", "");
			String id_img = Base64Util.encode(FileUtils.readFileByBytes(GetProfile.getValue("dllPath") + "/zp.bmp"));
			String result = face(img, id_img);
			resp.setCharacterEncoding("utf-8");
			PrintWriter writer = resp.getWriter();
			writer.print(result);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 一套流程 人脸识别+活体检测+人脸对比
	 * 
	 * @param imageA
	 *            base64后的imageA
	 * @param imageB
	 *            base64后的imageB
	 * @return
	 */
	private String face(String imageA, String imageB) {
		String result = null;
		try {
			/* 人脸检测 */
			FaceDetectRequest fd_request = new com.zmedu.request.FaceDetectRequest(imageA, 0);
			result = HttpUtils.FaceDetect(fd_request, youtu_cred);
			System.out.println("人脸识别错误码：" + new JSONObject(result).get("errorcode"));

			/* 活体检测 */
			// 检测到有人
			if (new JSONObject(result).get("errorcode").equals(0)) {

				LiveDetectRequest ld_request = new LiveDetectRequest(BUCKET_NAME, FileUtils.GenerateImage(imageA,
						"F:/jwj/JAVAHOME/testWeb/src/main/webapp/pic/tmp.jpg"));
				result = http.livedetectpicture(ld_request, cred);
				System.out.println("静态活人检测分数为：" + ((JSONObject) new JSONObject(result).get("data")).get("score"));

				if (new JSONObject(result).get("code").equals(0)) {
					double faceliveness = Double.parseDouble(((JSONObject) new JSONObject(result).get("data")).get(
							"score").toString());
					// 是活人
					if (faceliveness > 0.9) {

						/* 身份证对比 */
						FaceCompareRequest fc_request = new FaceCompareRequest(new String[] { imageA, imageB });
						result = HttpUtils.facecompare(fc_request, youtu_cred);
						result = "匹配度是:" + new JSONObject(result).get("similarity");
						System.out.println(result);

					} else {
						result = "不是活人,活体分数为：" + faceliveness;
					}
				}
			} else {
				result = "找不到人脸。";
			}
		} catch (Exception e) {
			// e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		servlet a=new servlet();
		LiveDetectRequest ld_request = new LiveDetectRequest(a.BUCKET_NAME,new File("d:/IMG_1949.JPG"));
		String result = a.http.livedetectpicture(ld_request, a.cred);
		System.out.println(result);
		System.out.println("静态活人检测分数为：" + ((JSONObject) new JSONObject(result).get("data")).get("score"));
	}
}
