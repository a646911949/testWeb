package com.zmedu.request;

import java.io.File;

import com.qcloud.image.request.AbstractBaseRequest;

public class LiveDetectRequest extends AbstractBaseRequest {

	private String appid;
	private String url;
	private File image;
	private boolean isUrl = true;

	public LiveDetectRequest(String bucketName) {
		super(bucketName);
		// TODO Auto-generated constructor stub
	}

	public LiveDetectRequest(String bucketName, File image) {
		super(bucketName);
		this.isUrl = false;
		this.image = image;
	}

	public LiveDetectRequest(String bucketName, String url) {
		super(bucketName);
		this.isUrl = true;
		this.url = url;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public boolean isUrl() {
		return isUrl;
	}

	public void setUrl(boolean isUrl) {
		this.isUrl = isUrl;
	}

}
