package com.moris.desktop;

import com.moris.desktop.config.FxmlView;
import com.moris.desktop.config.StageManager;
import com.moris.desktop.config.WorkspaceManager;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class MorisFX extends Application {
	private static Stage stage;

	 private ConfigurableApplicationContext applicationContext;
	 private WorkspaceManager workspaceManager;
	 private StageManager stageManager;

	/**
	 * The application initialization method. This method is called immediately
	 * after the Application class is loaded and constructed. An application may
	 * override this method to perform initialization prior to the actual starting
	 * of the application.
	 *
	 * <p>
	 * The implementation of this method provided by the Application class does nothing.
	 * </p>
	 *
	 * <p>
	 * NOTE: This method is not called on the JavaFX Application Thread. An
	 * application must not construct a Scene or a Stage in this
	 * method.
	 * An application may construct other JavaFX objects in this method.
	 * </p>
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override
	public void init() throws Exception {
		log.info("Application Initializing");
		applicationContext = new SpringApplicationBuilder(MorisMain.class).run();
		workspaceManager = applicationContext.getBean(WorkspaceManager.class);
		log.info("Application Initialized");
	}

	/**
	 * The main entry point for all JavaFX applications.
	 * The start method is called after the init method has returned,
	 * and after the system is ready for the application to begin running.
	 *
	 * <p>
	 * NOTE: This method is called on the JavaFX Application Thread.
	 * </p>
	 *
	 * @param primaryStage the primary stage for this application, onto which
	 *                     the application scene can be set.
	 *                     Applications may create other stages, if needed, but they will not be
	 *                     primary stages.
	 * @throws Exception if something goes wrong
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		log.info("Application Starting");
		stage = primaryStage;
		stageManager = applicationContext.getBean(StageManager.class, primaryStage);
		showLoginScene();
		log.info("Application Started");
	}

	/**
	 * This method is called when the application should stop, and provides a
	 * convenient place to prepare for application exit and destroy resources.
	 *
	 * <p>
	 * The implementation of this method provided by the Application class does nothing.
	 * </p>
	 *
	 * <p>
	 * NOTE: This method is called on the JavaFX Application Thread.
	 * </p>
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override
	public void stop() throws Exception {
		log.info("Application Stopping");
		System.out.println("Width:  " + stage.getWidth());
		System.out.println("Height: " + stage.getHeight());

		applicationContext.close();
		stage.close();
		log.info("Application Stopped");
	}

	private void showLoginScene() {
		stageManager.switchScene(FxmlView.LOGIN);
	}
}
