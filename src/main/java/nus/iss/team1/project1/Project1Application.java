package nus.iss.team1.project1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class Project1Application {

	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);
	}

	@GetMapping(value = "/getUser",produces = {"application/json;charset=UTF-8"})
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}


	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}


	@PostMapping("/post/demo")
	public Greeting demo(@RequestBody Greeting greeting) {
		return greeting;
	}

//	private static final Logger log = LoggerFactory.getLogger(Project1Application.class);

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
//
//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//		return args -> {
//			Quote quote = restTemplate.getForObject(
//					"https://quoters.apps.pcfone.io/api/random", Quote.class);
//			log.info(quote.toString());
//		};
//	}
}



