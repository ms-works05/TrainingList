package jp.crossabilitys.work.TrainingList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrainingListApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingListApplication.class, args);
	}

}
