package application;

import java.util.ArrayList;

public class Gestione {

	int TEMPO_MINIMO_BOX = 90;

	ArrayList<Hypercar> hypercars = new ArrayList<Hypercar>();
	ArrayList<Messaggio> messaggi = new ArrayList<Messaggio>();
	ArrayList<AutoInBox> autoInBox = new ArrayList<AutoInBox>();

	// ADMINS
	public static int trovaPosizioneAutoInOraTramiteId(ArrayList<Hypercar> hypercars, int id) {
		for (int i = 0; i<hypercars.size(); i++) {
			if (hypercars.get(i).id == id) return i;
		}
		return 0;
	}
	
	// TURRIN
	public Hypercar trovaAutoTramiteID(int id) {
		for (int i = 0; i < hypercars.size(); i++) {
			if (hypercars.get(i).id == id) {
				return hypercars.get(i);
			}
			
		}
		return null;
	}

	// TURRIN
	public ArrayList<Hypercar> trovaAutoRitirate() {
		ArrayList<Hypercar> ritirate = new ArrayList<Hypercar>();
		for (int i = 0; i < hypercars.size(); i++) {
			if (hypercars.get(i).ritirata == true) {
				ritirate.add(hypercars.get(i));
			}
		}

		return ritirate;
	}

//	HU
	public Boolean verificaAutoInBoxTramiteID(int id) {
		for (int i = 0; i < autoInBox.size(); i++) {
			if (autoInBox.get(i).id == id) {
				return true;
			}
		}
		return false;
	}

	// HU
	public void aggiornaAutoInBox(int tempoCorrente) {
		for (int i = 0; i < autoInBox.size(); i++) {
			autoInBox.get(i).tempo++;
			if (autoInBox.get(i).tempo >= (int) (Math.random() * 40 + TEMPO_MINIMO_BOX)) {
				messaggi.add(new Messaggio("Auto " + trovaAutoTramiteID(autoInBox.get(i).id).modello + " uscita dal Box", tempoCorrente));
				trovaAutoTramiteID(autoInBox.get(i).id).guasta = false;
				autoInBox.remove(i);
			}
		}

	}

	// HU
	public void ordinaClassifica() {
		Hypercar hyperVar = null;
		for (int i = 0; i < hypercars.size(); i++) {
			for (int g = i + 1; g < hypercars.size(); g++) {
				if (hypercars.get(i).spazioPercorso < hypercars.get(g).spazioPercorso) {
					hyperVar = hypercars.get(i);
					hypercars.set(i, hypercars.get(g));
					hypercars.set(g, hyperVar);
				}
			}
		}
	}

}
