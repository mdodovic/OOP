package domZad1;

public abstract class Zivotinja {
	protected Rupa rupa;

	public Zivotinja(Rupa rupa) {
		this.rupa = rupa;
	}
		
	public int dimenzija() {		
		return 100 * rupa.getTrenutniBrojKoraka() / rupa.getUkupanBrojKoraka();
	}
	
	
	public abstract void iscrtaj();
	public abstract void efekatUdarene();
	public abstract void efekatPobegle();

}
