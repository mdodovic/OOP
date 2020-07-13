package domZad2;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Igra extends Frame {
	int n = 17;
	private Mreza mreza = new Mreza(this);
	
	private Label podloga = new Label("Podloga:");

	Checkbox trava;
	Checkbox zid;
	Checkbox pustinjaIPrasuma;

	private Label novcici = new Label("Novcici:");
	TextField brojNovcica = new TextField("10");
	
	Label brojPoena = new Label("Poena: 0");
	private Button pocni;
	
	public enum rezim {IZMENA, IGRANJE };
	rezim rezimIgre = rezim.IZMENA;
	
	public Igra(){
		super("Igra");
		setSize(800, 700);		
		add(istok(), BorderLayout.EAST);
		add(jug(), BorderLayout.SOUTH);
		dodajMeni();
		//dodajDijalog();
		add(mreza, BorderLayout.CENTER);

		this.setFocusable(true);
		this.requestFocusInWindow();

		dodajListenere();
		
		setVisible(true);
	}

	/*private void dodajDijalog() {
		Dialog preIgre = new Dialog(this, ModalityType.APPLICATION_MODAL);
		preIgre.setTitle("Pomocni dijalog");
		preIgre.add(new Label("Kreces se na wasd", Label.CENTER), BorderLayout.CENTER);
		preIgre.add(new Label("Nedaj da te uhvate tenkovi", Label.CENTER), BorderLayout.SOUTH);
		Button b = new Button("Spreman");
		b.addActionListener( e -> {
			preIgre.dispose();
		});
		preIgre.add(b, BorderLayout.NORTH);
		preIgre.setBounds(700, 200, 500, 200);
		preIgre.setResizable(false);
		preIgre.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				preIgre.dispose();
			}
		});
		preIgre.setVisible(true);
	}*/

	private void dodajMeni() {
		MenuBar bar = new MenuBar();
		Menu meniRezim = new Menu("Rezim");
		setMenuBar(bar); 
		bar.add(meniRezim);
		MenuItem rezimIzmena = new MenuItem("Rezim izmena");
		MenuItem rezimIgranje = new MenuItem("Rezim igranje");
		meniRezim.add(rezimIzmena);
		meniRezim.add(rezimIgranje);

		rezimIzmena.addActionListener( e->{
			if(rezimIgre == rezim.IGRANJE) {
				mreza.zaustaviMrezu();
				mreza.ocistiListe();
				for(int i = 0; i < 17; i++)
					for(int j = 0; j < 17; j++)
						mreza.getPolja()[i][j].repaint();
			}
			azurirajDozvoleIzmena(rezim.IZMENA);
			rezimIgre = rezim.IZMENA;	
			
		});
		
		rezimIgranje.addActionListener( e->{
			rezimIgre = rezim.IGRANJE;
			azurirajDozvoleIzmena(rezim.IGRANJE);
			requestFocus();
		});

	}

	
	private Component jug() {
		Panel p = new Panel();
		novcici.setAlignment(Label.CENTER);
		p.add(novcici);
		p.add(brojNovcica);
		p.add(brojPoena);
		p.add(pocni = new Button("Pocni"));

		azurirajDozvoleIzmena(rezim.IZMENA);


		return p;
	}

	private Component istok() {
		Panel p = new Panel(new GridLayout(1, 2));
		podloga.setAlignment(Label.LEFT);
		p.add(podloga);
		
		Panel pRadio = new Panel(new GridLayout(3, 1));		
		CheckboxGroup grupa = new CheckboxGroup();
		trava = new Checkbox("Trava", grupa, true);
		trava.setBackground(Color.GREEN);
		pRadio.add(trava);
		zid = new Checkbox("Zid", grupa, false);
		zid.setBackground(Color.LIGHT_GRAY);
		pRadio.add(zid);
		pustinjaIPrasuma = new Checkbox("Pustinja i Prasuma", grupa, false);
		pustinjaIPrasuma.setBackground(Color.ORANGE);
		pRadio.add(pustinjaIPrasuma);

		p.add(pRadio);
		
		return p;
	}

	private void dodajListenere() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mreza.zaustaviMrezu();
//				if(mreza.tajmerMreza != null)
//					mreza.tajmerMreza.interrupt();
				dispose();
			}
		});

		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char key = Character.toUpperCase(e.getKeyChar());
				switch (key) {
					case KeyEvent.VK_W: {
						pomeriIgrace(0);
						break;
					}
					case KeyEvent.VK_A: {
						pomeriIgrace(1);
						break;
					}
					case KeyEvent.VK_S: {
						pomeriIgrace(2);
						break;
					}
					case KeyEvent.VK_D: {
						pomeriIgrace(3);
						break;
					}
				}
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						pomeriIgrace(4);
						break;
					}
					case KeyEvent.VK_LEFT: {
						pomeriIgrace(5);
						break;
					}
					case KeyEvent.VK_DOWN: {
						pomeriIgrace(6);
						break;
					}
					case KeyEvent.VK_RIGHT: {
						pomeriIgrace(7);
						break;
					}
		
				}
				mreza.repaint();
			}
		});
		
		this.pocni.addActionListener((al) -> {
			if(rezimIgre == rezim.IGRANJE) {
				mreza.zaustaviMrezu();
				mreza.postaviBrojNovcica();
				mreza.pokreniMrezu();
				azurirajDozvoleIzmena(rezim.IGRANJE);
			}
			requestFocus();
		});


	}

	
	
	private void azurirajDozvoleIzmena(rezim r) {
		if(r == rezim.IGRANJE) {
			this.brojNovcica.setEnabled(true);
			this.pocni.setEnabled(true);
		}
		else {
			this.brojNovcica.setEnabled(false);
			this.pocni.setEnabled(false);			
		}
	}

	private void pomeriIgrace(int smer) {
		for(Igrac iIter: mreza.getListaIgraca()) {
			if(mreza.nitMreza.isAlive()) {
				if ( 0 <= smer && smer <= 3) {
					if(! (iIter instanceof ProtivnickiIgrac)) {
						int[] tekucaPozicija = iIter.getPolje().pozicija();
						int[] narednaPozicija = naredniPomeraj(tekucaPozicija, iIter.getPolje(), smer, iIter);
						Polje staroPolje = iIter.getPolje().dohvatiZaPomeraj(0, 0);
						Polje novoPolje = iIter.getPolje().dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]);					
						iIter.pomeri(novoPolje);
						staroPolje.repaint();
						novoPolje.repaint();		
					}
				}
				if ( 4 <= smer && smer <= 7) {					
					if(iIter instanceof ProtivnickiIgrac) {
						int[] tekucaPozicija = iIter.getPolje().pozicija();
						int[] narednaPozicija = naredniPomeraj(tekucaPozicija, iIter.getPolje(), smer - 4, iIter);
						Polje staroPolje = iIter.getPolje().dohvatiZaPomeraj(0, 0);
						Polje novoPolje = iIter.getPolje().dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]);					
						iIter.pomeri(novoPolje);
						staroPolje.repaint();
						novoPolje.repaint();		
					}
				}

			}
		}								
	}
	private int[] naredniPomeraj(int[] tekucaPozicija, Polje tekucePolje, int smer, Figura figura) {
		int i = tekucaPozicija[0];
		int j = tekucaPozicija[1];
		int[] narednaPozicija = new int[] {0, 0};	

		switch (smer) {
			case 0: // Gore - W
				if(i == 0)
					break;
				narednaPozicija[0] = -1;
				if(tekucePolje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(figura) == false) {
					narednaPozicija[0] = 0;
					break;
				}
				break;				
			case 1: // Levo - A
				if(j == 0)
					break;
				narednaPozicija[1] = -1;
				if(tekucePolje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(figura) == false) {
					narednaPozicija[1] = 0;
					break;
				}
				break;
			case 2: // Dole - S
				if(i == 16)
					break;
				narednaPozicija[0] = +1;
				if(tekucePolje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(figura) == false) {
					narednaPozicija[0] = 0;
					break;
				}
				break;
			case 3: // Desno - D
				if(j == 16)
					break;
				narednaPozicija[1] = +1;
				if(tekucePolje.dohvatiZaPomeraj(narednaPozicija[0], narednaPozicija[1]).mozeFigura(figura) == false) {
					narednaPozicija[1] = 0;
					break;
				}
				break;

		}
		return narednaPozicija;
	}

	public static void main(String[] args) {
		new Igra();
	}

}
