package tablero;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PantallaInicio extends JFrame {
	public PantallaInicio() {
		setMinimumSize(new Dimension(400,230)); // Setea las dimensiones de la ventana
		setResizable(false); // No pueden modificar las dimensiones de la ventana
		setLocationRelativeTo(null); // La ventana aparece en el centro de la pantalla
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Que se apague la aplicación cuando la cierran

		Container ct = getContentPane();
		
		//Agregamos el cartel en el tope de la ventana
		JLabel cartelInicio = new JLabel("¡Bienvenido!");
		cartelInicio.setFont(new Font("Times New Roman", Font.BOLD, 32));
        JPanel  panelCartelInicio = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCartelInicio.add(cartelInicio);
        ct.add(panelCartelInicio, BorderLayout.NORTH);
        
        // Panel en el que vamos a tener un cartel y los botones de dificultad
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        JLabel cartelDificultades = new JLabel("Elija una dificultad:");
		cartelDificultades.setFont(new Font("Arial", Font.PLAIN, 15));
        JPanel  panelCartelDificultades = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCartelDificultades.add(cartelDificultades);
        JPanel  panelDificultades = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonGroup dificultades = new ButtonGroup();

        JRadioButton facil = new JRadioButton("Fácil");
        facil.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        dificultades.add(facil);
        panelDificultades.add(facil);
        
        JRadioButton medio = new JRadioButton("Medio");
        medio.setFont(new Font("Arial", Font.PLAIN, 24));
        dificultades.add(medio);
        panelDificultades.add(medio);
        
        JRadioButton dificil = new JRadioButton("Difícil");
        dificil.setFont(new Font("Times New Roman", Font.BOLD, 24));
        dificultades.add(dificil);
        panelDificultades.add(dificil);
        
        // Agregamos el cartel y los botones al panel del centro
        panelCentral.add(panelCartelDificultades);
        panelCentral.add(panelDificultades);
        ct.add(panelCentral, BorderLayout.CENTER);
        
        //Agregamos el cartel al pie de la ventana
        JButton iniciar  = new JButton("INICIAR");
        iniciar.setFont(new Font("Times New Roman", Font.BOLD, 24));
        JPanel panelIniciar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelIniciar.add(iniciar);
		ct.add(panelIniciar, BorderLayout.SOUTH);
		
		// Agregamos la funcionalidad al boton de inicio
		iniciar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				TableroVisual tablero;
				if (facil.isSelected()) {
                    tablero = new TableroVisual(9,9, Dificultades.FACIL);
                } else if (medio.isSelected()) {
                	tablero = new TableroVisual(16,16, Dificultades.MEDIO);
                } else if (dificil.isSelected()) {
                	tablero = new TableroVisual(30,16, Dificultades.DIFICIL);
                } else {
                	return;
                }
				setVisible(false);
				tablero.setVisible(true);
			}
		});
	}
}
