package co.com.foodbank.vault.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import co.com.foodbank.contribution.sdk.config.EnableContributionSDK;
import co.com.foodbank.user.sdk.config.EnableUserSDK;

@Configuration
@EnableContributionSDK
@EnableUserSDK
@ComponentScan(basePackages = "co.com.foodbank.vault")
public class ConfigurationApp {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(
                        org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
}
