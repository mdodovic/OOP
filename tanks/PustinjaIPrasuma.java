package domZad2;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class PustinjaIPrasuma extends Polje {

	private Color boja = Color.ORANGE;
	
	public PustinjaIPrasuma(Mreza mreza) {
		super(mreza);
	}

	@Override
	public boolean mozeFigura(Figura f) {
		return f instanceof Igrac;
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
				
	}

}
