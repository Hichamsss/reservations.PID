package be.iccbxl.pid.Config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
	
    private String apiKey = "stripe.apiKey";

    public String getApiKey() {
        return apiKey;
    }


}
