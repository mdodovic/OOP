package domZad3;

import java.awt.Color;
import java.awt.Graphics;

public class Krug {
	protected Vektor centar;
	protected Color boja;
	protected double precnik;

	public Krug(Vektor centar, Color boja, double precnik) {
		this.centar = centar;
		this.boja = boja;
		this.precnik = precnik;
	}
	
	public static boolean preklapajuSe(Krug k1, Krug k2) {
				
		double c1c2 = Math.sqrt((k1.centar.getX() - k2.centar.getX()) * (k1.centar.getX() - k2.centar.getX()) +
								(k1.centar.getY() - k2.centar.getY()) * (k1.centar.getY() - k2.centar.getY()));
		if(c1c2 > k1.precnik / 2 + k2.precnik / 2)
			return false;
		return true;
	}
	
	public void iscrtaj(Scena s) {
		Graphics g = s.getGraphics();
		g.setColor(boja);
		g.fillOval(	(int) (centar.getX() - precnik / 2), 
					(int) (centar.getY() - precnik / 2), 
					(int) precnik, (int) precnik);
	}

}
