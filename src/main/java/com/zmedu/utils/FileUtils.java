package com.zmedu.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.imageio.plugins.common.ImageUtil;

import sun.misc.BASE64Decoder;

public class FileUtils {
	public static byte[] readFileByBytes(String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException();
		} else {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			BufferedInputStream is = new BufferedInputStream(
					new FileInputStream(file));
			try {
				byte[] buf = new byte[1024];
				int len = 0;
				while (-1 != (len = is.read(buf, 0, 1024))) {
					os.write(buf, 0, len);
				}
				byte[] result = os.toByteArray();
				return result;
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				os.close();
			}
		}
	}
	
	public static File GenerateImage(String imgStr,String imageName)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            String imgFilePath = imageName;//新生成的图片  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return new File(imageName);  
        }   
        catch (Exception e)   
        {  
            return null;  
        }  
    }  
}
