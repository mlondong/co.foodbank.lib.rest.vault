package co.com.foodbank.vault.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.com.foodbank.user.sdk.service.SDKUserService;

@Configuration
@Qualifier("sdkUser")
public class ConfigSDKUserService {
    @Bean
    public SDKUserService sdkUser() {
        return new SDKUserService();
    }
}
