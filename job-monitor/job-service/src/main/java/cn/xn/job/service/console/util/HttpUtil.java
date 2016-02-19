package cn.xn.job.service.console.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 项目名称：TravelingWeb
 * 
 * 类名称：HttpUtil
 * 
 * 类描述： 接口请求
 * 
 * 创建人： zhou'sheng
 * 
 * 创建时间：2014年10月17日 下午3:03:25
 * 
 * Copyright (c) 深圳市XXX有限公司-版权所有
 */
public class HttpUtil {

	private final Logger log = Logger.getLogger(HttpUtil.class);

	private static HttpUtil httpUtil;
	private int connectionTimeout = new Integer(30); // ConnectionTimeout
	private int timeout = new Integer(20);// Timeout
	private int size = new Integer(819200);// 8192

	private HttpUtil() {

	}

	public static HttpUtil getInstance() {
		
		if (null == httpUtil) {
			synchronized (HttpUtil.class) {
				if (null == httpUtil) {
					httpUtil = new HttpUtil();
				}
			}
		}
		return httpUtil;
	}

	public String phpPost(String url, Object request) throws ClientProtocolException, ConnectTimeoutException, SocketTimeoutException, HttpHostConnectException, Exception {
		if (null == request) {
			return null;
		}
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, size);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost httpRequest = new HttpPost(url + "?");
		String paramJson = JSON.toJSONString(request);
		log.info("请求:" + url + "?param=" + paramJson);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("param", paramJson));

		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		String strResult = null;
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				InputStream instreams = entity.getContent();
				strResult = convertStreamToString(instreams);
			}
			log.info("返回:" + strResult);
			if (null != strResult && !"".equals(strResult)) {
				return strResult;
			} else {
				return null;
			}
		} else if (httpResponse.getStatusLine().getStatusCode() == 404 || httpResponse.getStatusLine().getStatusCode() == 500) {
			log.info(httpResponse.getStatusLine().getStatusCode());
			throw new Exception(""+httpResponse.getStatusLine().getStatusCode());
		} else {
			log.info(httpResponse.getStatusLine().getStatusCode());
			return null;
		}
	}

	public String smsPost(String url, String mobile, String content) throws Exception {
		HttpPost httpRequest = new HttpPost(url);
		String strResult = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("content", content));
		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				InputStream instreams = entity.getContent();
				strResult = convertStreamToString(instreams);
			}
			log.info("返回:" + strResult);
			if (null != strResult && !"".equals(strResult)) {
				return strResult;
			} else {
				return null;
			}
		} else if (httpResponse.getStatusLine().getStatusCode() == 404 || httpResponse.getStatusLine().getStatusCode() == 500) {
			log.info(httpResponse.getStatusLine().getStatusCode());
			throw new Exception(""+httpResponse.getStatusLine().getStatusCode());
		} else {
			log.info(httpResponse.getStatusLine().getStatusCode());
			return null;
		}
		
	}

	public static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}

	public static void main(String[] args) throws HttpHostConnectException, ConnectTimeoutException, SocketTimeoutException, ClientProtocolException, Exception {
		System.out.println(HttpUtil.getInstance().smsPost("http://message.xiaoniuapp.com/xn-sms-service/sms/send", "13686441896","你购买的天天牛180产品"));
	}

}
