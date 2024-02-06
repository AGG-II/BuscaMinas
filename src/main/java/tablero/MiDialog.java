package tablero;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MiDialog extends JDialog{
	static final Dimension d = new Dimension(200,100); 
	public MiDialog(String mensaje) {
		setModal(true); // No se puede tocar el resto del JFrame cuando aparece el Dialog
		setMinimumSize(d); // Setea las dimensiones de la ventana
		setResizable(false); // No pueden modificar las dimensiones de la ventana
		setLocationRelativeTo(null); // La ventana aparece en el centro de la pantalla

		Container ct = getContentPane();
		JPanel panelCentrado = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel cartel = new JLabel(mensaje);
		JButton botonSalir = new JButton("Salir");
		
		botonSalir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});
		
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	dispose();
        		System.exit(0);
            }
        });
	    
		panel.add(cartel);
		panel.add(botonSalir);
		panelCentrado.add(panel);
		ct.add(panelCentrado);
		setVisible(true);
	}
}
