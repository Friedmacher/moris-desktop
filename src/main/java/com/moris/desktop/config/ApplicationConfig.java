package com.moris.desktop.config;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class ApplicationConfig {
	private final FxmlLoader fxmlLoader;
	private final String applicationTitle;
	private final String applicationHome;
	private final ApplicationEventPublisher eventPublisher;


	public ApplicationConfig(FxmlLoader fxmlLoader, @Value("${spring.application.name}") String applicationTitle, @Value("${spring.application.home}") String applicationHome, ApplicationEventPublisher eventPublisher) {
		this.fxmlLoader = fxmlLoader;
		this.applicationTitle = applicationTitle;
		this.applicationHome = applicationHome;
		this.eventPublisher = eventPublisher;
	}

	@Bean
	@Lazy
	public StageManager stageManager(Stage stage) throws IOException {
		return new StageManager(fxmlLoader, stage, applicationTitle, eventPublisher);
	}

	@Bean
	public WorkspaceManager workspaceManager() throws IOException {
		return new WorkspaceManager(applicationHome);
	}
}
