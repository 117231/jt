package com.jt.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.jt.common.service.HttpClientService;

public class TestHttpClient {
	/**
	 * 1.导入jar包
	 * 2.定义请求链接url
	 * 3.获取工具API对象
	 * 4.定义请求方式 get/post
	 * 5.利用客户端程序,发起请求,获取响应结果
	 * 6.判断请求状态码如果为200则表示请求成功!!
	 * 7.获取最终返回数据,进行业务处理.
	 * String 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * **/
	@Test
	public void testGet() throws ClientProtocolException, IOException {
		String url = "https://www.jd.com/";
		CloseableHttpClient client = 
				HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = 
				client.execute(get);
		if(response.getStatusLine()
				   .getStatusCode()==200) {
			//表示请求成功!!!
			String result = 
			EntityUtils.toString(response.getEntity());
			System.out.println(result);
		}
	}
	
	//通过工具API可以直接获取远程返回值
	public void testHttpClient() {
		String url = 
		"http://manage.jt.com/web/findItem";
		HttpClientService httpClient = new HttpClientService();
		Map<String,String> params = new HashMap<>();
		params.put("itemId", "562379");
		params.put("name","tomcat");
		//http://manage.jt.com/.../findItem?itemId=562379
		String result = httpClient.doGet(url, params);
		for (Map.Entry<String,String> entry 
				: params.entrySet()) {
			String key = entry.getKey();
			String name = entry.getValue();
		}
	}	
}
