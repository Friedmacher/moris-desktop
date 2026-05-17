package com.moris.desktop.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class FxmlLoader {
	@Autowired
	Environment environment;

	private final ApplicationContext context;

	public FxmlLoader(ApplicationContext context) {
		this.context = context;
	}

	public Parent load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean);
		loader.setLocation(getClass().getResource(fxmlPath));
		loader.setResources(ResourceBundle.getBundle(Objects.requireNonNull(environment.getProperty("spring.application.i18n"))));
		return loader.load();
	}
}
