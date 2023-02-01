package ru.trick.springmangabot;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringMangaBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMangaBotApplication.class, args);
	}

	@Bean
	public HttpClient httpClient() {
		return HttpClientBuilder.create().build();
	}

	@Bean
	public RestTemplate restTemplate () {
		return new RestTemplate();
	}

}
