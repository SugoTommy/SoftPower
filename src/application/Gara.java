
package application;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Gara {

	int DURATA_GARA = 86400, LUNGHEZZA_GIRO = 13626, FATTORE_RIDUZIONE_TEMPO_IN_MILLISECONDI = 1;

	int tempoCorrente = 0, oraCorrente = 0;
	boolean fine, giroDecisivo;
	boolean iniziata;

	Gestione gestione = new Gestione();
	ArrayList<RegistroOra> registroOre = new ArrayList<RegistroOra>();

	Timer timer = new Timer();

	public Gara() {
		init();
	}

	// HU
	public void init() {

		String motori[] = { "V8", "V6", "Inline-4", "Inline-6", "Boxer", "W12", "W16", "Rotary", "V10", "V12",
				"Straight-6", "Flat-6", "Inline-5", "V5", "V4", "Flat-4", "Wankel", "Electric", "Hybrid", "Diesel" };

		String[] scuderie = { "Toyota Gazoo Racing", "Porsche Motorsport", "Audi Sport Team Joest", "Ferrari AF Corse",
				"Corvette Racing", "Ford Chip Ganassi Racing", "Aston Martin Racing", "BMW Team MTEK", "Peugeot Sport",
				"Mazda Team Joest", "Alpine Elf Team", "Cadillac Racing", "Glickenhaus Racing", "Nissan Motorsports",
				"Bentley Team M-Sport", "Jaguar Racing", "Mercedes-AMG Team", "Rebellion Racing", "Pescarolo Sport",
				"Lotus Racing" };

		String[][] modelli = { { "Toyota GR010 Hybrid", "Toyota TS050 Hybrid" },
				{ "Porsche 911 RSR", "Porsche 919 Hybrid" }, { "Audi R18 e-tron quattro", "Audi R10 TDI" },
				{ "Ferrari 458 Italia GT2", "Ferrari 488 GTE" }, { "Corvette C8.R", "Corvette C7.R" },
				{ "Ford GT", "Ford GT40" }, { "Aston Martin Vantage GTE", "Aston Martin DBR9" },
				{ "BMW M8 GTE", "BMW V12 LMR" }, { "Peugeot 908 HDi FAP", "Peugeot 905" },
				{ "Mazda 787B", "Mazda 757" }, { "Alpine A470", "Alpine A442" },
				{ "Cadillac DPi-V.R", "Cadillac Northstar LMP" }, { "Glickenhaus SCG 007", "Glickenhaus SCG 003C" },
				{ "Nissan GT-R LM Nismo", "Nissan R90CK" }, { "Bentley Speed 8", "Bentley EXP Speed 8" },
				{ "Jaguar XJR-9", "Jaguar D-Type" }, { "Mercedes-Benz CLR", "Mercedes-Benz C11" },
				{ "Rebellion R13", "Rebellion R-One" }, { "Pescarolo C60", "Pescarolo 01" },
				{ "Lotus Elise GT1", "Lotus Esprit GT1" } };

		String[][][] piloti = { { { "Mike Conway", "Kamui Kobayashi" }, { "Sébastien Buemi", "Kazuki Nakajima" } },
				{ { "Richard Lietz", "Gianmaria Bruni" }, { "Kevin Estre", "Neel Jani" } },
				{ { "Allan McNish", "Tom Kristensen" }, { "Benoît Tréluyer", "Marcel Fässler" } },
				{ { "Giancarlo Fisichella", "Toni Vilander" }, { "Gilles Villeneuve", "Alessandro Pier Guidi" } },
				{ { "Antonio García", "Jordan Taylor" }, { "Tommy Milner", "Oliver Gavin" } },
				{ { "Joey Hand", "Dirk Müller" }, { "Ryan Briscoe", "Richard Westbrook" } },
				{ { "Darren Turner", "Stefan Mücke" }, { "Alex Lynn", "Maxime Martin" } },
				{ { "Jesse Krohn", "John Edwards" }, { "Augusto Farfus", "Martin Tomczyk" } },
				{ { "Stéphane Sarrazin", "Pedro Lamy" }, { "Yannick Dalmas", "Derek Warwick" } },
				{ { "Johnny Herbert", "Volker Weidler" }, { "David Kennedy", "Pierre Dieudonné" } },
				{ { "Nicolas Lapierre", "André Negrão" }, { "Thomas Laurent", "Pierre Thiriet" } },
				{ { "João Barbosa", "Filipe Albuquerque" }, { "Jordan Taylor", "Renger van der Zande" } },
				{ { "Romain Dumas", "Ryan Briscoe" }, { "Richard Westbrook", "Franck Mailleux" } },
				{ { "Marc Gené", "Lucas Ordoñez" }, { "Michael Krumm", "Satoshi Motoyama" } },
				{ { "Tom Kristensen", "Guy Smith" }, { "Rinaldo Capello", "Mark Blundell" } },
				{ { "Jan Lammers", "Andy Wallace" }, { "Davy Jones", "Johnny Dumfries" } },
				{ { "Bernd Schneider", "Klaus Ludwig" }, { "Jean-Marc Gounon", "Mark Webber" } },
				{ { "Bruno Senna", "Nicolas Prost" }, { "Neel Jani", "André Lotterer" } },
				{ { "Henri Pescarolo", "Franck Montagny" }, { "Romain Dumas", "Julien Jousse" } },
				{ { "Johnny Herbert", "Derek Bell" }, { "Martin Donnelly", "Mark Blundell" } } };

		int id = 0;

		ArrayList<Driver> _piloti = new ArrayList<Driver>();

		for (int scuderiaIndice = 0; scuderiaIndice < scuderie.length; scuderiaIndice++) {
			for (int modelloIndice = 0; modelloIndice < 2; modelloIndice++) {
				id++;

				// aggiungere piloti
				for (int pilotaIndice = 0; pilotaIndice < 2; pilotaIndice++) {
					_piloti.add(new Driver(piloti[scuderiaIndice][modelloIndice][pilotaIndice],
							pilotaIndice == 0 ? true : false));
				}

				Hypercar hypercar = new Hypercar(id, scuderie[scuderiaIndice],
						motori[(int) (Math.random() * motori.length)], modelli[scuderiaIndice][modelloIndice],
						LUNGHEZZA_GIRO, _piloti);
				gestione.hypercars.add(hypercar);
				_piloti.clear();

			}
		}
	}

	// FEO
	public void inizia() {
		iniziata = true;

		timer.scheduleAtFixedRate(new TimerTask() {
			Hypercar hypercar = null;
			boolean inBox = false;
			String messaggio = "";

			@Override
			public void run() {
				// TODO Auto-generated method stub
				tempoCorrente++;
				for (int i = 0; i < gestione.hypercars.size(); i++) {
					hypercar = gestione.hypercars.get(i);

					if (((!gestione.verificaAutoInBoxTramiteID(hypercar.id) && !giroDecisivo) || (giroDecisivo
							&& hypercar.inGiroFinale && !gestione.verificaAutoInBoxTramiteID(hypercar.id)))
							&& !hypercar.ritirata) {

						hypercar.corri();

						if (hypercar.ritirata)
							gestione.messaggi
									.add(new Messaggio("Auto " + hypercar.modello + " si è ritirata", tempoCorrente));

						if (hypercar.guasta) {
							messaggio = "Auto " + hypercar.modello + " si è guastata ed entra in Box";
							inBox = true;
						}

						if (hypercar.controllaCambioDriver()) {
							messaggio = "Auto " + hypercar.modello + " cambia pilota ed entra in Box";
							inBox = true;

						}

						if (hypercar.numeroGiri >= 40 && hypercar.numeroGiri - hypercar.giroFermataBox >= 40) {
							messaggio = "Auto " + hypercar.modello + " ha fatto 40 giri senza fermarsi, entra in box";
							inBox = true;
						}

						if (inBox) {
							gestione.messaggi.add(new Messaggio(messaggio, tempoCorrente));
							gestione.autoInBox.add(new AutoInBox(hypercar.id));
							hypercar.pitStop++;
							hypercar.giroFermataBox = hypercar.numeroGiri;
							inBox = false;
						}

					} else if (gestione.verificaAutoInBoxTramiteID(hypercar.id)) {
						gestione.aggiornaAutoInBox(tempoCorrente);
					}

				}

				gestione.ordinaClassifica();

				if ((tempoCorrente / 3600) > oraCorrente) {
					oraCorrente = tempoCorrente / 3600;
					registraDatiOraCorrente();
					controlloFineGara();
				}

				if (giroDecisivo && gestione.hypercars.get(0).numeroGiri > gestione.hypercars.get(1).numeroGiri)
					controlloFineGara();
			}
		}, 0, FATTORE_RIDUZIONE_TEMPO_IN_MILLISECONDI);

	}

	// FEO
	public void registraDatiOraCorrente() {
		RegistroOra registroOra = new RegistroOra(this.oraCorrente);
		registroOra.rigistraAuto(gestione.hypercars);
		this.registroOre.add(registroOra);
	}
	
	// FEO
	public void controlloFineGara() {
		if (tempoCorrente == DURATA_GARA || giroDecisivo) {
			if (gestione.hypercars.get(0).numeroGiri == gestione.hypercars.get(1).numeroGiri) {
				giroFinale();
			} else {
				gestione.messaggi.add(new Messaggio(
						"Auto " + gestione.hypercars.get(0).modello + " ha vinto le mans!!!", tempoCorrente));
				this.fine = true;
				timer.cancel();
			}
		}
	}

	// FEO
	public void giroFinale() {
		int numeroGiriPiuAlto = gestione.hypercars.get(0).numeroGiri;
		gestione.messaggi.add(new Messaggio("Giro Finale", tempoCorrente));
		for (Hypercar hypercar : gestione.hypercars) {
			if (hypercar.numeroGiri == numeroGiriPiuAlto) {
				gestione.messaggi.add(new Messaggio("Auto " + hypercar.modello + " è nel giro finale", tempoCorrente));
				hypercar.inGiroFinale = true;
			}
		}

		giroDecisivo = true;
	}
}