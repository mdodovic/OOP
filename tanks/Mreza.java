package domZad2;

import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

import domZad2.Igra.rezim;


@SuppressWarnings("serial")
public class Mreza extends Panel implements Runnable {
	
	private Polje[][] polja;
	private int n;
	private Igra igra;
	
	private List<Igrac> listaIgraca = new ArrayList<Igrac>();
	private List<Novcic> listaNovcica = new ArrayList<Novcic>();
	private List<Tenk> listaTenkova = new ArrayList<Tenk>();

	private int brojNovcica = 14;
	private int brojTenkova;
	private int sakupljeniNovcici;
	
	Thread nitMreza;
	//Timer tajmerMreza;
	private boolean radi = true;

	public Mreza(int n, Igra igra) {
		this.n = n;
		this.igra = igra;
		polja = new Polje[n][n];
		setLayout(new GridLayout(n, n));
		popuniMatricuPolja();
		dodajPolja();		
	}

	void izmeniPolje(Polje polje) {
		if(igra.rezimIgre == rezim.IZMENA) {
	
			int i = polje.pozicija()[0];
			int j = polje.pozicija()[1];
			int ind = i * n + j;
			remove(ind);

			if(igra.trava.getState() == true)
				polja[i][j] = new Trava(this);
			else {
				if(igra.zid.getState() == true)
					polja[i][j] = new Zid(this);
				else
					polja[i][j] = new PustinjaIPrasuma(this);
			}
						
			add(polja[i][j], ind);
			revalidate();
			repaint();
		}
	}

	public void konfigurisi() {
		ocistiListe();
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				polja[i][j].repaint();
		azurirajLabele();
		dodajIgraca();
		dodajNovcice();
		dodajTenkove();
		nitMreza = new Thread(this);
		//if(tajmerMreza != null)
		//	tajmerMreza.interrupt();
		//tajmerMreza = new Timer();
		//tajmerMreza.start();
	}
	
	public void ocistiListe() {
		listaIgraca.clear();
		listaNovcica.clear();
		sakupljeniNovcici = 0;
		listaTenkova.clear();

	}
	
