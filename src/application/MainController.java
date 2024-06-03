package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	@FXML
	private Button inizia;

	@FXML
	private Label ora;

	@FXML
	private MenuButton timeMenu;

	@FXML
	private ProgressBar timeProgress;

	@FXML
	private FlowPane chat;

	// scoreboard elements
	@FXML
	private FlowPane scoreboard;
	Pane carRow;
	Label pos;
	Label pilota;
	Label macchina;
	Label tempoInGiro;
	Label giri;
	Label stato;
	Button pop;
	Line line;

	// pop up elements
	@FXML
	private AnchorPane popup;
	@FXML
	private ImageView scuderiaImagePopup;
	@FXML
	private Label idPopup;
	@FXML
	private Label giriPopup;
	@FXML
	private Label modelloPopup;
	@FXML
	private Label motorePopup;
	@FXML
	private Label pilota1Popup;
	@FXML
	private Label pilota2Popup;
	@FXML
	private Label tempoPilota1Popup;
	@FXML
	private Label tempoPilota2Popup;
	@FXML
	private Label pitStopPopup;
	@FXML
	private Label spazioPercorsoPopup;
	@FXML
	private Label migliorTempoPopup;
	@FXML
	private ImageView hypercarImmaginePopup;

	// podio elements
	@FXML
	private AnchorPane podio;
	@FXML
	private ImageView primoImg;
	@FXML
	private ImageView secondoImg;
	@FXML
	private ImageView terzoImg;
	@FXML
	private Label primoTxt;
	@FXML
	private Label secondoTxt;
	@FXML
	private Label terzoTxt;

	Gara gara = new Gara();

	Timer timer, bar;

	String _timeMenu = "LIVE";

	int currentIndexMsg, oraCorrente = 0;

	// ADMINS
	@FXML
	public void init() {
		ora.setText(formattaTempo(gara.tempoCorrente));
		if (gara.iniziata) {
			inizia.setVisible(false);

			MenuItem live = new MenuItem("LIVE");
			timeMenu.getItems().add(live);

			gara.registroOre.forEach(ora -> {
				MenuItem oraItem = new MenuItem(String.valueOf(ora.ora));
				timeMenu.getItems().add(oraItem);
			});

			timeMenu.getItems().forEach(item -> item.setOnAction(event -> {
				MenuItem menuItem = (MenuItem) event.getSource();
				String itemText = menuItem.getText();
				timeMenu.setText(itemText);

				_timeMenu = itemText;

				updateHypercars(_timeMenu.equals("LIVE") ? gara.gestione.hypercars
						: gara.registroOre.get(Integer.valueOf(_timeMenu) - 1).hypercars);

			}));

			oraCorrente = gara.oraCorrente;

			timer = new Timer();
			bar = new Timer();

			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					Platform.runLater(() -> {
						if (oraCorrente != gara.oraCorrente) {
							oraCorrente = gara.oraCorrente;
							MenuItem oraItem = new MenuItem(String.valueOf(oraCorrente));
							oraItem.setOnAction(event -> {
								MenuItem menuItem = (MenuItem) event.getSource();
								String itemText = menuItem.getText();
								timeMenu.setText(itemText);
								_timeMenu = itemText;

								updateHypercars(gara.registroOre.get(Integer.valueOf(_timeMenu) - 1).hypercars);
							});
							timeMenu.getItems().add(oraItem);
						}

						if (_timeMenu.equals("LIVE"))
							updateHypercars(gara.gestione.hypercars);
						updateMessaggi();
						if (gara.fine) {
							updateMessaggi();
							updateHypercars(gara.gestione.hypercars);
							ora.setText(formattaTempo(gara.tempoCorrente));
							timer.cancel();
						}
					});
				}
			}, 0, 500);

			bar.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					Platform.runLater(() -> {
						Double temp = (double) ((double) gara.tempoCorrente / (double) gara.DURATA_GARA);
						timeProgress.setProgress(temp);
						ora.setText(formattaTempo(gara.tempoCorrente));
						if (gara.fine) {
							popup.setVisible(false);
							primoImg.setImage(new Image(getClass().getResourceAsStream(
									"/modelli/" + gara.gestione.hypercars.get(0).modello.replace(" ", "-") + ".png")));
							primoTxt.setText(gara.gestione.hypercars.get(0).modello);
							secondoImg.setImage(new Image(getClass().getResourceAsStream(
									"/modelli/" + gara.gestione.hypercars.get(1).modello.replace(" ", "-") + ".png")));
							secondoTxt.setText(gara.gestione.hypercars.get(1).modello);
							terzoImg.setImage(new Image(getClass().getResourceAsStream(
									"/modelli/" + gara.gestione.hypercars.get(2).modello.replace(" ", "-") + ".png")));
							terzoTxt.setText(gara.gestione.hypercars.get(2).modello);
							podio.setVisible(true);
							inizia.setText("riavvia");
							inizia.setVisible(true);
							bar.cancel();
						}
					});
				};

			}, 0, 10);

		}
	}

	// AHEMD
	@FXML
	void iniziaGara(ActionEvent event) {
		gara = new Gara();
		GestoreRubrica.resettaTutteLeChat();
		inizia.setVisible(false);
		podio.setVisible(false);
		timeMenu.getItems().clear();
		MenuItem live = new MenuItem("LIVE");
		live.setOnAction(e -> {
			timeMenu.setText("LIVE");
			_timeMenu = "LIVE";
			updateHypercars(gara.gestione.hypercars);
		});

		timeMenu.getItems().add(live);
		chat.getChildren().clear();
		timer = new Timer();
		bar = new Timer();
		gara.inizia();
		oraCorrente = 0;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					if (oraCorrente != gara.oraCorrente) {
						oraCorrente = gara.oraCorrente;
						MenuItem oraItem = new MenuItem(String.valueOf(oraCorrente));
						oraItem.setOnAction(event -> {
							MenuItem menuItem = (MenuItem) event.getSource();
							String itemText = menuItem.getText();
							timeMenu.setText(itemText);
							_timeMenu = itemText;

							updateHypercars(gara.registroOre.get(Integer.valueOf(_timeMenu) - 1).hypercars);
						});
						timeMenu.getItems().add(oraItem);
					}

					if (_timeMenu.equals("LIVE"))
						updateHypercars(gara.gestione.hypercars);
					updateMessaggi();
					if (gara.fine) {
						updateMessaggi();
						updateHypercars(gara.gestione.hypercars);
						ora.setText(formattaTempo(gara.tempoCorrente));
						timer.cancel();
					}
				});
			}
		}, 0, 500);

		bar.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					Double temp = (double) ((double) gara.tempoCorrente / (double) gara.DURATA_GARA);
					timeProgress.setProgress(temp);
					ora.setText(formattaTempo(gara.tempoCorrente));
					if (gara.fine) {
						popup.setVisible(false);
						primoImg.setImage(new Image(getClass().getResourceAsStream(
								"/modelli/" + gara.gestione.hypercars.get(0).modello.replace(" ", "-") + ".png")));
						primoTxt.setText(gara.gestione.hypercars.get(0).modello);
						secondoImg.setImage(new Image(getClass().getResourceAsStream(
								"/modelli/" + gara.gestione.hypercars.get(1).modello.replace(" ", "-") + ".png")));
						secondoTxt.setText(gara.gestione.hypercars.get(1).modello);
						terzoImg.setImage(new Image(getClass().getResourceAsStream(
								"/modelli/" + gara.gestione.hypercars.get(2).modello.replace(" ", "-") + ".png")));
						terzoTxt.setText(gara.gestione.hypercars.get(2).modello);
						podio.setVisible(true);
						inizia.setText("riavvia");
						inizia.setVisible(true);
						bar.cancel();
					}
				});
			};

		}, 0, 10);

	}

	// GRANDINI ⭐
	@FXML
	void chiudiPopup(ActionEvent event) {
		popup.setVisible(false);
	}

	// GRANDINI ⭐
	@FXML
	void chiudiPodio(ActionEvent event) {
		podio.setVisible(false);
	}

	// AHMED
	public void updateHypercars(ArrayList<Hypercar> hypercars) {
		scoreboard.getChildren().clear();

		Integer index = 1;
		ImageView img;
		for (Hypercar car : hypercars) {

			carRow = new Pane();
			carRow.setPrefHeight(48);
			carRow.setPrefWidth(868.0);

			pos = new Label(String.valueOf(index));
			pos.setLayoutY(9);
			pos.setLayoutX(19);
			pos.setFont(new Font("System Bold Italic", 20));
			pos.setPrefWidth(34);
			pos.setTextFill(javafx.scene.paint.Color.valueOf("#fefefe"));

			pilota = new Label();
			pilota.setLayoutY(13);
			pilota.setLayoutX(58);
			pilota.setPrefWidth(190);
			pilota.setFont(new Font("Arial Bold Italic", 16));
			car.piloti.forEach(pil -> {
				if (pil.staGuidando) {
					pilota.setText(pil.nome);
				}
			});
			pilota.setTextFill(javafx.scene.paint.Color.valueOf("#fefefe"));

			macchina = new Label(car.modello);
			macchina.setLayoutY(13);
			macchina.setPrefWidth(205);
			macchina.setLayoutX(262.0);
			macchina.setFont(new Font("Arial", 17));
			macchina.setTextFill(javafx.scene.paint.Color.valueOf("#fefefe"));

			tempoInGiro = new Label(String.valueOf(formattaTempo(car.tempoInGiro)));
			tempoInGiro.setLayoutY(13);
			tempoInGiro.setLayoutX(472);
			tempoInGiro.setPrefWidth(94);
			tempoInGiro.setAlignment(Pos.CENTER);
			tempoInGiro.setFont(new Font("Verdana Pro Cond Italic", 17));
			tempoInGiro.setTextFill(javafx.scene.paint.Color.valueOf("#fefefe"));

			giri = new Label(String.valueOf(car.numeroGiri));
			giri.setLayoutY(13);
			giri.setPrefWidth(94);
			giri.setAlignment(Pos.CENTER);
			giri.setFont(new Font("Verdana Pro Cond Italic", 17));
			giri.setLayoutX(597);
			giri.setTextFill(javafx.scene.paint.Color.valueOf("#fefefe"));

			stato = new Label();
			stato.setLayoutY(13);
			stato.setLayoutX(702);
			stato.setPrefWidth(94);
			stato.setAlignment(Pos.CENTER);
			if (timeMenu.getText().equals("LIVE")) {
				stato.setFont(new Font("Arial", 14));
				stato.setLayoutY(16);
				stato.setTextFill(javafx.scene.paint.Color.valueOf("#fefefe"));
				if (car.ritirata) {
					stato.setText("Ritirata");
				} else if (gara.gestione.verificaAutoInBoxTramiteID(car.id)) {
					stato.setText("In Box");
				} else {
					stato.setText("In Gara");
				}

			} else if (timeMenu.getText().equals("1")) {
				img = new ImageView(new Image(getClass().getResourceAsStream("/images/neutro.png")));
				img.setFitHeight(20);
				img.setFitWidth(20);
				stato.setGraphic(img);
			} else {
				confrontaPosizioneAuto(Integer.valueOf(timeMenu.getText()), stato, car.id);
			}

			Label labelI = new Label("i");
			labelI.setFont(new Font("Bodoni MT Bold Italic", 14));
			labelI.setTextFill(javafx.scene.paint.Color.valueOf("#FFFFFF"));
			pop = new Button("", labelI);
			pop.setCursor(Cursor.HAND);
			pop.setPrefHeight(25);
			pop.setPrefWidth(25);
			pop.setOnMouseClicked(event -> apriPopup(car));
			pop.setLayoutX(816);
			pop.setLayoutY(11);
			pop.setStyle("-fx-background-color:black; -fx-background-radius: 999px; -fx-border-radius: 999px; ");

			line = new Line();
			line.setStartX(-124.39998626708984f);
			line.setStartY(13.000003814697266f);
			line.setEndX(722.800048828125f);
			line.setEndY(13.000003814697266f);
			line.setLayoutX(141);
			line.setLayoutY(35);
			line.setStroke(Paint.valueOf("#797979"));
			line.setStrokeWidth(0.5);

			carRow.getChildren().addAll(pos, macchina, pilota, tempoInGiro, line, pop, stato, giri);
			scoreboard.getChildren().add(carRow);
			index++;
		}
	}

	// GRANDINI
	public void updateMessaggi() {
		for (int i = currentIndexMsg; i < gara.gestione.messaggi.size(); i++) {
			Messaggio messaggio = gara.gestione.messaggi.get(i);
			AnchorPane wrapper = new AnchorPane();
			wrapper.getStyleClass().add("wrapper");
			AnchorPane ricevuti = new AnchorPane();
			ricevuti.getStyleClass().add("ricevuti");
			ricevuti.setStyle("-fx-background-color:  #cfd0d1");
			wrapper.getChildren().add(ricevuti);
			Label messaggioFxml = new Label();
			messaggioFxml.getStyleClass().add("messaggio");
			messaggioFxml.setTextFill(javafx.scene.paint.Color.valueOf("#121212"));
			messaggioFxml.setText(messaggio.messaggio);
			Label orario = new Label();
			orario.getStyleClass().add("orario");
			orario.setText(formattaTempo(messaggio.tempo));
			ricevuti.getChildren().addAll(orario, messaggioFxml);
			chat.getChildren().add(wrapper);
		}

		if (chat.getChildren().size() > 40) {
			Integer extraMessages = (chat.getChildren().size() - 40);
			for (int k = extraMessages; k >= 0; k--) {
				chat.getChildren().remove(k);
			}
		}
		currentIndexMsg = gara.gestione.messaggi.size();
	}

	// AHEMD
	public void apriPopup(Hypercar car) {
		popup.setVisible(true);
		modelloPopup.setText(car.modello);
		idPopup.setText(String.valueOf(car.id));
		pilota1Popup.setText(car.piloti.get(0).nome);
		pilota2Popup.setText(car.piloti.get(1).nome);
		tempoPilota1Popup.setText(formattaTempo(car.piloti.get(0).totTempoInGuida));
		tempoPilota2Popup.setText(formattaTempo(car.piloti.get(1).totTempoInGuida));
		motorePopup.setText(car.motore);
		pitStopPopup.setText(String.valueOf(car.pitStop));
		giriPopup.setText(String.valueOf(car.numeroGiri));
		spazioPercorsoPopup.setText(String.valueOf(car.spazioPercorso / 1000) + " km");

		if (car.giroPiuVeloce != null) {
			migliorTempoPopup.setText(String.valueOf(formattaTempo(car.giroPiuVeloce.tempo)));
		} else {
			migliorTempoPopup.setText("0");
		}

		String modelloImg = "/modelli/" + car.modello.replace(" ", "-") + ".png";
		String scuderiaImg = "/scuderie/" + car.scuderia.replace(" ", "-") + ".png";

		hypercarImmaginePopup.setImage(new Image(getClass().getResourceAsStream(modelloImg)));
		scuderiaImagePopup.setImage(new Image(getClass().getResourceAsStream(scuderiaImg)));

	}

	// AHMED
	public void confrontaPosizioneAuto(int ora, Label stato, int hypercarId) {
		ArrayList<Hypercar> prevHour = gara.registroOre.get(ora - 2).hypercars;
		ArrayList<Hypercar> currentHour = gara.registroOre.get(ora - 1).hypercars;
		int currentPos = Gestione.trovaPosizioneAutoInOraTramiteId(currentHour, hypercarId);
		int prevPos = Gestione.trovaPosizioneAutoInOraTramiteId(prevHour, hypercarId);

		ImageView img;

		if (currentPos < prevPos) {
			img = new ImageView(new Image(getClass().getResourceAsStream("/images/su.png")));
			img.setFitWidth(20);
			img.setFitHeight(17);
			stato.setGraphic(img);
		} else if (currentPos > prevPos) {
			img = new ImageView(new Image(getClass().getResourceAsStream("/images/giu.png")));
			img.setFitWidth(20);
			img.setFitHeight(17);
			stato.setGraphic(img);
		} else {
			img = new ImageView(new Image(getClass().getResourceAsStream("/images/neutro.png")));
			img.setFitHeight(20);
			img.setFitWidth(20);
			stato.setGraphic(img);
		}

	}

	// ADMINS
	@FXML
	public void switchToChat(Event event) {
		if (AutenticazioneController.tema.equals("dark")) {
			AutenticazioneController.tema = "light";
		} else {
			AutenticazioneController.tema = "dark";
		}
		try {
			bar.cancel();
			timer.cancel();
			Image icon = new Image("images\\whatsapp.png");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Autenticazione.fxml"));
			Parent root = loader.load();
			AutenticazioneController autenticazioneController = loader.getController();
			autenticazioneController.gara = this.gara;
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.getIcons().clear();
			stage.getIcons().add(icon);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("WhatsaApp Lite");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// AHMED
	public String formattaTempo(int tempo) {
		return String.format("%02d:%02d:%02d", (int) (tempo / 3600), (int) ((tempo / 60) % 60), (int) (tempo % 60));
	}

}
