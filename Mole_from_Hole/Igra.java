package domZad1;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Igra extends Frame {
	private Basta basta = new Basta(4,4);
	
	private Label tezina = new Label("Tezina:");
	private Checkbox nivoLak;
	private Checkbox nivoSrednji;
	private Checkbox nivoTezak;
	private Button kreniStani;
	private boolean uToku = false;
	
	private Label povrce = new Label("Povrce: 100 / p: 0");
	
	private Igra(){
		super("Igra");
		setSize(800, 600);
		add(basta, BorderLayout.CENTER);
		add(pocetnaKonfiguracija(), BorderLayout.EAST);
		dodajOsluskivace();
		
		//dodajMeni();
		//dodajDijalog();
		
		setVisible(true);	
	}
	
	/*private void dodajDijalog() {
		Dialog preIgre = new Dialog(this, ModalityType.APPLICATION_MODAL);
		preIgre.setTitle("Pomocni dijalog");
		preIgre.add(new Label("Za unistavanje krtice klikni je", Label.CENTER), BorderLayout.CENTER);
		preIgre.add(new Label("Povrce se smanjuje kad god ti krtica pobegne", Label.CENTER), BorderLayout.SOUTH);
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

	/*private void dodajMeni() {
		MenuBar bar = new MenuBar();
		Menu meniIgre = new Menu("Opcije");
		setMenuBar(bar); 
		bar.add(meniIgre);
		MenuItem opcijaKreni = new MenuItem("Pocni igru");
		Menu meniStani = new Menu("Opcije zaustavljanja");
		
		MenuItem opcijaKraj = new MenuItem("Zaustavi aplikaciju");
		MenuItem opcijaStani = new MenuItem("Zaustavi igru");

		meniStani.add(opcijaStani);
		meniStani.addSeparator();
		meniStani.add(opcijaKraj);
		opcijaKraj.setShortcut(new MenuShortcut('Z'));
		meniIgre.add(opcijaKreni);
		meniIgre.add(meniStani);

		opcijaKreni.addActionListener( e -> {
			if(uToku == false) {
				if(nivoLak.getState()) {
					basta.setBrojKoraka(10);
					basta.setDt(1000);
				} else {
					if(nivoSrednji.getState()) {
						basta.setBrojKoraka(8);
						basta.setDt(750);
						
					} if(nivoTezak.getState()) {
						basta.setBrojKoraka(6);
						basta.setDt(500);	
					}
				}				
				basta.pokreni();
				kreniStani.setLabel("Stani");
				uToku = !uToku;
				azuriraj(uToku);
			}
		});
		opcijaKraj.addActionListener( e -> {
			basta.zaustavi();
			basta.tajmer.interrupt();
			dispose();
		});
		opcijaStani.addActionListener( e -> {
			if(uToku == true) {
				basta.zaustavi();
				kreniStani.setLabel("Kreni");
				uToku = !uToku;
				azuriraj(uToku);
			}
		});
	}*/

	private void azuriraj(boolean traje) {
		nivoLak.setEnabled(!traje);
		nivoSrednji.setEnabled(!traje);
		nivoTezak.setEnabled(!traje);
	}
	
	private Component pocetnaKonfiguracija() {
		Panel p1 = new Panel(new GridLayout(2, 1));
		Panel p2 = new Panel(new GridLayout(5, 1));
		tezina.setAlignment(Label.CENTER);
		tezina.setFont(new Font(null, Font.BOLD, 18));
		
		CheckboxGroup grupa = new CheckboxGroup();
		p2.add(tezina);
		p2.add(nivoLak = new Checkbox("Lak", grupa, false));
		p2.add(nivoSrednji = new Checkbox("Srednji", grupa, true));
		p2.add(nivoTezak = new Checkbox("Tezak", grupa, false));

		kreniStani = new Button("Kreni");
		p2.add(kreniStani);		

		p1.add(p2);
		
		povrce.setAlignment(Label.CENTER);
		povrce.setFont(new Font(null, Font.BOLD, 18));
		p1.add(povrce);
		basta.postaviLabelu(povrce);
		
		return p1;

	}

	private void dodajOsluskivace() {
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				basta.zaustavi();
				//basta.tajmer.interrupt();
				dispose();
			}
		});
		
		kreniStani.addActionListener(e -> {
			if(uToku == true) {
				basta.zaustavi();
				kreniStani.setLabel("Kreni");
			}
			else {
				if(nivoLak.getState()) {
					basta.setBrojKoraka(10);
					basta.setDt(1000);
				} else {
					if(nivoSrednji.getState()) {
						basta.setBrojKoraka(8);
						basta.setDt(750);
						
					} if(nivoTezak.getState()) {
						basta.setBrojKoraka(6);
						basta.setDt(500);	
					}
				}
				basta.pokreni();
				kreniStani.setLabel("Stani");
			}
			uToku = !uToku;
			azuriraj(uToku);
		});
		
		
		
		

	}

	
	private static Igra jedinaInstanca = null; 
	
	public static Igra dohvatiIgru() 
    { 
        if (jedinaInstanca == null) 
            jedinaInstanca = new Igra(); 
  
        return jedinaInstanca; 
    } 
	
	public static void main(String[] args) {
		dohvatiIgru();
	}
	
}
