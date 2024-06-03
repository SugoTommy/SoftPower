// MARTINO
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;

public class Chat {
	String usernameMittente, usernameDestinatario;
	Scanner lettore;
	BufferedWriter scrittore;
	BufferedReader br;
	String path = "";
	ArrayList<MessaggioChat> messaggi = new ArrayList<MessaggioChat>();

	Chat(String usernameMittente, String usernameDestinatario) {
		this.usernameMittente = usernameMittente;
		this.usernameDestinatario = usernameDestinatario;
		if (usernameMittente.equalsIgnoreCase("amministrazione")) {
			path = "src//comunicazione//" + usernameDestinatario + "//messaggi.txt";
		} else {
			path = "src//comunicazione//" + usernameMittente + "//messaggi.txt";
		}
		try {
			br = new BufferedReader(new FileReader(new File(path)));
			lettore = new Scanner(br);
			scrittore = new BufferedWriter(new FileWriter(new File(path), true));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		caricaMessaggi();
	}

	public void caricaMessaggi() {
		try {
			String firstLine = br.readLine();
			if (firstLine != null) {
				messaggi.add(formattaRigaPerMessaggi(firstLine));
			}
			while (lettore.hasNextLine()) {
				messaggi.add(formattaRigaPerMessaggi(lettore.nextLine()));
			}

			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void aggiungiMessaggio(String messaggio) {
		String tempo = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		MessaggioChat messaggio1 = new MessaggioChat(usernameMittente, tempo, messaggio);
		try {
			riapriFlusso();
			scrittore.append(formattaMessaggioPerFile(messaggio1));
			scrittore.flush();
			scrittore.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		messaggi.add(messaggio1);
	}

	public String formattaMessaggioPerFile(MessaggioChat messaggio) {
		String a = messaggio.proprietario + " - " + messaggio.tempo + " - " + messaggio.testo + "\n";
		return a;
	}

	public void riapriFlusso() {
		try {
			scrittore = new BufferedWriter(new FileWriter(new File(path), true));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public MessaggioChat formattaRigaPerMessaggi(String messaggio) {
		String[] contenuto = messaggio.split(" - ");
		MessaggioChat a = new MessaggioChat(contenuto[0], contenuto[1], contenuto[2]);
		return a;
	}

}
