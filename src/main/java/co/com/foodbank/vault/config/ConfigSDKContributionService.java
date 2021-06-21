package co.com.foodbank.vault.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.com.foodbank.contribution.sdk.service.SDKContributionService;

@Configuration
@Qualifier("sdkContribution")
public class ConfigSDKContributionService {

    @Bean
    public SDKContributionService sdkContribution() {
        return new SDKContributionService();
    }

}
