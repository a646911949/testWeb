package com.zmedu.request;

import java.io.File;

import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;
import com.qcloud.image.request.AbstractBaseRequest;

/**
 *
 * @author serenazhao �����������
 */
public class FaceDetectRequest extends AbstractBaseRequest {
	// �Ƿ���url
	private boolean isUrl = true;
	// ���ģʽ��0Ϊ�������������1Ϊ�����������
	private int mode = 0;

	// url
	private String url = "";

	// ͼƬ�����б�
	private String image;

	/**
	 * @param bucketName
	 *            bucket����
	 * @param url
	 *            url
	 * @param mode
	 *            ���ģʽ��0Ϊ�������������1Ϊ�����������
	 */
	public FaceDetectRequest(String bucketName, String url, int mode) {
		super("");
		this.isUrl = true;
		this.mode = mode;
		this.url = url;
	}

	public FaceDetectRequest(String image, int mode) {
		super("");
		this.isUrl = false;
		this.mode = mode;
		this.image = image;
	}

	public boolean isUrl() {
		return isUrl;
	}

	public String getUrl() {
		return url;
	}

	public int getMode() {
		return mode;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public void check_param() throws ParamException {
		super.check_param();
		if (isUrl) {
			CommonParamCheckUtils.AssertNotNull("url", url);
		} else {
			CommonParamCheckUtils.AssertNotNull("image content", image);
		}

		if (mode != 0 && mode != 1) {
			throw new ParamException("param mode error, please check!");
		}
	}
}
