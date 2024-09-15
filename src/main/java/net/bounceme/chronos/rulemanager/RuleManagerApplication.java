package net.bounceme.chronos.rulemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RuleManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuleManagerApplication.class, args);
	}

}
