package com.moris.desktop.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ExternalConfigPostProcessor implements EnvironmentPostProcessor {
	// Change this string to match your exact environment variable name
	private static final String APP_HOME_ENV = "MORIS_HOME";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, @NonNull SpringApplication application) {
		String appHome = environment.getProperty(APP_HOME_ENV);

		if (appHome == null || appHome.isEmpty()) {
			log.error("Environment variable " + APP_HOME_ENV + " is not set.");
			return;
		}

		// Construct path: ${MY_APP_HOME}/config/config.properties
		File     configFile = new File(appHome + File.separator + "config" + File.separator + "config.properties");
		Resource resource   = new FileSystemResource(configFile);

		if (resource.exists()) {
			try {
				Properties               properties     = PropertiesLoaderUtils.loadProperties(resource);
				PropertiesPropertySource propertySource = new PropertiesPropertySource("externalConfig", properties);
				// Add to the front so it overrides application.properties if duplicates exist
				environment.getPropertySources().addFirst(propertySource);
			} catch (IOException e) {
				throw new IllegalStateException("Failed to load external configuration from " + configFile.getAbsolutePath(), e);
			}
		} else {
			log.error("Configuration file not found at {}", configFile.getAbsolutePath());
		}
	}
}

