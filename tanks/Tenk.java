package domZad2;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable{

//	private boolean radi = false;
	private Thread nit = new Thread(this);
	
	public Tenk(Polje polje) {
		super(polje);
		
	}
	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		g.setColor(Color.BLACK);
		int sirinaPolja = polje.getWidth();
		int visinaPolja = polje.getHeight();
		
		g.drawLine(	0, 
					0, 
					sirinaPolja, 
					visinaPolja);
		
		g.drawLine(	0, 
					visinaPolja, 
					sirinaPolja, 
					0);
	}
	
	public synchronized void pokreniTenk() {
		nit.start();
	}
	public synchronized void zaustaviTenk() {
		nit.interrupt();
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
/*				synchronized (this) {
					while(!radi)
						wait();
				}
*/
				synchronized (polje) {
					int[] tekucaPozicija = polje.pozicija();
					int[] narednaPozicija = naredniPomeraj(tekucaPozicija);
					Polje staroPolje = polje.dohvatiZaPomeraj(0, 0);
					Polje novoPolje = polje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]);					
					this.pomeri(novoPolje);
					staroPolje.repaint();
					novoPolje.repaint();
				}

				Thread.sleep(500);
			}
		} catch (InterruptedException e) {}
	}
	private int[] naredniPomeraj(int[] tekucaPozicija) {

		int i = tekucaPozicija[0];
		int j = tekucaPozicija[1];
		int[] narednaPozicija = new int[2];
		boolean traziSe = true;
		while(true) {
			narednaPozicija[0] = 0;
			narednaPozicija[1] = 0;
			int smer = (int)(Math.random() * 4);
			switch (smer) {
			case 0: // Levo
				if(i == 0)
					break;
				narednaPozicija[0] = -1;
				if(polje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(this) == false) {
					break;
				}
				traziSe = false;
				break;
			case 1: // Dole
				if(j == 16)
					break;
				narednaPozicija[1] = +1;
				if(polje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(this) == false) {
					break;
				}
				traziSe = false;
				break;
			case 2: // Desno
				if(i == 16)
					break;
				narednaPozicija[0] = +1;
				if(polje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(this) == false) {
					break;
				}
				traziSe = false;
				break;
			case 3: // Gore
				if(j == 0)
					break;
				narednaPozicija[1] = -1;
				if(polje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(this) == false) {
					break;
				}
				traziSe = false;
				break;
			}
			if(traziSe == false)
				break;
		}

		
		return narednaPozicija;
	}

	
}
