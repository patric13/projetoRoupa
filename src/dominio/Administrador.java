package dominio;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@PrimaryKeyJoinColumn(name="idPessoa")
public class Administrador extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	


}
