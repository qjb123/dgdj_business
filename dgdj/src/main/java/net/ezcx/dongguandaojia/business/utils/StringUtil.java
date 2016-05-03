package net.ezcx.dongguandaojia.business.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串相关操作类
 * 
 * @author Administrator
 * 
 */
public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * 将价格中间添加逗号
	 * 
	 * @param number
	 * @return
	 */
	public static String saveComma(Float number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###"); 
		return decimalFormat.format(number);
	}
	/**
	 * 四舍五入
	 * 
	 * @return
	 */
	public static String saveFour(String number) {
		BigDecimal decimalFormat = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
		return decimalFormat.toString();
	}
	/**
	 * 保留0位小数
	 * 
	 * @param number
	 * @return
	 */
	public static String savePointNumber(Float number) {
		DecimalFormat decimalFormat = new DecimalFormat("0");
		return decimalFormat.format(number);
	}
	/**
	 * 保留2位小数
	 * 
	 * @param number
	 * @return
	 */
	public static String saveTwoPointNumber(Float number) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		return decimalFormat.format(number);
	}
	/**
	 * 将list<String> 集合转化为String
	 * 
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	/**
	 * 当获取的字符为null是转化为""
	 * 
	 * @param string
	 * @return
	 */
	public static String ChangeText(String string) {
		if (isEmpty(string) || string.equals("null")) {
			return "";
		} else {
			return string;
		}
	}
	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		Pattern emailer = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
		if(email == null || email.trim().length()==0) 
			return false;
	    return emailer.matcher(email).matches();
	}
	/**
	 * 判断是不是一个合法的电话号码
	 * @param
	 * @return
	 */
	public static boolean isPhone(String phone){
		Pattern emailer = Pattern.compile("[1][3578]\\d{9}");
		if(phone == null || phone.trim().length()==0) 
			return false;
	    return emailer.matcher(phone).matches();
	}
	/**
	 * 处理url 如果不是以http://或者https://开头，就添加http://
	 * 
	 * @param url
	 *            被处理的url
	 * @return
	 */
	public static String preUrl(String url) {
		if (url == null) {
			return null;
		}
		if (url.startsWith("http://") || url.startsWith("https://")) {
			return url;
		} else {
			return "http://" + url;
		}
	}
	
	 /*public static void showToast(final String toast, final Context context)
	    {
	    	new Thread(new Runnable() {
				
				@Override
				public void run() {
					Looper.prepare();
					Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
					Looper.loop();
				}
			}).start();
	    }*/
	
	 public static boolean isConnected(Context context) {
	        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo info = conn.getActiveNetworkInfo();
	        return (info != null && info.isConnected());
	    }
	    
		public static String getImei(Context context, String imei) {
			try {
				TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				imei = telephonyManager.getDeviceId();
			} catch (Exception e) {
				Log.e(StringUtil.class.getSimpleName(), e.getMessage());
			}
			return imei;
		}
}
