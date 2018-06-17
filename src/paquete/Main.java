package paquete;

public class Main {

	public static void main(String[] args) {
		ImagenGag i = new ImagenGag();
		
		Post gag = i.getPost();
		System.out.println(gag.getTitulo() + " - " + gag.getLink());
	}
}
