package tablero;

import javax.swing.JButton;

public class MiBoton extends JButton {

	private MiPunto coordenada;
	private String estado = "Nada";
	public MiBoton(MiPunto coordenada) {
		this.coordenada = coordenada;
	}
	
	MiPunto get_coordenada() {
		return coordenada;
	}
	
	public String get_estado() {
		return estado;
	}
	public void set_estado(String estado) {
		this.estado = estado;
	} 
	
}
