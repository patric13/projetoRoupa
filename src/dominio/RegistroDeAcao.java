package dominio;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import dominio.EntityIdSequencial;
import model.DadosException;

@Entity
public class RegistroDeAcao implements EntityIdSequencial, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "REGISTRODEACAO_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "REGISTRODEACAO_ID", sequenceName = "SEQ_REGISTRODEACAO", allocationSize = 1)
	private Long id;

	//
	private Calendar data;
	private String descricao;
	


	@ManyToOne
	private Usuario usuario;

	public RegistroDeAcao() {
		super();
	}

	public RegistroDeAcao(String tipo, Usuario usuario) throws DadosException {
		super();
		this.setDescricao(tipo);
		Calendar hoje = Calendar.getInstance();
		this.setData(hoje);
		this.setUsuario(usuario);
		
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Long getId() {
		
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	

}
