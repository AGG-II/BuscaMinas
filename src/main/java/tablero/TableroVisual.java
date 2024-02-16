package tablero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TableroVisual extends JFrame {
	final Dimension d = new Dimension(1000,750);
	private Tablero tablero = null;
	private Map<MiPunto, MiBoton> botones= new HashMap<>();
	private int x;
	private int y;
	private int cantidadCasillas;
	
	public TableroVisual(int x, int y, Dificultades diff) {
	this.x = x;
	this.y = y;
	this.cantidadCasillas = x * y;
	this.tablero = Tablero.crear_tablero(diff);
	setMinimumSize(d);
	setPreferredSize(d);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	Container ct = getContentPane();
	JPanel panel = new JPanel(new GridLayout(x, y));
	panel.setPreferredSize(new Dimension(200,300));
	
	for(int i = 0; i< x; i++) {
		for(int j = 0; j < y; j++) {
	        MiPunto coordenada = new MiPunto(i,j);
	        MiBoton boton = new MiBoton(coordenada);
	       boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						String contenido = obtener_casilla(boton);
						jugada(boton, contenido);
					}catch(Exception ex){
						tablero.iniciar_partida(boton.get_coordenada());
						jugada(boton, obtener_casilla(boton));
					}
					
				}
			});
	       boton.addMouseListener(new MouseAdapter() {
	            public void mouseReleased(MouseEvent e) {
	                if (SwingUtilities.isRightMouseButton(e)) {
	                	if(boton.get_estado() == EstadosBoton.NADA) {
	                		boton.set_estado(EstadosBoton.BANDERA);
	                		boton.setText("🚩");
	                	}else if(boton.get_estado() == EstadosBoton.BANDERA){
	                		boton.set_estado(EstadosBoton.NADA);
	                		boton.setText("");
	                	}
	                	
	                }
	            }
	        });
	        botones.put(coordenada, boton);
			panel.add(boton);
		}
	}
	ct.add(panel, BorderLayout.CENTER);
	
	JPanel panelCartel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JLabel cartel = new JLabel("¡Buscaminas!");
	panelCartel.add(cartel);
	ct.add(panelCartel, BorderLayout.NORTH);
	
	}

	private String obtener_casilla(MiBoton boton) {
		String retorno = "";
		Set<MiPunto> bombas= tablero.get_bombas();
		Map<MiPunto, Integer> numeros = tablero.get_numeros();
		MiPunto casilla = boton.get_coordenada();
		if(bombas.contains(casilla)) {
			retorno = "💣";
		}else if(numeros.containsKey(casilla)){
			retorno = numeros.get(casilla).toString();
		}
		return retorno;
	}
	
	private void jugada(MiBoton boton, String contenido) {
		if(boton.get_estado() == EstadosBoton.APRETADO || boton.get_estado() == EstadosBoton.BANDERA) {
			return;
		}
		boton.setBackground(new Color(200,200,200));
		boton.set_estado(EstadosBoton.APRETADO);
		boton.setText(contenido);
		switch(contenido) {
		case "💣":
			perdio();
			break;
		case "":
			toco_vacio(boton);
			break;
		}
		// No hay ningun comportamiento especial si la casilla seleccionada es un numero
		cantidadCasillas--;
		if(cantidadCasillas == tablero.get_cantBombas()) {
			gano();
		}
	}
	
	private void gano() {
		MiDialog cartelPerdiste = new MiDialog("¡Ganaste!");
		cartelPerdiste.setVisible(true);
	}
	
	private void perdio() {
		MiDialog cartelPerdiste = new MiDialog("¡Perdiste!");
		cartelPerdiste.setVisible(true);
	}
	
	private void toco_vacio(MiBoton boton) {
		Set <MiBoton> casillasAd = casillas_adyacentes(boton);
		for(MiBoton adyacente : casillasAd) {
			jugada(adyacente, obtener_casilla(adyacente));
		}
	}
	private Set<MiBoton> casillas_adyacentes(MiBoton boton){
		MiPunto coordenada = boton.get_coordenada();
		Set<MiPunto> adyacentes = coordenada.adyacentes();
		Set<MiBoton> retorno = new HashSet<>();
		
		for(MiPunto adyacente : adyacentes) {
			if(botones.containsKey(adyacente)) {
				retorno.add(botones.get(adyacente));
			}
		}
		return retorno;
	}
}
