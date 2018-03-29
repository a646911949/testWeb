package com.zmedu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class GetProfile {


	public static String getValue(String key) throws Exception {

		Properties properties = new Properties();
		FileInputStream in = new FileInputStream(GetProfile.class.getResource("/").getPath()+"profile.txt");
		properties.load(in);
		String result=(String) properties.get(key);
		in.close();
		return result;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(GetProfile.getValue("dllPath"));
	}
}
