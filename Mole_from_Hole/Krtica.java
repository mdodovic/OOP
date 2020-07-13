package domZad1;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja {

	public Krtica(Rupa rupa) {
		super(rupa);
	}

	@Override
	public void iscrtaj() {
		Graphics g = rupa.getGraphics();
		g.setColor(Color.DARK_GRAY);
		int sirinaRupe = rupa.getWidth() - rupa.getWidth() / 4;
		int visinaRupe = rupa.getHeight() - rupa.getHeight() / 4;
		
		g.fillOval(	sirinaRupe / 2 - sirinaRupe / 2 * dimenzija() / 100, 
					visinaRupe / 2 - visinaRupe / 2 * dimenzija() / 100, 
					sirinaRupe * dimenzija() / 100, visinaRupe * dimenzija() / 100);
	}

	@Override
	public void efekatUdarene() {
		rupa.g(); // poeni za udarac zivotinja
		rupa.zaustavi();
	}

	@Override
	public void efekatPobegle() {
		rupa.getBasta().smanjiKolicinuPovrca();
	}

}
