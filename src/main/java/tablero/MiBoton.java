package tablero;

import javax.swing.JButton;

public class MiBoton extends JButton {

	private MiPunto coordenada;
	private EstadosBoton estado = EstadosBoton.NADA;
	public MiBoton(MiPunto coordenada) {
		this.coordenada = coordenada;
	}
	
	MiPunto get_coordenada() {
		return coordenada;
	}
	
	public EstadosBoton get_estado() {
		return estado;
	}
	public void set_estado(EstadosBoton estado) {
		this.estado = estado;
	} 
	
}
