package com.zmedu.id;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;

public class LoadIdCardInfo {

	private static volatile LoadIdCardInfo instance = null;
	private int port = 0;
	private File bmp = null;
	private String dllPath = null;
	private String[] infos = new String[8];

	/**
	 * 打开端口
	 * 
	 * @param Port
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException
	 */
	private int CVR_InitComm() throws NativeException, IllegalAccessException,
			UnsupportedEncodingException {
		JNative n = null;
		try {
			n = new JNative("Termb.dll", "CVR_InitComm");
			n.setRetVal(Type.INT); // 指定返回参数的类型
			n.setParameter(0, port);
			n.invoke(); // 调用方法
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * 验证身份证是否正确放置
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	private int CVR_Authenticate() throws NativeException,
			IllegalAccessException {
		JNative n = null;
		try {
			n = new JNative("Termb.dll", "CVR_Authenticate");
			n.setRetVal(Type.INT); // 指定返回参数的类型
			n.invoke(); // 调用方法
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * 生成身份证信息文件
	 * 
	 * @param Active
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	private int CVR_Read_Content() throws NativeException,
			IllegalAccessException {
		JNative n = null;
		try {
			n = new JNative("Termb.dll", "CVR_Read_Content");
			n.setRetVal(Type.INT); // 指定返回参数的类型
			n.invoke(); // 调用方法
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * 关闭端口
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	private int CVR_CloseComm() throws NativeException, IllegalAccessException {
		JNative n = null;
		try {
			n = new JNative("Termb.dll", "CVR_CloseComm");
			n.setRetVal(Type.INT); // 指定返回参数的类型
			n.invoke(); // 调用方法
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * 获取读取身份证实例
	 * 
	 * @param port
	 *            端口
	 * @param dllPath
	 *            dll的位置
	 * @return
	 */
	public static LoadIdCardInfo getInstance(int port, String dllPath) {
		synchronized (LoadIdCardInfo.class) {
			if (instance == null) {
				instance = new LoadIdCardInfo();
				instance.port = port;
				instance.setDllPath(dllPath);
			}
		}
		return instance;
	}

	private LoadIdCardInfo() {
	}

	private void deleteFiles() {
		File file = new File(dllPath + "/wz.txt");
		if (file.exists())
			file.delete();
		file = new File(dllPath + "/zp.bmp");
		if (file.exists())
			file.delete();
		file = new File(dllPath + "/xp.wlt");
		if (file.exists())
			file.delete();
	}

	/**
	 * 读取身份证信息
	 * 
	 * @throws Exception
	 */
	public void LoadIDCardInfo() throws Exception {
		final ExecutorService exec = Executors.newFixedThreadPool(1);
		Callable<String> call = new Callable<String>() {
			public String call() throws Exception {
				deleteFiles();
				int open_code = CVR_InitComm();
				if (open_code == 1) {
					while (!(CVR_Authenticate() == 1)) {
						Thread.sleep(300);
					}
					CVR_Read_Content();
					File infoFile = new File(dllPath + "/wz.txt");
					BufferedReader reader = new BufferedReader(new FileReader(
							infoFile));
					int count = 0;
					infos = new String[8];
					String str = null;
					while ((str = reader.readLine()) != null) {
						infos[count++] = str;
					}
					reader.close();
					bmp = new File(dllPath + "/zp.bmp");
				} else if (open_code == 2) {
					throw new Exception("串口打开失败");
				}
				return "身份证读取完成";
			}
		};
		try {
			Future<String> future = exec.submit(call);
			String obj = future.get(1000 * 10, TimeUnit.MILLISECONDS);
			System.out.println("任务成功返回:" + obj);
		} catch (TimeoutException ex) {
			System.out.println("处理超时");
		} catch (Exception e) {
			System.out.println("处理失败");
			throw new Exception(e.getMessage());
		} finally {
			CVR_CloseComm();
		}
		// 关闭线程池
		exec.shutdown();
	}

	/**
	 * 获取身份证相片
	 * 
	 * @return
	 */
	public File getPeoplePhoto() {
		return bmp;
	}

	/**
	 * 获取身份证所有信息
	 * 
	 * @return
	 */
	public String[] getPeopleInfos() {
		return infos;
	}

	/**
	 * 获取姓名信息
	 * 
	 * @return
	 */
	public String getPeopleName() {
		return infos[0];
	}

	/**
	 * 获取性别信息
	 * 
	 * @return
	 */
	public String getPeopleSex() {
		return infos[1];
	}

	/**
	 * 获取民族信息
	 * 
	 * @return
	 */
	public String getPeopleNation() {
		return infos[2];
	}

	/**
	 * 获取生日信息
	 * 
	 * @return
	 */
	public String getPeopleBirthday() {
		return infos[3];
	}

	/**
	 * 获取住址信息
	 * 
	 * @return
	 */
	public String getPeopleAddress() {
		return infos[4];
	}

	/**
	 * 获取身份证号码
	 * 
	 * @return
	 */
	public String getPeopleIDCode() {
		return infos[5];
	}

	/**
	 * 获取发证机关信息
	 * 
	 * @return
	 */
	public String getDepartment() {
		return infos[6];
	}

	/**
	 * 获取有效开始日期
	 * 
	 * @return
	 */
	public String getStartDate() {
		return infos[7].substring(0, infos[7].indexOf("-"));
	}

	/**
	 * 获取有效截止日期
	 * 
	 * @return
	 */
	public String getEndDate() {
		return infos[7].substring(infos[7].indexOf("-"));

	}

	private void setDllPath(String path) {
		try {
			System.load(path.replaceAll("\\\\","/")+"/termb.dll"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dllPath =path;

	}
}
