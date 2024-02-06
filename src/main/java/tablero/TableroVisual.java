package tablero;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TableroVisual extends JFrame {
	final Dimension d = new Dimension(800,600);
	private Tablero tablero = null;
	private Map<MiPunto, MiBoton> botones= new HashMap<>();
	private int x;
	private int y;
	private int cantidadCasillas;
	
	public TableroVisual(int x, int y) {
	this.x = x;
	this.y = y;
	this.cantidadCasillas = x * y;
	this.tablero = Tablero.crear_tablero(1);
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
	       /*inicio*/ boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						String contenido = obtener_casilla(boton);
						jugada(boton, contenido);
					}catch(Exception ex){
						tablero.iniciar_partida(boton.get_coordenada());
						jugada(boton, obtener_casilla(boton));
					}
					
				}
			});// final
	       boton.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                // Check if the right mouse button is clicked (Button3 for right click)
	                if (SwingUtilities.isRightMouseButton(e)) {
	                	if(boton.get_estado() == "Nada") {
	                		boton.set_estado("Bandera");
	                		boton.setText("🚩");
	                	}else if(boton.get_estado() == "Bandera"){
	                		boton.set_estado("Nada");
	                		boton.setText("");
	                	}
	                	
	                }
	            }
	        });
			// boton.setText(i + " , " + j);
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
		String retorno = "∅";
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
		if(boton.get_estado() == "Apretado" || boton.get_estado() == "Bandera"/*-------------------------------!HACER UN ENUM!------------------------------------------------------------------------*/) {
			return;
		}
		boton.set_estado("Apretado");
		boton.setText(contenido);
		switch(contenido) {
		case "💣":
			perdio();
			break;
		case "∅":
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
