package domZad3;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Scena extends Canvas implements Runnable{

	private Igra igra;

	private Igrac igrac;
	private List<Balon> baloni = new ArrayList<Balon>();
	private List<Balon> baloniZaIzbacivanje = new ArrayList<Balon>();

	private boolean radi = false;
	private Thread nit;
	//Timer nitTimera;
	
	
	public Scena(Igra igra) {
		this.igra = igra;
		this.setFocusable(true);
		this.requestFocusInWindow();
		dodajOsluskivacaTastature();
	}
	
	private void dodajOsluskivacaTastature() {

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				synchronized (this) {
					if(radi == true) {
						switch(e.getKeyCode()) {
							case KeyEvent.VK_LEFT: igrac.pomeri(-5); break;
							case KeyEvent.VK_RIGHT: igrac.pomeri(5); break;		
						}
					}
					repaint();
				}
			}
		});
		
	}

	private void konfigurisi() {
		igrac = new Igrac(new Vektor(getWidth() / 2., getHeight() * 9. / 10), 30, new Vektor(1, 0), this);		
		baloni.clear();			
		nit = new Thread(this);
		nit.start();
//		nitTimera = new Timer();
//		nitTimera.start();
//		nitTimera.kreni();
	}

	private void generisiBalon() {
		if( Math.random() < 0.1 ) {

			Balon bTmp = new Balon(new Vektor((int)(Math.random() * getWidth()), 0), 20, 
									new Vektor(0, (int)(Math.random() * 150 + 50)), this);
			
			for(Balon bIter : baloni) {
				if(Krug.preklapajuSe(bTmp, bIter) == true) {
					return;
				}
			}	
			dodajFiguru(bTmp);
		}
		
	}

	public synchronized void kreni() {
		if(nit != null)
			nit.interrupt();
//		if(nitTimera != null)
//			nitTimera.resetTajmera();
		konfigurisi();
		repaint();
		radi = true;
		notify();
	}
	
	public synchronized void zaustavi() {
		if(nit != null)
			nit.interrupt();
//		if(nitTimera != null)
//			nitTimera.pauziraj();
		radi = false;
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				synchronized(this) {
					while(!radi) wait();
				}
				Thread.sleep(60);
				azuriraj();
				repaint();
			} catch (InterruptedException e) {
				break;
			}
		}	
	}
	
	private synchronized void azuriraj() {
		synchronized (baloni) {
			generisiBalon();
			for(Balon bIter : baloni) {
				if(Krug.preklapajuSe(bIter, igrac)) {
					bIter.sudarilaSe(igrac);
					igrac.sudarilaSe(bIter);
				}
			}

			
			for(Balon bIter : baloni) {
				bIter.prosloVreme(60./1000);				
			}
			for(Balon bIter : baloniZaIzbacivanje) {
				baloni.remove(bIter);				
			}
			baloniZaIzbacivanje.clear();				
		}
	}

	@Override
	public void paint(Graphics g) {
		if(igrac != null)
			igrac.iscrtaj(this);
		synchronized (baloni) {
			for(Balon bIter : baloni)
				bIter.iscrtaj(this);
		}
	}
	
	public synchronized void izbaciFiguru(KruznaFigura kruznaFigura) {
		if(kruznaFigura instanceof Balon) {
				for(Balon bIter : baloni) {
					if(bIter.equals(kruznaFigura)) {
						baloniZaIzbacivanje.add(bIter);
					}					
				}
		}
	}
	
	public synchronized void dodajFiguru(KruznaFigura figura) {
		if(figura instanceof Balon)
			baloni.add((Balon)figura);
	}

}

/*class Timer extends Thread {
	
	private int s, m;
	private boolean radi;
	//private Label label;
	
	//public Timer(Label label) {
	//	this.label = label;
	//}
	
	@Override
	public void run() {
		try {
			while(!isInterrupted()) {
				synchronized (this) {
					while(!radi)
						wait();
				}
				
	//			label.setText(toString());
	//			label.revalidate();
				ispis();
				sleep(1000);
				s++;
				if(s % 60 == 0) {
					m++;
					s = 0;
				}
			}
		} catch (InterruptedException e) {
		}
	}
	
	public synchronized void kreni() {
		radi = true;
		notify();
	}
	
	public synchronized void pauziraj() {
		radi = false;
	}
	
	public synchronized void resetTajmera() {
		m = s = 0;
	}
	
	@Override
	public synchronized String toString() {
		return String.format("%02d:%02d", m, s);
	}
	
	public synchronized void ispis() {
		System.out.println(m + ":" + s);
	}
}
*/