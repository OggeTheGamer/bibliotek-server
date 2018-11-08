package nu.ssis.a18mosu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BiblotekApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblotekApplication.class, args);
	}
}
