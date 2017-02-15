package org.bit.big.brother.m.k.cap.tu;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bit.big.brother.m.k.cap.t.svm.Trainning;
import org.junit.experimental.theories.Theories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaDownloader {
	
	private static String p = CaptchaDownloader.class.getClassLoader().getResource(".").getPath();
	
	private static Logger logger = LoggerFactory.getLogger(CaptchaDownloader.class);
	
	private Runnable task;

	public CaptchaDownloader() {
		this.task = new Runnable() {
			

			@Override
			public void run() {
				CloseableHttpClient client = HttpClients.createDefault();
				HttpGet get = new HttpGet(
						"http://www.bjgjj.gov.cn/wsyw/servlet/PicCheckCode1");
				CloseableHttpResponse response = null;
				OutputStream outStream = null;
				try {
					logger.error("start...."+ Thread.currentThread().getId());
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						
						File f = new File("D:/tmpImg/"+System.currentTimeMillis()+".png");
						
						outStream = new BufferedOutputStream(new FileOutputStream(f));
						entity.writeTo(outStream);
						outStream.flush();
						logger.error("success...."+Thread.currentThread().getId());
					}
					logger.error("complete...."+Thread.currentThread().getId());
				}catch (IOException e) {
					e.printStackTrace();
				}finally {
					if (outStream != null) {
						try {
							outStream.close();
						} catch (IOException e) {
						}
					}
					if (response != null) {
						try {
							response.close();
						} catch (IOException e) {
							
						}
					}
				}

			}
		};
	}

	public void download() {
		new Thread(task).start();
		return;
	}
	
	public static void main(String[] args) {
		CaptchaDownloader c = new CaptchaDownloader();
		for (int i = 0; i < 100; i++) {
			c.download();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

}
