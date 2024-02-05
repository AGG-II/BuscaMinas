package tablero;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TableroVisual extends JFrame {
	public TableroVisual() {
	// TODO:
	/*
	 * - Que el tamaño del tablero se reciba como argumentos del constructor
	 * */
	Dimension d = new Dimension(800,600);
	setMinimumSize(d);
	setPreferredSize(d);
	setResizable(false);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(9, 9));
	for(int i = 0; i< 9; i++) {
		for(int j = 0; j < 9; j++) {
			// TODO:
			/*
			 * - Crear botones que tengan además la clase mi punto
			 * - Guardar estos botones y así poder referenciarlos
			 * */
			panel.add(new JButton(i + " , " + j));
		}
	}
	Container ct = getContentPane();
	
	ct.add(new JLabel("QWERTYUIOP"), BorderLayout.NORTH);
	ct.add(panel, BorderLayout.CENTER);
	
	}
}
