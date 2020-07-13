package domZad3;

public class Vektor implements Cloneable {
	private double x, y;

	public Vektor(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public Vektor clone(){
		Vektor v = null;
		try {
			v = (Vektor) super.clone();
		} catch (CloneNotSupportedException e) {}
		return v;
	}
	
	public void pomnozi(double t) {
		x *= t;
		y *= t;
	}
	
	public void saberi(Vektor v) {
		x += v.x;
		y += v.y;
	}
	
	public static void main(String[] args) {
		Vektor v1 = new Vektor(2,3);
		Vektor v2 = v1.clone();
		
		v2.pomnozi(2);
		System.out.println(v1.getX() + " != " + v2.getX());
		
	}

}
