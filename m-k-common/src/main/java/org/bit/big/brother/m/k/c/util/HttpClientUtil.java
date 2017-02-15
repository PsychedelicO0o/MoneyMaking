package org.bit.big.brother.m.k.c.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.bit.big.brother.m.k.d.domain.LoginBean;
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
        logger.info("初始化 httpclient 连接池");

        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(connection_timeout)
                .setConnectionRequestTimeout(connection__request_timeout).setSocketTimeout(so_timeout).build();

        HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(0, false);
        httpClient = HttpClients.custom().setRetryHandler(retryHandler).setConnectionManager(cm).setDefaultRequestConfig(defaultRequestConfig).build();
    }

    public String get(String url) throws Exception {
        
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
     * @throws IOException 
     */
    public String post(String uri, Object object) throws IOException {
    	
        HttpPost httpPost = new HttpPost(uri);
        
        String entityStr = JSON.toJSONString(object);
        HttpEntity entity = new StringEntity(entityStr, "UTF-8");
        httpPost.setEntity(entity);

        ResponseHandler<Map<String,String>> responseHandler = new SessonResponseHandler();
        Map<String,String> result = null;
        try {
        	result = httpClient.execute(httpPost, responseHandler);
        } finally {
            httpPost.abort();
        }
        return result.get("responseBody");
    }
    
    public String post(String cookieUrl,String lkIncUrl,String imgUrl,String loginUrl, LoginBean loginBean) throws IOException {
    	
        HttpPost cookiePost = new HttpPost(cookieUrl);
        HttpPost lhIncPost = new HttpPost(lkIncUrl);
        HttpPost loginPost = new HttpPost(loginUrl);
        HttpPost imgPost = new HttpPost(imgUrl);

        ResponseHandler<Map<String,String>> responseHandler = new SessonResponseHandler();
        Map<String,String> result = null;
        try {
        	result = httpClient.execute(cookiePost, responseHandler);
        	result = httpClient.execute(lhIncPost, responseHandler);
        	
        	String reStr = result.get("responseBody").trim();
        	logger.error(reStr);
        	loginBean.setLk(reStr.substring(4, reStr.length()));
        	File f = httpClient.execute(imgPost, new ImageFileResponseHandler("D:/tmpImg/"+System.currentTimeMillis()+".jpg"));
        	
        	Scanner s = new Scanner(System.in);
        	
        	loginBean.setGjjcxjjmyhpppp(s.nextLine());
        	
        	String entityStr = JSON.toJSONString(loginBean);
            HttpEntity entity = new StringEntity(entityStr, "UTF-8");
            loginPost.setEntity(entity);
        	
        	result = httpClient.execute(loginPost, responseHandler);
        //	
        } finally {
        	cookiePost.abort();
        	lhIncPost.abort();
        	loginPost.abort();
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
    
    class ImageFileResponseHandler implements ResponseHandler<File>{

    	private String filePathName;
    	public ImageFileResponseHandler(String filePathName) {
    		
    		this.filePathName = filePathName;
		}
		@Override
		public File handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			
			final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            
            File storeFile = new File(filePathName);
			FileOutputStream output = new FileOutputStream(storeFile);
            
            if (entity != null) {
            	InputStream instream = entity.getContent();
            	try {
            		byte b[] = new byte[1024];
            		int j = 0;
            		while( (j = instream.read(b))!=-1){
            			output.write(b,0,j);
            		}
            	}catch(Exception e){
            		throw e;
            	}finally{
            		output.flush();
            		output.close();
            		instream.close();
            	}
            }
			return storeFile;
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
