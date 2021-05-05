package me.kalpha.trsubscribeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;

@SpringBootApplication
public class TrSubscribeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrSubscribeServiceApplication.class, args);
	}

	@Bean
	public ByteArrayJsonMessageConverter messageConverter() {
		return new ByteArrayJsonMessageConverter();
	}
}
