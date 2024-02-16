package tablero;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Tablero {
	private int filas;
	private int columnas;
	private int cantBombas;
	private Set<MiPunto> bombas;
	private Map<MiPunto, Integer> numeros;
	
	private Tablero (int filas, int columnas,int cantBombas) {
		this.filas = filas;
		this.columnas = columnas;
		this.cantBombas = cantBombas;
	}
	
	static public Tablero crear_tablero(Dificultades diff) {
		int filas = 9, columnas = 9, cantBombas = 10;
		switch(diff) {
			case FACIL:
				filas = 9;
				columnas = 9;
				cantBombas = 10;
				break;
			case MEDIO:
				filas = 16;
				columnas = 16;
				cantBombas = 40;
				break;
			case DIFICIL:
				filas = 16;
				columnas = 30;
				cantBombas = 99;
				break;
			}
		return  new Tablero(filas, columnas, cantBombas);
	}
	
	public int get_filas() {
		return filas;
	}
	
	public int get_columnas() {
		return columnas;
	}
	
	public int get_cantBombas() {
		return cantBombas;
	}

	public Set<MiPunto> get_bombas() {
		return bombas;
	}
	
	public Map<MiPunto, Integer> get_numeros() {
		return numeros;
	}

	public void iniciar_partida(MiPunto inicio) {
		Set<MiPunto> prohibidas = posiciones_prohibidas(inicio);
		MiPunto bomba;
		bombas = new HashSet<>();
		numeros = new HashMap<>();
		for(int i = 0; i< cantBombas; i ++) {
			bomba = crear_bomba(prohibidas);
			agregar_bomba(bomba);
		}
	}
	
	// Retorna un punto en donde se puede agregar una bomba
	private MiPunto crear_bomba(Set<MiPunto> prohibidas) {
		Random rand = new Random();
		int cantCasillas = filas * columnas;
		int nroCasilla = rand.nextInt(cantCasillas);
		MiPunto bomba = MiPunto.deNumero_aPunto(nroCasilla, columnas);
		// Si la casilla elegida aleatoriamente no puede ser una bomba o ya fue elegida anteriormente entonces 
		// se fija en una casilla adyacente (si la casilla es la ultima entonces comienza desde la primera casilla)
		while(prohibidas.contains(bomba) || bombas.contains(bomba)) {
			nroCasilla = (nroCasilla + 1) % cantCasillas;
			bomba = MiPunto.deNumero_aPunto(nroCasilla, columnas);
		}
		return bomba;
	}
	
	// Dado el punto inicial retorna todas las casillas que no pueden contener bombas
	private Set<MiPunto> posiciones_prohibidas(MiPunto centro){
		Set<MiPunto> prohibidos = centro.adyacentes();
		Set<MiPunto> retorno = new HashSet<>();
		for(MiPunto adyacente : prohibidos) {
			if(adyacente.numero_casilla(filas, columnas) != -1) {
				retorno.add(adyacente);
			}
		}
		return retorno;
	}

	private void agregar_bomba(MiPunto bomba) {
		// Dejamos de contar la cantidad de bombas adyacentes a la casilla
		numeros.remove(bomba);
		bombas.add(bomba);
		Set<MiPunto> adyacentes = bomba.adyacentes();
		// Todos los puntos adyacentes a la bomba que no sean una bomba deben aumentar el numero de bombas adyacentes en 1
		for(MiPunto adyacente : adyacentes) {
			// Si la casilla es valida
			if(adyacente.numero_casilla(filas, columnas) != -1) {
				Integer numero = numeros.get(adyacente);
				if(numero != null) {
					numeros.put(adyacente, numero + 1);
				}else {
					numeros.put(adyacente, 1);
				}
			}
		}
	}

	public void dibujar_tablero() {
		MiPunto casilla = new MiPunto(0,0);
		for(int x = 0; x < columnas; x++) {
			StringBuilder salida = new StringBuilder();
			for(int y = 0; y < filas; y++) {
				casilla.setear(x,y);
				if(bombas.contains(casilla)) {
					salida.append("💣\t");
				}else if(numeros.containsKey(casilla)){
					salida.append(numeros.get(casilla) + "\t");
				}else {
					salida.append("∅\t");
				}
			}
			System.out.println(salida);
		}
	}
}
