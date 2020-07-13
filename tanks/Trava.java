package domZad2;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Trava extends Polje {

	private Color boja = Color.GREEN;
	
	public Trava(Mreza mreza) {
		super(mreza);
	}

	@Override
	public boolean mozeFigura(Figura f) {
		return true;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(boja);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		synchronized (mreza.getListaIgraca()) {				
			for(Igrac iIter: mreza.getListaIgraca())
				iIter.iscrtaj();
		}
		synchronized (mreza.getListaNovcica()) {				
			for(Novcic nIter: mreza.getListaNovcica())
				nIter.iscrtaj();		
		}
		
		synchronized (mreza.getListaTenkova()) {				
			for(Tenk tIter: mreza.getListaTenkova())
			tIter.iscrtaj();
		}
		
	}

}
