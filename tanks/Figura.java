package domZad2;

public abstract class Figura {
	
	protected Polje polje;

	public Figura(Polje polje) {
		super();
		this.polje = polje;
	}

	public Polje getPolje() {
		return polje;
	}
	
	public void pomeri(Polje p) {
		polje = p;
	}

	public boolean equals(Figura f) {
		int[] tek = polje.pozicija();
		int[] arg = f.polje.pozicija();
		if (tek[0] == arg[0] && tek[1] == arg[1])
			return true;
		return false;
	}
	
	public abstract void iscrtaj();
	

}
