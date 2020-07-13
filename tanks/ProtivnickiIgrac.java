package domZad2;

import java.awt.Color;
import java.awt.Graphics;

public class ProtivnickiIgrac extends Igrac {

	public ProtivnickiIgrac(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		g.setColor(Color.BLUE);
		int sirinaPolja = polje.getWidth();
		int visinaPolja = polje.getHeight();
		
		g.drawLine(	sirinaPolja / 2, 
					0, 
					sirinaPolja / 2, 
					visinaPolja);
		
		g.drawLine(	0, 
					visinaPolja / 2, 
					sirinaPolja, 
					visinaPolja / 2);

	}
}
