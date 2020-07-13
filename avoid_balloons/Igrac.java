package domZad3;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends KruznaFigura {

	public Igrac(Vektor centar, double precnik, Vektor brzina, Scena scena) {
		super(centar, Color.GREEN, precnik, brzina, scena);
	}

	
	@Override
	public void sudarilaSe(KruznaFigura f) {
		if(f instanceof Balon)
			scena.zaustavi();
	}
	
	public void pomeri(double dx) {
		Vektor noviCentar = centar.clone();
		noviCentar.saberi(new Vektor(dx, 0));
		
		double levoS = 0;
		double desnoS = scena.getWidth();
		
		double levoF = noviCentar.getX() - precnik / 2;
		double desnoF = noviCentar.getX() + precnik / 2;
//		System.out.println(levoS + "<=" + levoF + "&&" + desnoF + "<=" + desnoS);
		
		if(levoS <= levoF && desnoF <= desnoS)
			centar = noviCentar;
			
	}
	
	public synchronized void prosloVreme(double dt) {}
	
	public void iscrtaj(Scena s) {
		super.iscrtaj(s);
		Graphics g = s.getGraphics();
		g.setColor(Color.BLUE);
		g.fillOval(	(int) (centar.getX() - precnik / 2 + precnik / 4), 
					(int) (centar.getY() - precnik / 2 + precnik / 4), 
					(int) (precnik / 2), (int) (precnik / 2));
	}

}
