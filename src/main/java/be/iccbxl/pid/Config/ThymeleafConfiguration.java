package be.iccbxl.pid.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ThymeleafConfiguration {
	@Bean
	public LayoutDialect thymeleafDialect() {
	    return new LayoutDialect();
	}
}
