package com.zmedu.id;

import com.zmedu.utils.GetProfile;

public class test1 {
	public static void main(String[] args) throws Exception {
		Long start=System.currentTimeMillis();
		LoadIdCardInfo id=LoadIdCardInfo.getInstance(1001, GetProfile.getValue("dllPath"));
		id.LoadIDCardInfo();
		System.out.println(id.getDepartment());
		System.out.println(System.currentTimeMillis()-start);
	}
}
	