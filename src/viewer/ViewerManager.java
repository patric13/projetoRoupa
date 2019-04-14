package viewer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import viewer.desktop.JanelaPrincipal;
import controller.util.ICtrlCasoDeUso;

public class ViewerManager {
	
	private static Properties relacaoDeUIs;
	
	public static void inicializar() {	     
	     try {
	    	 // Recupero o arquivo viewer.properties que está no pacote onde está esta classe
			InputStream in = ViewerManager.class.getResourceAsStream("viewer.properties");
			relacaoDeUIs = new Properties();
			relacaoDeUIs.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object obterViewer(ICtrlCasoDeUso controlador, Class classificador) {
		Object userInterface = null;
		try {
			if(relacaoDeUIs == null)
				ViewerManager.inicializar();
			String nomeInterface = classificador.getName();		
		    String nomeClasseUI = relacaoDeUIs.getProperty(nomeInterface);
			Class cl = Class.forName(nomeClasseUI);
			Constructor construtor = cl.getConstructor(controlador.getClass());
			userInterface = construtor.newInstance(controlador);			
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		return userInterface;
	}
}
