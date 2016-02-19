package cn.xn.job.service.console.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.TreeMap;

public class SignUtils {

	private static Log logger = LogFactory.getLog(SignUtils.class);
	
	public static String addSign(TreeMap<String, String> treeMap, String charset, String key) {
		logger.info("--------addSign------- treeMap:" + treeMap);
		//遍历签名参数
		StringBuilder sign_sb = new StringBuilder();
		
		Iterator<String> it = treeMap.keySet().iterator();
		while(it.hasNext()){
			String mapKey = it.next();
			if(StringUtils.isEmpty(treeMap.get(mapKey)))
				continue;
			if(StringUtils.isEmpty(sign_sb.toString())) {
				sign_sb.append(mapKey + "=" + treeMap.get(mapKey));
			} else {
				sign_sb.append("&" + mapKey + "=" + treeMap.get(mapKey));
			}
		}
		sign_sb.append("&key=" + key);
		
		logger.info("----addSign---sign_sb:" + sign_sb);
		return MD5.md5(sign_sb.toString(), charset);
	}
	
	public static String addSign(TreeMap<String, String> treeMap, String key) {
		logger.info("--------addSign------- treeMap:" + treeMap);
		//遍历签名参数
		StringBuilder sign_sb = new StringBuilder();
		
		Iterator<String> it = treeMap.keySet().iterator();
		while(it.hasNext()){
			String mapKey = it.next();
			if(StringUtils.isEmpty(treeMap.get(mapKey)))
				continue;
			if(StringUtils.isEmpty(sign_sb.toString())) {
				sign_sb.append(mapKey + "=" + treeMap.get(mapKey));
			} else {
				sign_sb.append("&" + mapKey + "=" + treeMap.get(mapKey));
			}
		}
		sign_sb.append("&key=" + key);
		
		logger.info("----addSign---sign_sb:" + sign_sb);
		return MD5.md5(sign_sb.toString());
	}
	
	
	/**
	 * 签名验证
	 * @param treeMap
	 * @param key
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static boolean isPassSign(TreeMap<String, String> treeMap, String charSet, String key) {
		TreeMap<String, String> tempMap = (TreeMap<String, String>)treeMap.clone();
		String sign = (String)tempMap.remove("sign");
		logger.info("----isPassSign----sign:"+ sign);
		
		String addSign = addSign(tempMap, charSet, key);
		
		logger.info("----isPassSign----addSign:" + addSign);
		if(StringUtils.isNotEmpty(addSign) && addSign.equals(sign)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 签名验证
	 * @param treeMap
	 * @param key
	 * @return
	 */

//	@SuppressWarnings("unchecked")
//	public static boolean isPassSign(TreeMap<String, String> treeMap, String key) {
//		TreeMap<String, String> tempMap = (TreeMap<String, String>)treeMap.clone();
//		String sign = (String)tempMap.remove("sign");
//		logger.info("----isPassSign----sign:"+ sign);
//		
//		String addSign = addSign(tempMap, key);
//		
//		logger.info("----isPassSign----addSign:" + addSign);
//		if(StringUtils.isNotEmpty(addSign) && addSign.equals(sign)) {
//			return true;
//		}
//		return false;
//	}
}