	public void postaviBrojNovcica() {
		brojNovcica = Integer.parseInt(igra.brojNovcica.getText());		
		brojTenkova = brojNovcica / 3;

	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				synchronized(this) {
					while(!radi) wait();
				}
				iscrtajSve();
				azuriraj();
				Thread.sleep(40);

			} catch (InterruptedException e) {
				break;
			}
		}

	}
	
	private void iscrtajSve() {
		synchronized (listaIgraca) {
			for(Igrac iIter: listaIgraca)
				iIter.getPolje().repaint();	
		}
// Zbog brzine ovo se ne iscrtava
//		for(Novcic nIter: listaNovcica)
//			nIter.getPolje().repaint();	
		
		repaint();
	}

	private synchronized void azuriraj() {
		
		synchronized (listaIgraca) {				
			for(Tenk tIter: listaTenkova) {
				Igrac iTmp = null;
				for(Igrac iIter: listaIgraca) {					
					if(tIter.equals(iIter)) {
						if(!(iIter instanceof ProtivnickiIgrac))
							zaustaviMrezu();
						iTmp = iIter;
						break;
					}
				}
				if(iTmp != null)
					listaIgraca.remove(iTmp);
			}			
		}
		for(Igrac iIter: listaIgraca) {
			Novcic nTmp = null;
			for(Novcic nIter: listaNovcica) {
				if(iIter.equals(nIter)) {
					sakupljeniNovcici++;
					nTmp = nIter;
					break;
				}				
			}
			synchronized (listaNovcica) {				
				if(nTmp != null)
					listaNovcica.remove(nTmp);
			}
		}
		if(listaNovcica.size() == 0) {
			zaustaviMrezu();
		}
		azurirajLabele();
	}
	
	private void azurirajLabele() {
		igra.brojPoena.setText("Poena: " + sakupljeniNovcici);
	}
	
	public void zaustaviMrezu() {
		if(nitMreza != null)
			nitMreza.interrupt();
		//if(tajmerMreza != null)
		//	tajmerMreza.pauziraj();
		for (Tenk tIter : listaTenkova) {
			tIter.zaustaviTenk();
		}		
	}
	
	public void pokreniMrezu() {
		if(nitMreza != null)
			nitMreza.interrupt();
		//if(tajmerMreza != null)
		//	tajmerMreza.resetTajmera();
		konfigurisi();
		nitMreza.start();
		//tajmerMreza.kreni();
		for (Tenk tIter : listaTenkova) {
			tIter.pokreniTenk();
		}
		
	}

	
	private void dodajTenkove() {

		while(brojTenkova > 0) {
			int i = (int) (Math.random() * n);
			int j = (int) (Math.random() * n);			
			if(polja[i][j] instanceof Trava) {
				Tenk tTmp = new Tenk(polja[i][j]);
				boolean zauzeto = false;
				for(Tenk tIter : listaTenkova)
					if(tIter.equals(tTmp)) {
						zauzeto = true;
						break;
					}
				for(Novcic nIter : listaNovcica)
					if(nIter.equals(tTmp)) {
						zauzeto = true;
						break;
					}
				for(Igrac iIter : listaIgraca)
					if(iIter.equals(tTmp)) {
						zauzeto = true;
						break;
					}
				if(zauzeto == false) {
					listaTenkova.add(tTmp);
					brojTenkova--;
				}
			}
		}
		
	}

	
	private void dodajNovcice() {

		while(brojNovcica > 0) {
			int i = (int) (Math.random() * n);
			int j = (int) (Math.random() * n);			
			if(polja[i][j] instanceof Trava) {
				Novcic nTmp = new Novcic(polja[i][j]);
				boolean zauzeto = false;
				for(Novcic nIter : listaNovcica)
					if(nIter.equals(nTmp)) {
						zauzeto = true;
						break;
					}
				for(Igrac iIter : listaIgraca)
					if(iIter.equals(nTmp)) {
						zauzeto = true;
						break;
					}
				if(zauzeto == false) {
					listaNovcica.add(nTmp);
					brojNovcica--;
				}
			}
		}
		
	}

	private void dodajIgraca() {
		while(true) {
			int i = (int) (Math.random() * n);
			int j = (int) (Math.random() * n);
			if(polja[i][j] instanceof Trava) {
				Igrac iTmp = new Igrac(polja[i][j]);
				boolean zauzeto = false;
				for(Igrac iIter : listaIgraca)
					if(iIter.equals(iTmp)) {
						zauzeto = true;
						break;
					}
				if(zauzeto == false) {
					listaIgraca.add(new Igrac(polja[i][j]));
					break;
				}
			}
		}		
		
		
		while(true) {
			int i = (int) (Math.random() * n);
			int j = (int) (Math.random() * n);
			if(polja[i][j] instanceof Trava) {
				ProtivnickiIgrac iTmp = new ProtivnickiIgrac(polja[i][j]);
				boolean zauzeto = false;
				for(Igrac iIter : listaIgraca)
					if(iIter.equals(iTmp)) {
						zauzeto = true;
						break;
					}
				if(zauzeto == false) {
					listaIgraca.add(iTmp);
					break;
				}
			}
		}		
	}

	private void dodajPolja() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				add(polja[i][j]);	
			}
		}	
	}
	

	private void popuniMatricuPolja() {
		int zidovi = 20 * n * n / 100;
		int pustinje = 12 * n * n / 100;

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				polja[i][j] = null;
			}
		}
		while(zidovi > 0) {
			int i = (int) (Math.random() * n);
			int j = (int) (Math.random() * n);
			if(polja[i][j] == null) {
				zidovi--;
				polja[i][j] = new Zid(this);
			}
		}
		while(pustinje > 0) {
			int i = (int) (Math.random() * n);
			int j = (int) (Math.random() * n);
			if(polja[i][j] == null) {
				pustinje--;
				polja[i][j] = new PustinjaIPrasuma(this);
			}
		}
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(polja[i][j] == null) {
					polja[i][j] = new Trava(this);
				}
			}
		}
	}
	
	
	
	public Mreza(Igra igra) {
		this(17, igra);
	}


	public Polje[][] getPolja() {
		return polja;
	}


	public List<Igrac> getListaIgraca() {
		return listaIgraca;
	}

	public List<Novcic> getListaNovcica() {
		return listaNovcica;
	}

	public List<Tenk> getListaTenkova() {
		return listaTenkova;
	}
	
	
	/*for(int i = 0; i < n; i ++) {
	for(int j = 0; j < n; j++) {
		final int ii = i;
		final int jj = j;	
		mreza.getPolja()[i][j].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(rezimIzmene == true) {
						System.out.println("Kliknuo");
						mreza.removeAll();
						mreza.getPolja()[ii][jj] = new Trava(mreza);
						for(int iT = 0; iT < n; iT++) {
							for(int jT = 0; jT < n; jT++) {
								mreza.add(mreza.getPolja()[iT][jT]);
							}
						}
						mreza.repaint();
					}
				}
			});
		}
	}*/
	
}

/*
class Timer extends Thread {

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