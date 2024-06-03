// FEO
package application;


import java.util.*;

public class RegistroOra {
	
	int ora;
	ArrayList<Hypercar> hypercars = new ArrayList<Hypercar>();

	public RegistroOra(int ora) {
		this.ora = ora;
	}
	
	public void rigistraAuto(ArrayList<Hypercar> cars) {
		cars.forEach(car->{
			ArrayList<Driver> _piloti = new ArrayList<Driver>();
			car.piloti.forEach(pil->{
				Driver driver = new Driver(pil.nome,pil.staGuidando);
				driver.tempoInGuida = pil.tempoInGuida;
				driver.totTempoInGuida = pil.totTempoInGuida;
				_piloti.add(driver);
			});
			Hypercar hypercar = new Hypercar(car.id, car.scuderia,
					car.motore, car.modello,
					car.lunghezzaGiro, _piloti);
			hypercar.giroFermataBox = car.giroFermataBox;
			hypercar.giroPiuVeloce = car.giroPiuVeloce;
			hypercar.guasta = car.guasta;
			hypercar.numeroGiri = car.numeroGiri;
			hypercar.pitStop = car.pitStop;
			hypercar.ritirata = car.ritirata;
			hypercar.spazioPercorso = car.spazioPercorso;
			hypercar.tempoInGiro = car.tempoInGiro;
			
		
			
			hypercars.add(hypercar);
         });
	}

}
