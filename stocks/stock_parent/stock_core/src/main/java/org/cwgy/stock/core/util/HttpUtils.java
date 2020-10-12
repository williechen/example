package org.cwgy.stock.core.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.config.RequestConfig.Builder;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 網際網路存取
 * 
 * @author willie chen
 *
 */

public class HttpUtils {
	private static Logger log = LogManager.getLogger(HttpUtils.class);

	/**
	 * 
	 * @param url     http url
	 * @param headers http header
	 * @param parames http param
	 * @return ClassicHttpRequest http request
	 * @throws Exception error
	 */
	public static ClassicHttpRequest methodGet(String url, Map<String, String> headers, Map<String, Object> parames)
			throws Exception {
		HttpGet httpGet = null;

		if (StringUtils.isNotBlank(url)) {

			URIBuilder uriBuilder = new URIBuilder(url);
			// 設定引數
			if (parames != null && !parames.isEmpty()) {
				List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
				for (Iterator<String> iter = parames.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String value = String.valueOf(parames.get(name));
					valuePairs.add(new BasicNameValuePair(name, value));
				}
				uriBuilder.addParameters(valuePairs);
			}

			httpGet = new HttpGet(uriBuilder.build());

			Builder config = RequestConfig.custom();
			config.setConnectTimeout(Timeout.ofMinutes(5L)); // 總連線時間
			config.setConnectionRequestTimeout(Timeout.ofSeconds(150L)); // 傳送時間
			config.setResponseTimeout(Timeout.ofSeconds(150L)); // 回傳時間

			httpGet.setConfig(config.build());

			// 設定 header
			if (headers != null && !headers.isEmpty()) {
				for (Iterator<String> iter = headers.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String value = String.valueOf(headers.get(name));
					httpGet.setHeader(name, value);
				}
			}

		}

		return httpGet;

	}

