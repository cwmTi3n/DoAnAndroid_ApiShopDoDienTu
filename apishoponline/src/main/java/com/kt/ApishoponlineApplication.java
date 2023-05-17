package com.kt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kt.services.ImageService;
import org.springframework.context.annotation.Bean;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;

@SpringBootApplication
public class ApishoponlineApplication implements CommandLineRunner{
	@Autowired
    ImageService imageService;
	public static void main(String[] args) {
		SpringApplication.run(ApishoponlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		imageService.init();
	}

	@Bean
	public HttpClient httpClient() throws Exception {
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, (certificate, authType) -> true)
				.build();

		SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);

		return HttpClientBuilder.create()
				.setSSLSocketFactory(sslConnectionFactory)
				.build();
	}

}
