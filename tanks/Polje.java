package domZad2;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public abstract class Polje extends Canvas {
	
	protected Mreza mreza;
	public Polje(Mreza mreza) {
		this.mreza = mreza;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mreza.izmeniPolje(Polje.this);
			}
		});

		
	}
		

	public Mreza getMreza() { return mreza; }
	
	public int[] pozicija() { 
		for(int i = 0; i < mreza.getPolja().length; i++)
			for(int j = 0; j < mreza.getPolja().length; j++)
				if(this == mreza.getPolja()[i][j])
					return new int[] {i, j};
		return null;
	}

	
	public Polje dohvatiZaPomeraj(int dI, int dJ) {
		int[] ij = pozicija();
		return mreza.getPolja()[ij[0] + dI][ij[1] + dJ];
	}
	
	public abstract boolean mozeFigura(Figura f);
	public abstract void paint(Graphics g);
	
}
