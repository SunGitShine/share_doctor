package com.phicomm.doctor.util.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http请求处理工具
 * 
 * @author qiang.xie
 * 
 * @date 2017年12月14日 下午2:19:15
 */
public class HttpClientUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String doPost(String url, Map<String, String> map, String authorization) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			if (StringUtils.isNotEmpty(authorization)) {
				httpPost.addHeader("Authorization", authorization);
			}
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (!map.isEmpty()) {
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "utf-8");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String doGet(String url, String params, String authorization) {
		HttpClient httpClient = null;
		String result = null;
		if (StringUtils.isNotEmpty(params)) {
			url = url + "?" + params;
		}
		try {
			httpClient = new SSLClient();
			HttpGet get = new HttpGet(url);
			if (StringUtils.isNotEmpty(authorization)) {
				get.addHeader("Authorization", authorization);
			}
			HttpResponse response = httpClient.execute(get);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "utf-8");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Get the content according to the full url.
	 * 
	 * @param url:
	 *            full url
	 * @return: the content
	 */
	public static String doGet(String url) {
		HttpClient httpClient = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, "utf-8");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}