	/**
	 * 
	 * @param url     http url
	 * @param headers http header
	 * @param parames
	 * @return ClassicHttpRequest
	 * @throws Exception
	 */
	public static ClassicHttpRequest methodPost(String url, Map<String, String> headers, Map<String, Object> parames)
			throws Exception {
		HttpPost httpPost = null;

		if (StringUtils.isNotBlank(url)) {
			httpPost = new HttpPost(url);

			Builder config = RequestConfig.custom();
			config.setConnectTimeout(Timeout.ofMinutes(1L));
			config.setConnectionRequestTimeout(Timeout.ofMinutes(1L));
			config.setResponseTimeout(Timeout.ofMinutes(1L));

			httpPost.setConfig(config.build());

			// 設定 header
			if (headers != null && !headers.isEmpty()) {
				for (Iterator<String> iter = headers.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String value = String.valueOf(headers.get(name));
					httpPost.setHeader(name, value);
				}
			}

			// 設定引數
			if (parames != null && !parames.isEmpty()) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Iterator<String> iter = parames.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String value = String.valueOf(parames.get(name));
					nvps.add(new BasicNameValuePair(name, value));
				}
				httpPost.setEntity(
						new UrlEncodedFormEntity(nvps, ContentType.APPLICATION_FORM_URLENCODED.getCharset()));
			}
		}

		return httpPost;
	}

	/**
	 * 
	 * @param url     http url
	 * @param headers http header
	 * @param parames
	 * @return ClassicHttpRequest
	 * @throws Exception
	 */
	public static ClassicHttpRequest methodGetByJson(String url, Map<String, String> headers,
			Map<String, Object> parames) throws Exception {
		HttpGet httpGet = null;

		if (StringUtils.isNotBlank(url)) {
			URIBuilder uriBuilder = new URIBuilder(url);
			// 設定引數
			if (parames != null && !parames.isEmpty()) {
				ObjectMapper mapper = new ObjectMapper();
				String jsonString = mapper.writeValueAsString(parames);

				Encoder encoder = Base64.getEncoder();
				String base64fileString = new String(encoder.encode(jsonString.getBytes()));

				uriBuilder.addParameter("request_data", base64fileString);
			}

			httpGet = new HttpGet(uriBuilder.build());

			Builder config = RequestConfig.custom();
			config.setConnectTimeout(Timeout.ofMinutes(5L)); // 總連線時間
			config.setConnectionRequestTimeout(Timeout.ofSeconds(150L)); // 傳送時間
			config.setResponseTimeout(Timeout.ofSeconds(150L)); // 回傳時間

			httpGet.setConfig(config.build());

			// 設定 header
			if (headers != null && !headers.isEmpty()) {
				for (Iterator<String> iter = headers.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String value = String.valueOf(headers.get(name));
					httpGet.setHeader(name, value);
				}
			}
		}

		return httpGet;

	}

	/**
	 * 
	 * @param url     http url
	 * @param headers http header
	 * @param parame
	 * @return ClassicHttpRequest
	 * @throws Exception
	 */
	public static ClassicHttpRequest methodPostByJson(String url, Map<String, String> headers,
			Map<String, Object> parame) throws Exception {
		HttpPost httpPost = null;

		if (StringUtils.isNotBlank(url)) {

			httpPost = new HttpPost(url);

			Builder config = RequestConfig.custom();
			config.setConnectTimeout(Timeout.ofMinutes(1L));
			config.setConnectionRequestTimeout(Timeout.ofMinutes(1L));
			config.setResponseTimeout(Timeout.ofMinutes(1L));

			httpPost.setConfig(config.build());

			// 設定 header
			if (headers != null && !headers.isEmpty()) {
				for (Iterator<String> iter = headers.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String value = String.valueOf(headers.get(name));
					httpPost.setHeader(name, value);
				}
			}

			// 設定引數
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(parame);
			StringEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);

			httpPost.setEntity(entity);
		}

		return httpPost;
	}

	/**
	 * 
	 * @param request http request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public static Map<String, Object> getHttpResponseData(ClassicHttpRequest request) throws Exception {
		Map<String, Object> resultMap = null;

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		if (request != null) {
			try {

				client = HttpClients.createDefault();
				response = client.execute(request);

				int status = response.getCode();
				if (status < 400) {
					resultMap = new HashMap<String, Object>();
					resultMap.put("STATUS", status);

					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String entityStr = EntityUtils.toString(entity, "utf-8");
						resultMap.put("CONTENT", entityStr);
					}
				} else {
					throw new Exception(response.getEntity().getContent().toString());
				}

			} finally {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}

			}
		}
		return resultMap;
	}

	/**
	 * 
	 * @param request http request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public static Map<String, Object> getFileToBase64ByHttpResponseData(ClassicHttpRequest request) throws Exception {
		Map<String, Object> resultMap = null;

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		if (request != null) {
			try {

				client = HttpClients.createDefault();
				response = client.execute(request);

				int status = response.getCode();
				if (status < 400) {
					resultMap = new HashMap<String, Object>();
					resultMap.put("STATUS", status);

					HttpEntity entity = response.getEntity();
					if (entity != null) {
						BufferedInputStream bis = new BufferedInputStream(entity.getContent());
						ByteArrayOutputStream baos = new ByteArrayOutputStream();

						byte[] buffer = new byte[4096];
						int readLength = 0;
						while ((readLength = bis.read(buffer, 0, buffer.length)) != -1) {
							baos.write(buffer, 0, readLength);
						}

						Encoder encoder = Base64.getEncoder();
						String base64fileString = new String(encoder.encode(baos.toByteArray()));

						resultMap.put("CONTENT", base64fileString);
					}
				} else {
					throw new Exception(response.getEntity().getContent().toString());
				}
			} finally {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}

			}

		}
		return resultMap;
	}

	/**
	 * 
	 * @param request http request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public static Map<String, Object> getHttpsResponseData(ClassicHttpRequest request) throws Exception {
		Map<String, Object> resultMap = null;

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		if (request != null) {
			try {
				// 信任所有
				final SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
					Logger logSub = LogManager.getLogger(getClass());

					@Override
					public boolean isTrusted(final X509Certificate[] chain, final String authType)
							throws CertificateException {
						final X509Certificate cert = chain[0];
						logSub.info("SubjectDN=" + cert.getSubjectDN().getName());
						return true;
					}

				}).build();
				// Allow TLSv1.2 protocol only
				final SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
						.setSslContext(sslcontext).setTlsVersions(TLS.V_1_2).build();
				final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
						.setSSLSocketFactory(sslSocketFactory).build();

				client = HttpClients.custom().setConnectionManager(cm).build();
				response = client.execute(request);

				int status = response.getCode();
				if (status < 400) {
					resultMap = new HashMap<String, Object>();
					resultMap.put("STATUS", status);

					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String entityStr = EntityUtils.toString(entity, "utf-8");
						resultMap.put("CONTENT", entityStr);
					}
				} else {
					throw new Exception(response.getEntity().getContent().toString());
				}

			} finally {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}

			}

		}
		return resultMap;
	}

	/**
	 * 
	 * @param request http request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public static Map<String, Object> getFileToBase64ByHttpsResponseData(ClassicHttpRequest request) throws Exception {
		Map<String, Object> resultMap = null;

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		if (request != null) {
			try {
				// 信任所有
				final SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
					Logger logSub = LogManager.getLogger(getClass());

					@Override
					public boolean isTrusted(final X509Certificate[] chain, final String authType)
							throws CertificateException {
						final X509Certificate cert = chain[0];
						logSub.info("SubjectDN=" + cert.getSubjectDN().getName());
						return true;
					}

				}).build();
				// Allow TLSv1.2 protocol only
				final SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
						.setSslContext(sslcontext).setTlsVersions(TLS.V_1_2).build();
				final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
						.setSSLSocketFactory(sslSocketFactory).build();

				client = HttpClients.custom().setConnectionManager(cm).build();
				response = client.execute(request);

				int status = response.getCode();
				if (status < 400) {
					resultMap = new HashMap<String, Object>();
					resultMap.put("STATUS", status);

					HttpEntity entity = response.getEntity();
					if (entity != null) {
						BufferedInputStream bis = new BufferedInputStream(entity.getContent());
						ByteArrayOutputStream baos = new ByteArrayOutputStream();

						byte[] buffer = new byte[4096];
						int readLength = 0;
						while ((readLength = bis.read(buffer, 0, buffer.length)) != -1) {
							baos.write(buffer, 0, readLength);
						}

						Encoder encoder = Base64.getEncoder();
						String base64fileString = new String(encoder.encode(baos.toByteArray()));

						resultMap.put("CONTENT", base64fileString);
					}
				} else {
					throw new Exception(response.getEntity().getContent().toString());
				}
			} finally {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}

			}
		}
		return resultMap;
	}
}
