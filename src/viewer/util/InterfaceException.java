package viewer.util;



public class InterfaceException extends Exception {
	private ErroDeInterface erro;
	
	public InterfaceException(ErroDeInterface erro) {
		super(erro.toString());
		this.erro = erro;
	}
}
