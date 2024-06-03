// BRIGANTE
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Autenticazione {
	BufferedReader lettore;

	public boolean verificaPassword(String username, String password) {
		try {
			String path = "src//comunicazione//" + username;
			if (verificaEsistenza(path) == false) {
				throw new NonAutorizzatoException();
			}
			lettore = new BufferedReader(new FileReader(new File("src//comunicazione//" + username + "//password.txt")));
			String password1 = lettore.readLine();
			lettore.close();
			if (password.equals(password1)) {
				return true;
			} else {
				throw new NonAutorizzatoException();
			}
		} catch (NonAutorizzatoException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verificaEsistenza(String path) {
		return (new File(path).exists());
	}
}
