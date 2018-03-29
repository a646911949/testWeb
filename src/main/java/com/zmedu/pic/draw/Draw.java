package com.zmedu.pic.draw;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONObject;

public class Draw  extends JFrame {

		private Graphics jg;

		private Color rectColor = new Color(0xf5f5f5);
		private JSONObject json = null;

		/**
		 * DrawSee构造方法
		 */
		public Draw(JSONObject json) {
			Container p = getContentPane();
			setBounds(100, 100, 500, 500);
			setVisible(true);
			p.setBackground(rectColor);
			setLayout(null);
			setResizable(false);
			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.json = json;
			try {

				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 获取专门用于在窗口界面上绘图的对象
			jg = this.getGraphics();

			// 绘制游戏区域
			paintComponents(jg);

		}

		public void paintComponents(Graphics g) {
			try {

				g.setColor(Color.black);

				// json = (JSONObject) json.get("face");
				// JSONArray a=(JSONArray) json.get("face");
				// json=a.getJSONObject(0);
				// System.out.println(json);
				// json=(JSONObject) json.get("face_shape");

				json = (JSONObject) ((JSONArray) json.get("face"))
						.getJSONObject(0).get("face_shape");
//				System.out.println(json);

				JSONArray arr = json.getJSONArray("right_eye");
//				System.out.println(arr.get(0));
				JSONObject temp = null;

				int num = 0;
				String key = null;
				boolean flag = true;
				while (flag) {
					switch (num) {
					case 0:
						key = "face_profile";
						break;
					case 1:
						key = "left_eye";
						break;
					case 2:
						key="right_eye";
						break;
					case 3:
						key = "left_eyebrow";
						break;
					case 4:
						key = "right_eyebrow";
						break;
					case 5:
						key = "mouth";
						break;
					case 6:
						key = "nose";
						break;
					default:
						flag = false;
						break;
					}
					arr = json.getJSONArray(key);
					for (int i = 0; i < arr.length(); i++) {
						temp = (JSONObject) arr.get(i);
						g.drawLine(temp.getInt("x"), temp.getInt("y"),
								temp.getInt("x"), temp.getInt("y"));
					}
					num++;
				}

				System.out.println(arr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
