package com.moris.desktop.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class StageManager {
	@Autowired
	Environment environment;

	private final Stage primaryStage;
	private final FxmlLoader fxmlLoader;
	private final String                    applicationTitle;
	private final ApplicationEventPublisher eventPublisher;


	public StageManager(FxmlLoader fxmlLoader, Stage primaryStage, String applicationTitle, ApplicationEventPublisher eventPublisher) {
		this.fxmlLoader = fxmlLoader;
		this.primaryStage = primaryStage;
		this.applicationTitle = applicationTitle;
		this.eventPublisher = eventPublisher;
	}

	public void switchScene(final FxmlView view) {
		primaryStage.setTitle(applicationTitle);
		Parent rootNode = loadRootNode(view.getFxmlPath());
		Scene scene = new Scene(rootNode, 1500.0, 900.0);  //TODO: Read from user configuration, open the window as big as the last time
		String stylesheet = Objects.requireNonNull(getClass().getResource(Objects.requireNonNull(environment.getProperty("spring.application.style")))).toExternalForm();
		scene.getStylesheets().add(stylesheet);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Parent loadRootNode(String fxmlPath) {
		Parent rootNode;
		try {
			rootNode = fxmlLoader.load(fxmlPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return rootNode;
	}

	public void switchToNextScene(final FxmlView view) {
		Parent rootNode = loadRootNode(view.getFxmlPath());
		primaryStage.getScene().setRoot(rootNode);
		primaryStage.show();
	}

}
