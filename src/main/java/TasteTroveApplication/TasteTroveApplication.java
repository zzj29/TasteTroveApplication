package TasteTroveApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import TasteTroveApplication.security.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class TasteTroveApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasteTroveApplication.class, args);
	}

}
