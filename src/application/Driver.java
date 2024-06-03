package application;

// TURRIN
public class Driver extends Persona {
	int tempoInGuida = 0, totTempoInGuida = 0;
	boolean staGuidando;
	
	public Driver(String nome, boolean staGuidando) {
		super(nome);
		this.staGuidando = staGuidando;
	}

}
