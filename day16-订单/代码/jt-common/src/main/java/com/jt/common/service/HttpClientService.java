package com.jt.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
	//因为common是工具API,有的需要调用,
	//有的不需要配置.
	//如果不加该属性,
	//则jt-manage程序不需要httpClient时
	//该后台启动时必然报错.
    @Autowired(required=false)
    private CloseableHttpClient httpClient;  //操作http请求API

    @Autowired(required=false)			     //定义请求超时间
    private RequestConfig requestConfig;
    
    //编辑请求发送的方式,获取返回值结果 给调用者.
    /**
     * //拼接url
    		url = url + "?";
    		//遍历map集合
    		for (Map.Entry<String,String> entry : params.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				url = url+ key + "=" + value + "&";
			}
    		
    		url = url.substring(0,url.length()-1);
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public String doGet(String url,Map<String,String> params,String charset){
    	
    	String result = null;
    	
    	//1.判断字符集编码是否为null
    	if(StringUtils.isEmpty(charset)){
    		charset = "UTF-8";
    	}
    	
    	//2.封装用户提交参数  
    	//http://manage.jt.com?id=1&name=tom
    	if(params != null){
    		try {
    			URIBuilder builder = new URIBuilder(url);//注意参数
    			
    			for (Map.Entry<String,String> entry : params.entrySet()) {
					
    				builder.addParameter(entry.getKey(), entry.getValue());
				}
    			
    			//拼接成的url?key=value&key2=value2
    			url = builder.build().toString();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	//3.封装请求参数类型
    	HttpGet httpGet = new HttpGet(url);
    	
    	try {
    		//4.发起请求.获取响应结果
    		CloseableHttpResponse response = 
        	httpClient.execute(httpGet);
        	
        	//5.判断返回值是否正确
    		if(response.getStatusLine()
    				.getStatusCode() == 200){
    			
    			//6.解析返回值数据
    			result = EntityUtils
    					.toString(response.getEntity(),charset);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    public String doGet(String url){
    	
    	return doGet(url, null, null);
    }
    
    public String doGet(String url,Map<String,String> params){
    	
    	return doGet(url, params, null);
    }
    
    /**
     * 1.定义请求方式httpPost
     * 2.将参数进行表单实体封装.
     * 3.发起url请求,获取返回值
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public String doPost(String url,Map<String,String> params,String charset){
    	String result = null;
    	if(StringUtils.isEmpty(charset)){
    		
    		charset = "UTF-8";
    	}
    	
    	//1.定义请求类型
    	HttpPost post = new HttpPost(url);
    	post.setConfig(requestConfig); //定义链接时长
  
    	try {	
	    	//2.参数封装
	    	if(params != null){
	    		List<BasicNameValuePair> parameters = new ArrayList<>();
	    		//动态获取用户数据
	    		for (Map.Entry<String,String> entry : params.entrySet()) {
	    			parameters.add(
	    			new BasicNameValuePair(entry.getKey(),entry.getValue()));
				}
	    		//封装FORM表单实体对象,作用传递参数
	    		UrlEncodedFormEntity entity = 
	    				new UrlEncodedFormEntity(parameters,charset);
	    		post.setEntity(entity);
	    	}
	    	
	    	//3.发起url请求.
	    	CloseableHttpResponse httpResponse = 
	    			httpClient.execute(post);
	    	//504 访问超时 500 服务器异常 406 浏览器解析参数异常  404 请求没有对应的处理方式
	    	//400 参数提交到后台参数类型错误. 200 请求正常  304 浏览器已缓存
	    	if(httpResponse.getStatusLine().getStatusCode() == 200){
	    		result  = 
	    	EntityUtils.toString(httpResponse.getEntity(),charset);
	    	
	    	}
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
   
    	return result;
    }
    
    public String doPost(String url,Map<String,String> params){
    	
    	return doPost(url, params, null);
    }
    
    public String doPost(String url){
    	
    	return doPost(url, null, null);
    }
}
