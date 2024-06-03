package application;

import java.util.*;

public class Hypercar {
	int VELOCITA_MINIMA = 40, VELOCITA_MASSIMA = 103;

	int id, tempoInGiro, numeroGiri, spazioPercorso, giroFermataBox, pitStop, lunghezzaGiro;
	String scuderia, motore, modello;
	Boolean guasta = false, ritirata = false, inGiroFinale = false;

	GiroPiuVeloce giroPiuVeloce;
	ArrayList<Driver> piloti = new ArrayList<Driver>();

	// BISOGNI
	public Hypercar(int id, String scuderia, String motore, String modello, int lunghezzaGiro,
			ArrayList<Driver> piloti) {

		this.lunghezzaGiro = lunghezzaGiro;
		this.id = id;
		this.scuderia = scuderia;
		this.motore = motore;
		this.modello = modello;

		for (Driver pilota : piloti) {
			this.piloti.add(pilota);
		}
	}

	// BISOGNI
	public void corri() {
		int velocita = (int) (Math.random() * (VELOCITA_MASSIMA - VELOCITA_MINIMA) + VELOCITA_MINIMA);
		spazioPercorso += velocita;
		aggiornaDati();

		if ((int) (Math.random() * 10000) == 2495) {
			guasta = true;
			if ((int) (Math.random() * 5003) == 33)
				ritirata = true;
		}

	}

	// BISOGNI
	public void aggiornaDati() {
		for (Driver pilota : this.piloti) {
			if (pilota.staGuidando) {
				pilota.totTempoInGuida += 1;
				pilota.tempoInGuida += 1;
			}
		}

		this.tempoInGiro += 1;

		if ((this.spazioPercorso / this.lunghezzaGiro) > numeroGiri) {
			controllaGiroPiuVeloce();
			tempoInGiro = 0;
			numeroGiri++;
		}

	}

//	PRIO
	public Boolean controllaCambioDriver() {

		for (int i = 0; i < this.piloti.size(); i++) {
			if (this.piloti.get(i).tempoInGuida >= (3600 * 4)) {
				this.piloti.get(i).staGuidando = false;
				this.piloti.get(i).tempoInGuida = 0;

				if (i == 0) {
					this.piloti.get(1).staGuidando = true;
				} else {
					this.piloti.get(0).staGuidando = true;
				}
				return true;
			}
		}

		return false;
	}

	// PRIO
	public void controllaGiroPiuVeloce() {
		if (giroPiuVeloce == null || this.tempoInGiro < this.giroPiuVeloce.tempo) {
			Driver pilotaInGuida = null;
			for (Driver pilota : this.piloti) {
				if (pilota.staGuidando)
					pilotaInGuida = pilota;
			}
			this.giroPiuVeloce = new GiroPiuVeloce(tempoInGiro, pilotaInGuida);
		}
	}

}