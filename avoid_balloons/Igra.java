package domZad3;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Igra extends Frame {

	private Scena scena;
	
	public Igra() {	
		super("Baloni");
		setSize(500, 500);
		
		scena = new Scena(this);
		add(scena, BorderLayout.CENTER);
		
	//	dodajMeni();
	//	dodajDijalog();
		
		dodajOsluskivace();
		
		setVisible(true);
		scena.kreni();
	}
	
	/*private void dodajDijalog() {
		Dialog preIgre = new Dialog(this, ModalityType.APPLICATION_MODAL);
		preIgre.setTitle("Pomocni dijalog");
		preIgre.add(new Label("Bezi od balona", Label.CENTER), BorderLayout.CENTER);
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
	
	}
	*/
	
	private void dodajOsluskivace() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				scena.zaustavi();
				//if(scena.nitTimera != null)
				//	scena.nitTimera.interrupt();
				dispose();
			}
		});
		
	}


	public static void main(String[] args) {
		new Igra();
	}

}
