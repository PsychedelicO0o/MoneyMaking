package org.bit.big.brother.m.k.c.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private PoolingHttpClientConnectionManager cm;

    private CloseableHttpClient httpClient;

    public void destroy() {
        cm.shutdown();
    }

    public HttpClientUtil() {

    }

    public HttpClientUtil(int maxTotal, int defaultMaxPerRoute, int connection_timeout, int so_timeout, int connection__request_timeout) {
        // 创建Http连接池
        logger.info("初始化 new httpclient 连接池");

        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(connection_timeout)
                .setConnectionRequestTimeout(connection__request_timeout).setSocketTimeout(so_timeout).build();

        HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(0, false);
        httpClient = HttpClients.custom().setRetryHandler(retryHandler).setConnectionManager(cm).setDefaultRequestConfig(defaultRequestConfig).build();
    }

    public String get(String url) throws Exception {
        logger.debug("use new HttpClient");
        HttpGet httpGet = new HttpGet(url);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        String responseBody = null;
        try {
            responseBody = httpClient.execute(httpGet, responseHandler);
        } finally {
            httpGet.abort();
        }
        return responseBody;
    }

    public String get(String url, String charsetName) throws Exception {
        logger.debug("use new HttpClient");
        HttpGet httpGet = new HttpGet(url);

        ResponseHandler<String> responseHandler = new CharsetResponseHandler(Charset.forName(charsetName));

        String responseBody = null;
        try {
            responseBody = httpClient.execute(httpGet, responseHandler);
        } finally {
            httpGet.abort();
        }
        return responseBody;
    }

    /**
     * post 请求，讲object参数 转为 json 字符串
     * 
     * @param uri
     * @param object
     * @return
     */
    public String post(String uri, Object object) {
        HttpPost httpPost = new HttpPost(uri);
        String entityStr = JSON.toJSONString(object);
        HttpEntity entity = new StringEntity(entityStr, "UTF-8");
        httpPost.setEntity(entity);

        ResponseHandler<Map<String,String>> responseHandler = new SessonResponseHandler();
        Map<String,String> result = null;
        try {
        	RequestConfig localConfig = RequestConfig.custom()
        		    .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
        		    .build();
        	httpPost.setConfig(localConfig);
        	result = httpClient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            logger.error("httpClient出错,param=" + entityStr, e);
        } finally {
            httpPost.abort();
        }
        return result.get("responseBody");
    }
    
    class SessonResponseHandler implements ResponseHandler<Map<String,String>> {

		@Override
		public Map<String,String> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			Map<String,String> result = new HashMap<String,String>();
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            Header[] headers = response.getAllHeaders();
            for(int i = 0 ; i < headers.length ; i ++){
            	if(headers[i].getName().equalsIgnoreCase("set-cookie")){
            		result.put("cookie",headers[i].getValue());
            		break;
            	}
            }
            logger.error(result.get("cookie"));
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            result.put("responseBody", entity == null ? null : EntityUtils.toString(entity));
            return result;
		}
    	
    }

    class CharsetResponseHandler implements ResponseHandler<String> {

        private Charset defaultCharSet;

        public CharsetResponseHandler(Charset defaultCharSet) {
            this.defaultCharSet = defaultCharSet;
        }

        public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
        	
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            
            ContentType ct = ContentType.getOrDefault(entity);
            Charset charSet = ct.getCharset();
            if (charSet == null) {
                charSet = defaultCharSet;
            }

            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            return entity == null ? null : EntityUtils.toString(entity, charSet);
        }
    }
}
