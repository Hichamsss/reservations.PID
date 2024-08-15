package be.iccbxl.pid.Config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
	
    private String apiKey = "sk_test_51OL1SZEE3iAsaZHF2cg38GvmDIR0dFLMzxbt5SZX3ejfFxqK7EgE4dFYkgDLcjGq4QVZeFVZ4KjFEXTnHA3axxLZ005TkzrS3R";

    public String getApiKey() {
        return apiKey;
    }


}
