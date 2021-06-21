package co.com.foodbank.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import co.com.foodbank.contribution.sdk.config.EnableContributionSDK;

@SpringBootApplication
@EnableContributionSDK
@ComponentScan(basePackages = "co.com.foodbank.vault")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
