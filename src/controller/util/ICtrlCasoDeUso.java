package controller.util;

import model.util.DadosException;

public interface ICtrlCasoDeUso {
	/**
	 * 
	 */
	public abstract void iniciar() throws ControleException, DadosException;

	/**
	 * 
	 */
	public abstract void terminar() throws ControleException, DadosException;

}