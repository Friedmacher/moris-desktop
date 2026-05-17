package com.moris.desktop.security.login;

import com.moris.desktop.config.FxmlView;
import com.moris.desktop.config.StageManager;
import com.moris.desktop.security.user.User;
import com.moris.desktop.security.user.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {
	private final StageManager              stageManager;
	private final UserService               userService;
	private final ApplicationEventPublisher eventPublisher;

	ResourceBundle resources;
	StringProperty messageProperty = new SimpleStringProperty();

	@FXML
	private Button        btnLogin;
	@FXML
	private Button        btnRegister;
	@FXML
	private Button        btnReset;
	@FXML
	private TextArea      messageArea;
	@FXML
	private PasswordField tfPassword;
	@FXML
	private TextField     tfUserName;

	@Lazy
	public LoginController(StageManager stageManager, UserService userService, ApplicationEventPublisher eventPublisher) {
		this.stageManager   = stageManager;
		this.userService    = userService;
		this.eventPublisher = eventPublisher;
	}

	@FXML
	void doLogin(ActionEvent event) {
		Optional<User> user = userService.findByUserNameAndPassword(tfUserName.getText(), tfPassword.getText());
		if (user.isPresent()) {
			eventPublisher.publishEvent(new LoginEvent(this, tfUserName.getText()));
			this.stageManager.switchToNextScene(FxmlView.HOME);
		} else {
			messageProperty.set("Invalid username or password!");
		}
	}

	@FXML
	void doPasswordReset(ActionEvent event) {
		// Generate the 32-digit random password
		String password = PasswordGenerator.generate(32);

		// Save the generated password to the user.
		userService.resetPassword(tfUserName.getText(), password);

		TextField passwordField = new TextField(password);
		passwordField.setEditable(false);

		Button btnCopyPassword = new Button("Copy Password");
		btnCopyPassword.setOnAction(e -> {
			final Clipboard        clipboard = Clipboard.getSystemClipboard();
			final ClipboardContent content   = new ClipboardContent();
			content.putString(password);
			clipboard.setContent(content);
		});

		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.getChildren().add(passwordField);
		hBox.getChildren().add(btnCopyPassword);

		// Create the Alert
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Security Update");
		alert.setHeaderText("New Password Generated for: " + tfUserName.getText());
		alert.getDialogPane().setContent(hBox);

		// Display the alert.
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			eventPublisher.publishEvent(new PasswordResetEvent(this, tfUserName.getText()));
			messageProperty.set("Password reset done!");
		}
	}

	@FXML
	void doRegister(ActionEvent event) {
		Optional<User> user = userService.findByUserName(tfUserName.getText());
		if (user.isPresent()) {
			messageProperty.set("User already exists!");
		} else {
			Optional<User> registeredUser = userService.registerNewUser(tfUserName.getText());

			String password = registeredUser.get().getPassword();

			TextField passwordField = new TextField(password);
			passwordField.setEditable(false);

			Button btnCopyPassword = new Button("Copy Password");
			btnCopyPassword.setOnAction(e -> {
				final Clipboard        clipboard = Clipboard.getSystemClipboard();
				final ClipboardContent content   = new ClipboardContent();
				content.putString(password);
				clipboard.setContent(content);
			});

			HBox hBox = new HBox();
			hBox.setSpacing(10);
			hBox.getChildren().add(passwordField);
			hBox.getChildren().add(btnCopyPassword);

			// Create the Alert
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("User Registration");
			alert.setHeaderText(String.format("The user '%s' has been registered!", tfUserName.getText()));
			alert.getDialogPane().setContent(hBox);

			// Display the alert.
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				eventPublisher.publishEvent(new UserRegisteredEvent(this, tfUserName.getText()));
				messageProperty.set("New user registration done!");
			}
		}
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
		this.resources = resources;
		btnLogin.disableProperty().bind(tfUserName.textProperty().isEmpty());
		btnReset.disableProperty().bind(tfUserName.textProperty().isEmpty());
		btnRegister.disableProperty().bind(tfUserName.textProperty().isEmpty());
		messageArea.visibleProperty().bind(messageProperty.isNotEmpty());
		messageArea.textProperty().bind(messageProperty);
	}

	@EventListener
	public void handleLogoutEvent(LogoutEvent event) {
		messageProperty.set("'%s' logged out!".formatted(event.getUserName()));
	}
}
