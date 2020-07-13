package domZad2;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {
	
	public Novcic(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		g.setColor(Color.YELLOW);
		int sirinaPolja = polje.getWidth();
		int visinaPolja = polje.getHeight();
		
		g.fillOval(sirinaPolja / 4, visinaPolja / 4, sirinaPolja / 2, visinaPolja / 2);
					
	}

}
