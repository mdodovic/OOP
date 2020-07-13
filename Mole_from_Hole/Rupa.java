package domZad1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Rupa extends Canvas implements Runnable {

	public void g() { basta.f(); }
	public Basta getBasta() { return basta; }

	private Basta basta;
	private Zivotinja zivotinja;
	
	private int ukupanBrojKoraka = 10;
	private int trenutniBrojKoraka = 0;
	private int x, y;
	
	private Thread nitRupa;

	private boolean udarena = false;
	
	public Rupa(Basta basta) {
		super();
		this.basta = basta;
		dodajMisa();
	}

	private void dodajMisa() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xE = e.getX();
				int yE = e.getY();
				if(xE < x && yE < y) {
					zgaziZivotinju();
				}
			}
		});
	}
	
	public Zivotinja getZivotinja() { return zivotinja; }
	public void setZivotinja(Zivotinja zivotinja) {	this.zivotinja = zivotinja; }

	public int getUkupanBrojKoraka() { return ukupanBrojKoraka; }
	public void setBrojKoraka(int brojKoraka) { this.ukupanBrojKoraka = brojKoraka; }
	public synchronized int getTrenutniBrojKoraka() { return trenutniBrojKoraka; }
	
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(160,82,45));
		
		x = getWidth() - getWidth() / 4;
		y = getHeight() - getHeight() / 4;
		g.fillRect(0, 0, x, y);

		if(zivotinja != null) {
			zivotinja.iscrtaj();
		}
	}
	
	public synchronized void stvori() {
		nitRupa = new Thread(this);
	}
	public synchronized void pokreni() {
		udarena = false;
		nitRupa.start();
	}
	public synchronized void zaustavi() {
		nitRupa.interrupt();
		zivotinja = null;
		basta.slobodnaRupa(this);
		repaint(); //Izaziva da se nestane 
	}
	public synchronized boolean pokrenuta() {
		return nitRupa.isAlive();
	}

	
	@Override
	public void run() {
		try {
			for(trenutniBrojKoraka = 0; trenutniBrojKoraka < ukupanBrojKoraka; trenutniBrojKoraka++) {
				Thread.sleep(100);
				repaint();
			}
			Thread.sleep(2000);			
			synchronized (this) {
				if (udarena == false && zivotinja != null)
					zivotinja.efekatPobegle();
			}
			zaustavi();
		}catch(InterruptedException ie) {
		}
	}
	
	public synchronized void zgaziZivotinju() {
		if(zivotinja == null)
			return;
		udarena = true;
		zivotinja.efekatUdarene();
	}
	
	
}
