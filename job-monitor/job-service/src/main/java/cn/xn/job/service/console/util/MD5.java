package cn.xn.job.service.console.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5 {
	
	/**
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String md5(String str, String charset) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes(charset));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
	

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}

	public static String md5Sign(String content, String signkey) {
		String content4Sign = content + signkey;
		return md5(content4Sign);
	}

	public static void main(String[] args) {
		//System.out.println(md5("31119@qq.com" + "123456"));
		//System.out.println(md5("mj1"));
		
		System.out.println(Charset.defaultCharset().name());
	}
}
