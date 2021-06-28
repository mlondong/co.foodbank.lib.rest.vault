package co.com.foodbank.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import co.com.foodbank.contribution.sdk.config.EnableContributionSDK;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableContributionSDK
@ComponentScan(basePackages = "co.com.foodbank.vault")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
