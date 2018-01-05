package com.js.support.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.js.support.exception.CommonException;
/**
 * HTTP请求工具
 * @gaining
 *
 */
@SuppressWarnings("deprecation")
public class HttpUtil {

	private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	// HTTP请求和响应的默认编码
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private static RequestConfig getConfig(Integer timeout) {
		RequestConfig config = null;
		if (timeout != null && timeout != 0) {
			config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(10000)
					.setConnectionRequestTimeout(10000).build();
		} else {
			config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
					.setConnectionRequestTimeout(10000).build();
		}
		return config;
	}
	 
	
	
	/**
	 * 
	 * @description 通过HTTP协议向服务器段发送请求,并得到返回结果，根据项目要求所有请求代码为UTF-8，请求地址必须用 URLEncoder.encode进行UTF-8转化。
	 * @param serviceUrl  服务器地址
	 * @param pMap       请求的键值对入参
	 * @return String 返回数据
	 */
	public static String post(String requestUrl, String para, int timeout) {
		String result = "";
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		long start_time = System.currentTimeMillis();
		try {
			HttpPost httpPost = new HttpPost(requestUrl);
			httpPost.setConfig(getConfig(timeout));
			if (para != null && !para.trim().equals("")) {
				StringEntity reqEntity = new StringEntity(para, DEFAULT_CHARSET);
				reqEntity.setContentType("charset=" + DEFAULT_CHARSET);
				reqEntity.setContentEncoding(DEFAULT_CHARSET);
				httpPost.setEntity(reqEntity);
			}
			logger.info("执行http请求参数编码【{}】请求参数【{}】",DEFAULT_CHARSET,para);
			if(requestUrl.indexOf("https://") == 0){
				logger.info("根据请求地址【{}】转换访问方式成https",requestUrl);
				httpclient = createSSLClientDefault();
			}else{
				httpclient = createSSLClientDefaultBycrert(null, null);
			}
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("执行http请求返回状态码【{}】,耗时【{}】", statusCode, (System.currentTimeMillis() - start_time));
			HttpEntity entity = response.getEntity();
			StringBuffer sb = new StringBuffer();
			if (entity != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), DEFAULT_CHARSET));
				String text = null;
				while ((text = bufferedReader.readLine()) != null) {
					sb.append(text);
				}
			}
			result = sb.toString();
			EntityUtils.consume(entity);
			
			if(statusCode != 200){
				logger.info("Http请求异常，返回码【{}】,返回内容【{}】", statusCode, result);
				return null;
			}
			return result;
		} catch (ConnectTimeoutException ex) {
			logger.error("执行http请求，发生异常。异常信息如下：", ex);
			throw new CommonException("连接微信平台超时");
		}catch(SocketTimeoutException ex){
			logger.error("执行http请求，发生异常。异常信息如下：", ex);
			throw new CommonException("读取微信平台信息超时");
		}catch(Exception ex){
			logger.error("执行http请求，发生异常。异常信息如下：", ex);
			throw new CommonException("系统正在维护中，请稍后再试");
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("根据请求地址【{}】请求参数【{}】返回信息【{}】,请求耗时【{}】", requestUrl, para, result, (System.currentTimeMillis() - start_time));
		}
	}
	
	
	
	
	public static String get(String requestUrl) throws Exception {
		/* 1 生成 HttpClinet 对象并设置参数 */
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		long start_time = System.currentTimeMillis();

		if (requestUrl.indexOf("https://") == 0) {
			logger.info("根据请求地址【{}】转换访问方式成https", requestUrl);
			httpClient = createSSLClientDefault();
		} else {
			httpClient = createSSLClientDefaultBycrert(null, null);
		}
		HttpGet httpRequst = new HttpGet(requestUrl);
		String result = "";
		try {
			// 使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
			httpResponse = httpClient.execute(httpRequst);// 其中HttpGet是HttpUriRequst的子类
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				// 一般来说都要删除多余的字符
				result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			} else {
				httpRequst.abort();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("读取http请求返回信息，请求地址【{}】,出参【{}】,请求耗时【{}】", requestUrl, result, (System.currentTimeMillis() - start_time));
		}
		return result;
	}
	
	/**
	 * 创建CloseableHttpClient
	 * @param keyID 证书密钥
	 * @param keyPath 证书绝对路径
	 * @return CloseableHttpClient
	 */
	private static CloseableHttpClient createSSLClientDefaultBycrert(String certKey, String certPath) {
		// 判断是否传入证书和密钥
		if (certPath != null && certKey != null) {
			logger.info("创建CloseableHttpClient使用证书，证书路径【{}】，证书密码【{}】", certPath, certKey);
			try {
				FileInputStream instream = new FileInputStream(new File(certPath));
				KeyStore keyStore = KeyStore.getInstance("PKCS12");
				try {
					keyStore.load(instream, certKey.toCharArray());
				} finally {
					if (instream != null) {
						instream.close();
					}
				}

				SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, certKey.toCharArray()).loadTrustMaterial(null,
						new TrustSelfSignedStrategy()).build();

				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
				// 设置httpclient的SSLSocketFactory 
				return HttpClients.custom().setDefaultRequestConfig(getConfig(null)).setSSLSocketFactory(sslsf).build();
			} catch (Exception e) {
				logger.info("创建CloseableHttpClient使用证书，证书路径【{}】，证书密码【{}】，发生异常【{}】", certPath, certKey, e.getMessage());
				e.printStackTrace();
				return HttpClients.custom().setDefaultRequestConfig(getConfig(null)).build();
			}
		} else {
			logger.info("创建CloseableHttpClient未使用证书");
			return createSSLClientDefault();
		}
	}
	
	/**
	 * 信任所有https证书请求
	 * @return
	 */
	private static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(getConfig(null)).build();
		} catch (KeyManagementException e) {
			logger.error("创建https访问异常【{}】",e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("创建https访问异常【{}】",e);
		} catch (KeyStoreException e) {
			logger.error("创建https访问异常【{}】",e);
		}
		return HttpClients.custom().setDefaultRequestConfig(getConfig(null)).build();
	}
}
