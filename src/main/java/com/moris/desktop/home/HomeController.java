package com.moris.desktop.home;

import com.moris.desktop.config.FxmlView;
import com.moris.desktop.config.StageManager;
import com.moris.desktop.security.login.LoginEvent;
import com.moris.desktop.security.login.LogoutEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HomeController implements Initializable {
	private final StageManager              stageManager;
	private final ApplicationEventPublisher eventPublisher;

	StringProperty userNameProperty = new SimpleStringProperty();

	@FXML
	private Label        helloLabel;
	@FXML
	private ToggleGroup  menuButtons;
	@FXML
	private ToggleButton btnHome;
	@FXML
	private ToggleButton btnFinance;
	@FXML
	private ToggleButton btnMaterialManagement;
	@FXML
	private ToggleButton btnBusinessPartner;
	@FXML
	private MenuBar      menuBar;
	@FXML
	private Menu         menuFile;
	@FXML
	private MenuItem     menuItemPreferences;
	@FXML
	private MenuItem menuItemLogout;
	@FXML
	private AnchorPane   paneCenter;


	@Lazy
	public HomeController(StageManager stageManager, ApplicationEventPublisher eventPublisher) {
		this.stageManager = stageManager;
		this.eventPublisher = eventPublisher;
	}

	@FXML
	void showHomePane(ActionEvent event) {

	}

	@FXML
	void showFinancePane(ActionEvent event) {

	}

	@FXML
	void showMaterialManagementPane(ActionEvent event) {

	}

	@FXML
	void showBusinessPartnerPane(ActionEvent event) {

	}

	@FXML
	void showPreferences(ActionEvent event) {

	}

	@FXML
	void showLogin(ActionEvent event) {
		eventPublisher.publishEvent(new LogoutEvent(this, userNameProperty.get()));
		this.stageManager.switchToNextScene(FxmlView.LOGIN);
	}

	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  {@code null} if the location is not known.
	 * @param resources The resources used to localize the root object, or {@code null} if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		helloLabel.textProperty().bind(userNameProperty);
	}

	@EventListener
	public void handleLoginEvent(LoginEvent event) {
		userNameProperty.set(event.getUserName());
	}


}
