package domZad3;

import java.awt.Color;

public abstract class KruznaFigura extends Krug {

	protected Vektor brzina;
	protected Scena scena;
	
	public KruznaFigura(Vektor centar, Color boja, double precnik, Vektor brzina, Scena scena) {
		super(centar, boja, precnik);
		this.brzina = brzina;
		this.scena = scena;
	}

	public synchronized void prosloVreme(double dt) {
		Vektor v = brzina.clone();
		v.pomnozi(dt);
		centar.saberi(v);
		if(this.izaslaIzScene() == true)
			scena.izbaciFiguru(this);
	}

	private synchronized boolean izaslaIzScene() {
		
		double doleF = centar.getY() - precnik / 2;
		double doleS = scena.getHeight();
//		System.out.println(this.getClass().getName() + ":" + doleS + ">" + doleF);
		if(doleS > doleF) {
			return false;
		}
		return true;
	}
		
	public abstract void sudarilaSe(KruznaFigura f);
	
}
