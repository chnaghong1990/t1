package com.zxjk.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	static String prefix = "http://127.0.0.1:8080/wireless";

	/**
	 * 业务经理详细 /app/business/detail
	 */
	public void business_detail() {
		String url = prefix + "/app/business/detail";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("token", "Y"));
		nvps.add(new BasicNameValuePair("cmId", "38"));
		
		String result = post(url, nvps);
		format(result);
	}
	/**
	 * 业务经理列表 /app/list/business
	 */
	public void business_list() {
		String url = prefix + "/app/list/business";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("token", "Y"));
		
		String result = post(url, nvps);
		format(result);
	}
	/**
	 * 门店详细 /app/store/detail
	 */
	public void store_detail() {
		String url = prefix + "/app/store/detail";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("token", "Y"));
		nvps.add(new BasicNameValuePair("storeId", "1"));
		
		String result = post(url, nvps);
		format(result);
	}
	/**
	 * 门店列表 /app/list/store
	 */
	public void store_list() {
		String url = prefix + "/app/list/store";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("token", "Y"));
		
		String result = post(url, nvps);
		format(result);
	}
	/**
	 * 审核 /app/account/audit
	 */
	public void audit() {
		String url = prefix + "/app/account/audit";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		nvps.add(new BasicNameValuePair("token", "38"));
		nvps.add(new BasicNameValuePair("id", "1"));
		nvps.add(new BasicNameValuePair("act", "Y"));
		
		String result = post(url, nvps);
		format(result);
	}
	
	
	/**
	 * 我的信息 /app/account/detail
	 */
	public void accountDetail() {
		String url = prefix + "/app/account/detail";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
//		nvps.add(new BasicNameValuePair("token", "3"));
//		nvps.add(new BasicNameValuePair("role", "1"));
		
//		nvps.add(new BasicNameValuePair("token", "3"));
//		nvps.add(new BasicNameValuePair("role", "2"));
//		
		nvps.add(new BasicNameValuePair("token", "38"));
		nvps.add(new BasicNameValuePair("role", "3"));
		
		String result = post(url, nvps);
		format(result);
	}

	/**
	 * 消息阅读
	 * /app/msg/read
	 */
	public void msgRead() {
		String url = prefix + "/app/msg/read";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userId", "1"));
		nvps.add(new BasicNameValuePair("messageId", "3"));
		String result = post(url, nvps);
		format(result);
	}

	private String post(String url, List<NameValuePair> nvps) {
		try {

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpClient.execute(httpPost);
			// 检验返回码
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {

				HttpEntity httpEntity = response.getEntity();
				String result = EntityUtils.toString(httpEntity);// 取出应答字符串
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Exception";
	}

	/**
	 * 得到格式化json数据 退格用\t 换行用\r
	 */
	public static String format(String jsonStr) {
		System.err.println(jsonStr);
		int level = 0;
		StringBuffer jsonForMatStr = new StringBuffer();
		for (int i = 0; i < jsonStr.length(); i++) {
			char c = jsonStr.charAt(i);
			if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
				jsonForMatStr.append(getLevelStr(level));
			}
			switch (c) {
			case '{':
			case '[':
				jsonForMatStr.append(c + "\n");
				level++;
				break;
			case ',':
				jsonForMatStr.append(c + "\n");
				break;
			case '}':
			case ']':
				jsonForMatStr.append("\n");
				level--;
				jsonForMatStr.append(getLevelStr(level));
				jsonForMatStr.append(c);
				break;
			default:
				jsonForMatStr.append(c);
				break;
			}
		}
		System.out.println(jsonForMatStr.toString());
		return jsonForMatStr.toString();

	}

	private static String getLevelStr(int level) {
		StringBuffer levelStr = new StringBuffer();
		for (int levelI = 0; levelI < level; levelI++) {
			levelStr.append("\t");
		}
		return levelStr.toString();
	}
}
