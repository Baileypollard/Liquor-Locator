package com.liqourlocator.Liquor.Locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LiquorLocatorApplication extends SpringBootServletInitializer
{

	public static void main(String[] args)
	{
		SpringApplication.run(LiquorLocatorApplication.class, args);
	}

	private static Class<LiquorLocatorApplication> applicationClass = LiquorLocatorApplication.class;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(applicationClass);
	}
}
