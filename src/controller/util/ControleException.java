package controller.util;


public class ControleException extends Exception {
	private ErroDeControle erro;
	
	public ControleException(ErroDeControle erro) {
		super(erro.toString());
		this.erro = erro;
	}
}
