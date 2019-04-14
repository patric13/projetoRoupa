package viewer.desktop.util;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import viewer.util.ErroDeInterface;
import viewer.util.InterfaceException;

public class JIntField extends JTextField {
	
	public int getValue() throws InterfaceException{
		int resultado;
		try {
			resultado = Integer.parseInt(this.getText());
		}
		catch(NumberFormatException nfe) {
			this.setBackground(Color.RED);
			JOptionPane.showMessageDialog(this, "Valor Inteiro Inválido");
			this.setBackground(Color.WHITE);
			throw new InterfaceException(new ErroDeInterface("Valor Inteiro Inválido"));
		}
		return resultado;
	}
	
	public void setValue(int value) {
		this.setText(Integer.toString(value));
	}

}
