package com.zmedu.pic.detectface;

import org.json.JSONObject;

import com.youtu.*;
import com.zmedu.utils.GetProfile;

public class Demo {


	public static void main(String[] args) {

		try {
			Youtu faceYoutu = new Youtu(YouTuAppContants.AppID,
					YouTuAppContants.SecretID, YouTuAppContants.SecretKey,
					Youtu.API_YOUTU_END_POINT, YouTuAppContants.userQQ);
			JSONObject response;
			response= faceYoutu.FaceCompare(GetProfile.getValue("picPath"),GetProfile.getValue("picPath"));
			//response = faceYoutu.DetectFace("test.jpg",1);
			//response = faceYoutu.ImageTerrorismUrl("http://open.youtu.qq.com/app/img/experience/terror/img_terror01.jpg");
			//response = faceYoutu.CarClassifyUrl("http://open.youtu.qq.com/app/img/experience/car/car_01.jpg");
			//response = faceYoutu.BizLicenseOcrUrl("http://open.youtu.qq.com/app/img/experience/char_general/ocr_yyzz_01.jpg");
			//response = faceYoutu.CreditCardOcrUrl("http://open.youtu.qq.com/app/img/experience/char_general/ocr_card_1.jpg");
//			response = faceYoutu.PlateOcrUrl("http://open.youtu.qq.com/app/img/experience/char_general/ocr_license_1.jpg");
			//get respose
			System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
