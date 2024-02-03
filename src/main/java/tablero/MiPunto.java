package tablero;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class MiPunto extends Point{
	
	public MiPunto(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Retorna todos los puntos adyacentes al punto (incluye al mismo punto).
	public Set<MiPunto> adyacentes(){
		Set<MiPunto> retorno = new HashSet<>();
		for(int offX = -1; offX < 2; offX++) {
			for(int offY = -1; offY < 2; offY++) {
				retorno.add(new MiPunto( (x + offX), (y + offY)));
			}
		}
		return retorno;
	} 
	
	// Retorna el numero de casilla del tablero con las dimensiones dadas,
	// Si el punto no posee coordenadas que existan en el tablero entonces retorna -1.
	public int numero_casilla(int filas, int columnas) {
		if(x < 0 || y < 0)
			return -1;
		if(x >= columnas || y >= filas )
			return -1;
		
		return x + y * columnas;
	}

	// Dado un numero de casilla y las dimensiones del tablero retorna el punto asociado a esa casilla
	static public MiPunto deNumero_aPunto(int nroCasilla, int columnas) {
		int x = nroCasilla % columnas;
		int y = nroCasilla / columnas;
		return new MiPunto(x, y);
	}
	
	public void setear(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
