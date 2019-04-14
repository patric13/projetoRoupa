package model.util;


public class DadosException extends Exception {
	private ErroDeDominio erro;
	
	public DadosException(ErroDeDominio erro) {
		super(erro.toString());
		this.erro = erro;
	}
}
