package application;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChatController extends BackToGara {
	@FXML
	FlowPane contenitore_messaggi;
	@FXML
	FlowPane contenitoreContatti;
	@FXML
	TextField input;
	@FXML
	Label dest;
	@FXML
	Label lbl_chats;
	@FXML
	ImageView destPfp;
	@FXML
	ScrollPane scrollMessaggi;
	@FXML
	ScrollPane scroll_contatti;
	@FXML
	AnchorPane pane;
	@FXML
	private ImageView tema;
	@FXML
	private Line vLine;
	@FXML
	private Line hLine;
	@FXML
	private ImageView send;
	@FXML
	private AnchorPane bottom_bar;
	@FXML
	private AnchorPane top_bar;

	String userMitt;
	GestoreRubrica gestoreRubrica;
	String userDest;
	Chat chat;

	String sentMessage, receivedMessage, hoverColor, selectedColor, contentColor, nameColor = "#ffffff";

	// RAMOS
	@FXML
	void logout(ActionEvent event) {
		if (AutenticazioneController.tema.equals("dark")) {
			AutenticazioneController.tema = "light";
		} else {
			AutenticazioneController.tema = "dark";
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Autenticazione.fxml"));
			AnchorPane root = loader.load();
			AutenticazioneController autenticazioneController = loader.getController();
			autenticazioneController.gara = this.gara;
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// RAMOS
	@FXML
	void cambiaTema() {
		if (AutenticazioneController.tema.equals("dark")) {
			AutenticazioneController.tema = "light";
			tema.setImage(new Image(getClass().getResourceAsStream("/images/moonlight.png")));
			scroll_contatti.setId("scrollLight");
			scrollMessaggi.setId("scrollLight");
			contenitore_messaggi.setStyle("-fx-background-color: #efeae2;");
			scrollMessaggi.setStyle("-fx-background-color: #efeae2;");
			top_bar.setStyle("-fx-background-color: #f0f2f5;");
			bottom_bar.setStyle("-fx-background-color: #f0f2f5;");
			contenitoreContatti.setStyle("-fx-background-color: white;");
			scroll_contatti.setStyle("-fx-background-color: white;");
			lbl_chats.setStyle("-fx-background-color: white; -fx-text-fill: #131d22;");
			dest.setStyle("-fx-text-fill: #131d22;");
			input.setStyle("-fx-background-color: white; -fx-text-fill: #131d22;");
			vLine.setStroke(Paint.valueOf("#d1d7db"));
			hLine.setStroke(Paint.valueOf("#d1d7db"));
			vLine.setStrokeWidth(0.7);
			hLine.setStrokeWidth(3.0);
			send.setImage(new Image(getClass().getResourceAsStream("/images/sendlight.png")));
			sentMessage = "#d9fdd2;";
			receivedMessage = "#ffffff;";
			hoverColor = "#f5f6f6;";
			selectedColor = "#f0f2f5;";
			contentColor = "#131d22";
			nameColor = "#131d22";
		} else {
			AutenticazioneController.tema = "dark";
			tema.setImage(new Image(getClass().getResourceAsStream("/images/sunlight.png")));
			scroll_contatti.setId("scrollDark");
			scrollMessaggi.setId("scrollDark");
			contenitore_messaggi.setStyle("-fx-background-color: #1c2d36;");
			scrollMessaggi.setStyle("-fx-background-color: #1c2d36;");
			top_bar.setStyle("-fx-background-color: #2a3a42;");
			bottom_bar.setStyle("-fx-background-color: #2a3a42;");
			contenitoreContatti.setStyle("-fx-background-color: #15242c;");
			scroll_contatti.setStyle("-fx-background-color: #15242c;");
			lbl_chats.setStyle("-fx-background-color: #15242c; -fx-text-fill: #ffffff;");
			dest.setStyle("-fx-text-fill: #ffffff;");
			input.setStyle("-fx-background-color: #374954; -fx-text-fill: #f4f4f5;");
			vLine.setStroke(Paint.valueOf("#374044"));
			hLine.setStroke(Paint.valueOf("#374044"));
			vLine.setStrokeWidth(0.7);
			hLine.setStrokeWidth(3.0);
			send.setImage(new Image(getClass().getResourceAsStream("/images/send.png")));
			sentMessage = "#006e5d;";
			receivedMessage = "#2a3a42;";
			hoverColor = "#1f2c33;";
			selectedColor = "#2a3942;";
			contentColor = "#f4f4f5";
			nameColor = "#7a9d97";
		}
		contenitoreContatti.getChildren().clear();
		inizializzaContatti();
		cambioUser();
	}

	// DRAGONETTI - ZUNIGA
	@FXML
	void inizializzaContatti() {
		if (userMitt.equals("amministrazione"))
			contenitoreContatti.setPrefWidth(344);
		for (int i = 0; i < gestoreRubrica.contatti.size(); i++) {
			AnchorPane contatto = new AnchorPane();
			contatto.setPrefHeight(80);
			contatto.setPrefWidth(341);
			contatto.setCursor(Cursor.HAND);
			contatto.setId(gestoreRubrica.contatti.get(i));
			contatto.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
				if (newValue) {
					if (!contatto.getId().equalsIgnoreCase(userDest)) {
						contatto.setStyle("-fx-background-color: " + hoverColor);
					}
				} else {
					if (!contatto.getId().equalsIgnoreCase(userDest)) {
						contatto.setStyle("-fx-background-color:  transparent;");
					}

				}
			});
			contatto.setOnMouseClicked(this::cambioUser);
			String path = gestoreRubrica.contatti.get(i);
			Image userIconn = new Image(getClass().getResourceAsStream("/comunicazione/" + path + "/pfp.png"));
			ImageView userIcon = new ImageView();
			userIcon.setImage(userIconn);
			userIcon.setFitWidth(64.0);
			userIcon.setFitHeight(64.0);
			userIcon.setLayoutX(15.0);
			userIcon.setLayoutY(7.0);
			userIcon.setPreserveRatio(true);
			Label userName = new Label(dashedToUppercase(gestoreRubrica.contatti.get(i)));
			userName.setTextFill(javafx.scene.paint.Color.valueOf(nameColor));
			userName.prefWidth(232);
			userName.prefHeight(17);
			userName.setLayoutX(92);
			userName.setLayoutY(20);
			userName.setFont(new Font("Arial", 20));
			Line line = new Line();
			line.setStartX(0.0f);
			line.setStartY(80.0f);
			line.setEndX(341.0f);
			line.setEndY(80.0f);
			line.setStroke(Paint.valueOf("#374044"));
			line.setStrokeWidth(0.5);
			contatto.getChildren().addAll(userIcon, userName, line);
			contenitoreContatti.getChildren().add(contatto);

		}
	}

	// MARTINO 
	@FXML
	public void cambioUser(MouseEvent e) {
		pane.setStyle("-fx-background-color: transparent;");
		userDest = ((Node) e.getSource()).getId();
		pane = (AnchorPane) contenitoreContatti.lookup("#" + userDest);
		pane.setStyle("-fx-background-color: " + selectedColor);
		dest.setText(dashedToUppercase(userDest));
		chat = new Chat(userMitt, userDest);
		contenitore_messaggi.getChildren().clear();
		Image userIconn = new Image(getClass().getResourceAsStream("/comunicazione/" + userDest + "/pfp.png"));
		destPfp.setImage(userIconn);
		caricaMessaggi();
	}

	// MARTINO 
	public void cambioUser() {
		pane = (AnchorPane) contenitoreContatti.lookup("#" + userDest);
		pane.setStyle("-fx-background-color: " + selectedColor);
		dest.setText(dashedToUppercase(userDest));
		chat = new Chat(userMitt, userDest);
		Image userIconn = new Image(getClass().getResourceAsStream("/comunicazione/" + userDest + "/pfp.png"));
		destPfp.setImage(userIconn);
		contenitore_messaggi.getChildren().clear();
		caricaMessaggi();
	}

	// MARTINO - ALONSO
	@FXML
	public void caricaMessaggi() {
		if (chat.messaggi.size() > 6)
			contenitore_messaggi.setPrefWidth(904); // appare lo scroll
		for (int i = 0; i < chat.messaggi.size(); i++) {
			Pane contenitore = new Pane();
			contenitore.setPrefHeight(80.0);
			contenitore.setPrefWidth(901);
			Pane messaggio = new Pane();
			messaggio.setPrefHeight(70.0);
			messaggio.setLayoutY(10.0);
			messaggio.setPrefWidth(440.5);
			messaggio.setId("messaggio");
			Label testo = new Label(chat.messaggi.get(i).testo);
			testo.setTextFill(javafx.scene.paint.Color.valueOf(contentColor));
			testo.setLayoutX(10.0);
			testo.setLayoutY(10.0);
			testo.setFont(Font.font("Arial", 24));
			Label time = new Label(chat.messaggi.get(i).tempo);
			time.setTextFill(javafx.scene.paint.Color.valueOf("#7a9d97"));
			time.setLayoutX(379.5);
			time.setLayoutY(48.0);
			time.setFont(Font.font("Arial", 14));
			if (userMitt.equalsIgnoreCase(chat.messaggi.get(i).proprietario)) {
				messaggio.setLayoutX(450.5);
				messaggio.setStyle("-fx-background-color: " + sentMessage);
			} else {
				messaggio.setLayoutX(10.0);
				messaggio.setStyle("-fx-background-color: " + receivedMessage);
			}
			messaggio.getChildren().addAll(testo, time);
			contenitore.getChildren().add(messaggio);
			contenitore_messaggi.getChildren().add(contenitore);
		}
	}
	
	// MARTINO
	@FXML
	public void invia() {
		String messaggio = input.getText();
		if (messaggio == null || messaggio.length() == 0 || messaggio.length() > 30) {

		} else {
			chat.aggiungiMessaggio(messaggio);
			contenitore_messaggi.getChildren().clear();
			caricaMessaggi();
		}
		input.setText(null);
	}

	// SCIMONE
	public String dashedToUppercase(String dashed) {
		String abc = "";
		String[] userDestNoDash = dashed.split("-");
		for (int i = 0; i < userDestNoDash.length; i++) {
			String word = userDestNoDash[i];
			if (word.length() > 0) {
				char firstChar = Character.toUpperCase(word.charAt(0));
				String restOfWord = word.substring(1);
				word = firstChar + restOfWord;
			}
			abc += word + " ";
		}
		return abc.trim();
	}

	// ADMINS
	public void init() {
		gestoreRubrica = new GestoreRubrica(userMitt);
		userDest = gestoreRubrica.contatti.get(0);
		chat = new Chat(userMitt, userDest);
		if (AutenticazioneController.tema.equals("dark")) {
			AutenticazioneController.tema = "light";
		} else {
			AutenticazioneController.tema = "dark";
		}
		inizializzaContatti();
		cambiaTema();

	}

}
