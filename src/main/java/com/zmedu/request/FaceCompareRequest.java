package com.zmedu.request;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;
import com.qcloud.image.request.AbstractBaseRequest;

public class FaceCompareRequest extends AbstractBaseRequest {

	// �����Ա����ͣ��Ƿ���urlʶ��
	private boolean isUrl;

	// AͼƬ��url
	private String urlA = "";

	// BͼƬ��url
	private String urlB = "";

	// url�б�
	private ArrayList<String> urlList = new ArrayList<String>();

	// ͼƬ�����б�,key=imageA, key=imageB
	private ArrayList<String> imageList = new ArrayList<String>();

	public FaceCompareRequest(String bucketName, String urlA, String urlB) {
		super(bucketName);
		this.isUrl = true;
		this.urlA = urlA;
		this.urlB = urlB;
	}

	public FaceCompareRequest(String[] image) {
		super("");
		this.isUrl = false;
		for (String string : image) {
			imageList.add(string);
		}
	}

	public boolean isUrl() {
		return isUrl;
	}

	public String getUrlA() {
		return urlA;
	}

	public String getUrlB() {
		return urlB;
	}

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public ArrayList<String> getImageList() {
		return imageList;
	}

	public void setImageList(ArrayList<String> imageList) {
		this.imageList = imageList;
	}

	@Override
	public void check_param() throws ParamException {
		super.check_param();
		if (isUrl) {
			CommonParamCheckUtils.AssertNotNull("urlA", urlA);
			CommonParamCheckUtils.AssertNotNull("urlB", urlB);
		} else {
			CommonParamCheckUtils.AssertNotZero("image list", imageList.size());
			CommonParamCheckUtils.AssertExceed("image list", imageList.size(), ClientConfig.getMaxListNum());
		}
	}

}
