package com.tiger.utilities;

import com.tiger.utilities.StringUtil;

/**
 * 格式展现类
 * @author superyu
 *
 */
public class DisplayUtil {
	
	/**
	 * 获取带有掩饰的邮箱
	 * @param email 如：superyu@126.com
	 * @return su***@126.com
	 */
	public static String showMail(String email){
		if(StringUtil.isEmpty(email)){
			return "";
		}
		String [] arr=email.split("@");
		
		if(arr.length!=2){
			return "";
		}
		
		if(StringUtil.isEmpty(arr[0])){
			return "";
		}
		
		if(StringUtil.isEmpty(arr[1])){
			return "";
		}
		
		if(arr[0].length()==1){
			return "***@"+arr[1];
		}
		
		if(arr[0].length()==2){
			return arr[0]+"***@"+arr[1];
		}
		
		return arr[0].substring(0,2)+"***@"+arr[1];
	}
	
	
	
	/**
	 * 获取遮盖手机号
	 * @param phoneNo 13800138000
	 * @return 138***8000
	 */
	public static String showPhoneNo(String phoneNo){
		if(StringUtil.isEmpty(phoneNo)){
			return "";
		}
		
		if(phoneNo.length()!=11){
			return "";
		}
		String top=phoneNo.substring(0,3);
		
		String bottom=phoneNo.substring(7,11);
		
		return top+"****"+bottom;
	}

}
