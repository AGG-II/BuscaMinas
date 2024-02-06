package tablero;

public class Main {

	public static void main(String[] args) {
		/*Tablero test = Tablero.crear_tablero();
		System.out.println("Las filas son:" + test.get_filas());
		System.out.println("Las columnas son:" + test.get_columnas());
		System.out.println("Las bombas son:" + test.get_cantBombas());
		test.iniciar_partida();
		test.dibujar_tablero();*/
		TableroVisual tester1 = new TableroVisual(9,9);
		tester1.setVisible(true);
		/*PantallaInicio tester = new PantallaInicio();
		tester.setVisible(true);*/
		}

}
