// ADMINS
package application;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BackToGara {

	public Gara gara;

	@FXML
	public void backToGara(Event event) {
		try {
			Image icon = new Image("images\\softpower.png");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root = loader.load();
			MainController main = loader.getController();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			main.gara = this.gara;
			stage.getIcons().clear();
			stage.getIcons().add(icon);
			main.init();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Le mans 24 hour");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
