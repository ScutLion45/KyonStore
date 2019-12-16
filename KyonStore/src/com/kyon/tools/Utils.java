package com.kyon.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Utils {
	/* 获取yyyy-MM-dd HH:mm:ss格式时间 
	 * HH: 24小时制，与MySQL的datetime格式一致
	 */
	public static String localeDateTime() {
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDT = f.format(now);
		return formatDT;
	}
	
	/* 生成随机ID */
	public static String genRandID() {
		UUID uuid=UUID.randomUUID();
		
		/* 形成MMddHHmm前缀 */
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("MMdd-HHmm");
		String ldt = f.format(now);
		
        String randID = ldt + "-" + uuid.toString();
        return randID;
	}
	
	/* 获取某月最大的日期数 
	 * 输入：yyyy-MM字符串
	 */
	public static int getMaxDateOf(String yM) {
		int dates = 0;
		int year = 0;
		int month = 0;
		int flag = 0;
		try {
			year = Integer.parseInt(yM.split("-")[0]);
			month = Integer.parseInt(yM.split("-")[1]);
			
			if(year%4==0 && year%100!=0)
				flag = 1;  // 判断是否为闰年
			
			switch(month) {
				case 1: case 3: case 5: case 7: case 8: case 10: case 12:
					dates = 31; break;
				case 4: case 6: case 9: case 11:
					dates = 30; break;
				default:
					dates = 0;
			}
			if(month==2) {
				if(flag==1) dates = 29;
				else dates = 28;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return dates;
	}
}
