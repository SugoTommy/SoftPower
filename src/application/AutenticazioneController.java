
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AutenticazioneController extends BackToGara {
	Autenticazione autenticazione = new Autenticazione();

	@FXML
	private AnchorPane anchor;
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="input_username"
	private TextField input_username; // Value injected by FXMLLoader

	@FXML // fx:id="input_password"
	private TextField input_password; // Value injected by FXMLLoader

	@FXML // fx:id="error_username"
	private Label error; // Value injected by FXMLLoader

	@FXML // fx:id="login"
	private Button login; // Value injected by FXMLLoader

	@FXML
	PasswordField passwordField;
	@FXML
	private CheckBox checkbox;

	@FXML
	private Label lbl_user;

	@FXML
	private Label lbl_pass;

	@FXML
	private ImageView theme;
	static String tema = "dark";

	// RAMOS
	@FXML
	public void changeTheme() {
		if (tema.equals("dark")) {
			tema = "light";
			anchor.setStyle("-fx-background-color: white;");
			input_username.setStyle(
					"-fx-border-color: #e8eaf2; -fx-background-color: white; -fx-background-radius:5; -fx-border-radius:5; -fx-text-fill: black;");
			input_password.setStyle(
					"-fx-border-color: #e8eaf2; -fx-background-color: white; -fx-background-radius:5; -fx-border-radius:5; -fx-text-fill: black;");
			passwordField.setStyle(
					"-fx-border-color: #e8eaf2; -fx-background-color: white; -fx-background-radius:5; -fx-border-radius:5; -fx-text-fill: black;");
			checkbox.setId("checklight");
			login.setStyle("-fx-text-fill: white; -fx-background-color:  #23d366; -fx-background-radius: 5;");
			theme.setImage(new Image(getClass().getResourceAsStream("/images/moon.png")));

		} else if (tema.equals("light")) {
			tema = "dark";
			anchor.setStyle("-fx-background-color: #0f1318;");
			input_username.setStyle(
					"-fx-border-color:  #2d3136; -fx-background-color:  #1f2328; -fx-background-radius:5; -fx-border-radius:5; -fx-text-fill: white; ");
			input_password.setStyle(
					"-fx-border-color:  #2d3136; -fx-background-color:  #1f2328; -fx-background-radius:5; -fx-border-radius:5; -fx-text-fill: white;");
			passwordField.setStyle(
					"-fx-border-color:  #2d3136; -fx-background-color:  #1f2328; -fx-background-radius:5; -fx-border-radius:5; -fx-text-fill: white;");
			checkbox.setId("checkdark");
			login.setStyle("-fx-text-fill: #0f1318; -fx-background-color:  #23d366; -fx-background-radius: 5;");
			theme.setImage(new Image(getClass().getResourceAsStream("/images/sun.png")));

		}
	}

	// RAMOS
	@FXML
	public void mostraPassword() {
		if (passwordField.isVisible()) {
			input_password.setText(passwordField.getText());
			input_password.setVisible(true);
			passwordField.setVisible(false);
		} else {
			passwordField.setText(input_password.getText());
			passwordField.setVisible(true);
			input_password.setVisible(false);
		}
	}

	// ZUNIGA - BROSOTO - DRAGONETTI
	@FXML
	boolean accedi(ActionEvent event) {
		input_username.textProperty().addListener((observable, oldValue, newValue) -> {
			error.setVisible(false);
		});
		input_password.textProperty().addListener((observable, oldValue, newValue) -> {
			error.setVisible(false);
		});
		String username = input_username.getText().replaceAll(" ", "-").toLowerCase(), password;
		if (passwordField.isVisible()) {
			password = passwordField.getText();
		} else {
			password = input_password.getText();
		}
		if (username.trim().length() == 0) {
			error.setText("Inserisci un username.");
			error.setVisible(true);
			return false;
		} else {
			error.setVisible(false);

		}
		if (password.trim().length() == 0) {
			error.setText("Inserisci una password.");
			error.setVisible(true);
			return false;
		} else {
			error.setVisible(false);
		}
		if (autenticazione.verificaPassword(username.trim(), password.trim())) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
				Parent root = loader.load();
				ChatController chatController = loader.getController();
				chatController.gara = this.gara;
				chatController.userMitt = username.trim();
				chatController.init();
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			error.setText("Username o password errati.");
			error.setVisible(true);
		}
		return true;
	}
	
	public void initialize() {	
		changeTheme();
	}

}
