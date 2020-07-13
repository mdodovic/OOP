package domZad2;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Zid extends Polje {

	private Color boja = Color.LIGHT_GRAY;
	
	public Zid(Mreza mreza) {
		super(mreza);
	}
	
	@Override
	public boolean mozeFigura(Figura f) {
		return false;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(boja);		
		g.fillRect(0, 0, getWidth(), getHeight());

	}

}
