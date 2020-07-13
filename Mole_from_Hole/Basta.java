package domZad1;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;



@SuppressWarnings("serial")
public class Basta extends Panel implements Runnable {

	
	public int brojPoena = 0;
	public void f() { ++brojPoena; }
	//Timer tajmer;


	private Rupa[][] rupe;
	private boolean[][] slobodneRupe;
	private int kolicinaPovrca;
	private Label lKolicinaPovrca;
	
	private int dt;
	private int brojKoraka;
	
	private int brojVrsta, brojKolona;

	private Thread nit;
	private boolean radi = false;		
	
	public Basta(int brojVrsta, int brojKolona) {
		this.brojVrsta = brojVrsta;
		this.brojKolona = brojKolona;		
		rupe = new Rupa[brojVrsta][brojKolona];		
		slobodneRupe = new boolean[brojVrsta][brojKolona];			
		//tajmer = new Timer();
		//tajmer.pauziraj();
		//tajmer.start();
		setLayout(new GridLayout(0, 4));
		setBackground(Color.GREEN);
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				slobodneRupe[i][j] = true;
				rupe[i][j] = new Rupa(this);
				add(rupe[i][j]);	
			}
		}
		konfigurisi();
	}


	private void konfigurisi() {
		//tajmer.resetTajmera();
		kolicinaPovrca = 100;
		brojPoena = 0;		// Broj poena
		azurirajLabele(); 
		nit = new Thread(this);
		nit.start();
	}
	
	private void generisiKrticu() {
		while(true) {
			int i = (int) (Math.random() * brojVrsta);
			int j = (int) (Math.random() * brojKolona);
			if(rupe[i][j].getZivotinja() == null && slobodneRupe[i][j] == true) {
				rupe[i][j].setZivotinja(new Krtica(rupe[i][j]));
				rupe[i][j].setBrojKoraka(brojKoraka);
				rupe[i][j].stvori(); // stvatanje niti zivotinje, tj niti rupe
				rupe[i][j].pokreni(); // nit.start()
				break;
			}
		}
	}
	
	public int getBrojKoraka() { return brojKoraka; }
	public void setBrojKoraka(int brojKoraka) {	this.brojKoraka = brojKoraka; }
	public void setDt(int dt) { this.dt = dt; }

	public void postaviLabelu(Label l) {
		this.lKolicinaPovrca = l;
	}

	private void azurirajLabele() {
		if(lKolicinaPovrca == null) return;
		lKolicinaPovrca.setText("Povrce: " + kolicinaPovrca + " / p: " + brojPoena);
	}
	
	public synchronized void smanjiKolicinuPovrca() {
		this.kolicinaPovrca--;
	}

	public synchronized void pokreni() {
		if(nit != null)
			nit.interrupt();
		konfigurisi();
		repaint();
		radi = true;
		//tajmer.kreni();
		notify();
	}
	public synchronized void zaustavi() {		
		//tajmer.pauziraj();
		nit.interrupt();
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				if(rupe[i][j].getZivotinja() != null) {
					rupe[i][j].zaustavi();
				}
			}
		}
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				synchronized (this) {
					while(!radi) {
						wait();
					}
				}
				Thread.sleep(dt);
				dt -= dt / 100;
				generisiKrticu();
				repaint();
				azurirajLabele();
				synchronized (this) {
					if(kolicinaPovrca == 0)
						zaustavi();
				}
			} catch (InterruptedException e) {
				break;
			}
			
		}

	}

	public synchronized void slobodnaRupa(Rupa rupa) {
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				if(rupe[i][j] == rupa) {
					slobodneRupe[i][j] = true;
					break;
				}
			}
		}
	}

}

/*class Timer extends Thread {

	private int s, m;
	private boolean radi;
//	private Label label;
	
//	public Timer(Label label) {
//		this.label = label;
//	}
	
	@Override
	public void run() {
		try {
			while(!isInterrupted()) {
				synchronized (this) {
					while(!radi)
						wait();
				}
				
//				label.setText(toString());
//				label.revalidate();
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
}*/

