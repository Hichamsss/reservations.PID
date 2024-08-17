package be.iccbxl.pid.Config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
	
    private String apiKey = "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";

    public String getApiKey() {
        return apiKey;
    }


}
