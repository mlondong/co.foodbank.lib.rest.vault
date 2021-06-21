package co.com.foodbank.vault.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import co.com.foodbank.contribution.sdk.config.EnableContributionSDK;

@Configuration
@EnableContributionSDK
@ComponentScan(basePackages = "co.com.foodbank.vault")
public class ConfigurationApp {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
