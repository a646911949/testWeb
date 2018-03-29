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
	 * �򿪶˿�
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
			n.setRetVal(Type.INT); // ָ�����ز���������
			n.setParameter(0, port);
			n.invoke(); // ���÷���
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * ��֤���֤�Ƿ���ȷ����
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
			n.setRetVal(Type.INT); // ָ�����ز���������
			n.invoke(); // ���÷���
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * �������֤��Ϣ�ļ�
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
			n.setRetVal(Type.INT); // ָ�����ز���������
			n.invoke(); // ���÷���
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * �رն˿�
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	private int CVR_CloseComm() throws NativeException, IllegalAccessException {
		JNative n = null;
		try {
			n = new JNative("Termb.dll", "CVR_CloseComm");
			n.setRetVal(Type.INT); // ָ�����ز���������
			n.invoke(); // ���÷���
			return Integer.parseInt(n.getRetVal());
		} finally {

		}
	}

	/**
	 * ��ȡ��ȡ���֤ʵ��
	 * 
	 * @param port
	 *            �˿�
	 * @param dllPath
	 *            dll��λ��
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
	 * ��ȡ���֤��Ϣ
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
					throw new Exception("���ڴ�ʧ��");
				}
				return "���֤��ȡ���";
			}
		};
		try {
			Future<String> future = exec.submit(call);
			String obj = future.get(1000 * 10, TimeUnit.MILLISECONDS);
			System.out.println("����ɹ�����:" + obj);
		} catch (TimeoutException ex) {
			System.out.println("����ʱ");
		} catch (Exception e) {
			System.out.println("����ʧ��");
			throw new Exception(e.getMessage());
		} finally {
			CVR_CloseComm();
		}
		// �ر��̳߳�
		exec.shutdown();
	}

	/**
	 * ��ȡ���֤��Ƭ
	 * 
	 * @return
	 */
	public File getPeoplePhoto() {
		return bmp;
	}

	/**
	 * ��ȡ���֤������Ϣ
	 * 
	 * @return
	 */
	public String[] getPeopleInfos() {
		return infos;
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public String getPeopleName() {
		return infos[0];
	}

	/**
	 * ��ȡ�Ա���Ϣ
	 * 
	 * @return
	 */
	public String getPeopleSex() {
		return infos[1];
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public String getPeopleNation() {
		return infos[2];
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public String getPeopleBirthday() {
		return infos[3];
	}

	/**
	 * ��ȡסַ��Ϣ
	 * 
	 * @return
	 */
	public String getPeopleAddress() {
		return infos[4];
	}

	/**
	 * ��ȡ���֤����
	 * 
	 * @return
	 */
	public String getPeopleIDCode() {
		return infos[5];
	}

	/**
	 * ��ȡ��֤������Ϣ
	 * 
	 * @return
	 */
	public String getDepartment() {
		return infos[6];
	}

	/**
	 * ��ȡ��Ч��ʼ����
	 * 
	 * @return
	 */
	public String getStartDate() {
		return infos[7].substring(0, infos[7].indexOf("-"));
	}

	/**
	 * ��ȡ��Ч��ֹ����
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
