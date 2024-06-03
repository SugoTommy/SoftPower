// MARTINO
package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestoreRubrica {
	ArrayList<String> contatti = new ArrayList<String>();
	String usernameMittente;
	static File directory = new File("src//comunicazione");
	static String[] list = directory.list();

	GestoreRubrica(String usernameMittente) {
		this.usernameMittente = usernameMittente;
		caricaContatti();
	}

	public void caricaContatti() {
		if (usernameMittente.equalsIgnoreCase("amministrazione")) {
			for (int i = 0; i < list.length; i++) {
				if (!list[i].equalsIgnoreCase("amministrazione")&& !list[i].startsWith(".")) {
					contatti.add(list[i]);
				}
			}
		} else {
			contatti.add("amministrazione");
		}
	}

	public static void resettaTutteLeChat() {
		String path = "src//comunicazione//", path1 = "//messaggi.txt";
		FileWriter fr;
		for (int i = 0; i < list.length; i++) {

			if (!list[i].equalsIgnoreCase("amministrazione") && !list[i].startsWith(".")) {
				try {
					fr = new FileWriter(new File(path + list[i] + path1));
					fr.write("");
					fr.flush();
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
