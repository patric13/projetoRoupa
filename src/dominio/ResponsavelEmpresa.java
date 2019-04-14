package dominio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="idPessoa")
public class ResponsavelEmpresa extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private boolean master;
	@ManyToOne
	private Empresa empresa;
	@OneToMany(mappedBy = "responsavel")
	private List<Vaga> conjVagas = new ArrayList<Vaga>();

	public ResponsavelEmpresa() {
		super();
	}

	public ResponsavelEmpresa(boolean master, String cpf, String nome, String dt_nasc, String email, String estado,
			String cidade, String bairro, int cep, String logradouro, String complemento, int numero, int telefone,
			int celular, Empresa empresa) throws DadosException, IOException {
		super(cpf, nome, dt_nasc, email, estado, cidade, bairro, cep, logradouro, complemento, numero, telefone,
				celular);
		this.setMaster(master);
		this.setEmpresa(empresa);
		this.conjVagas = new ArrayList<Vaga>();

	}

	public List<Vaga> getVaga() {
		return conjVagas;
	}

	public void addVaga(Vaga vaga) throws DadosException {
		if (this.conjVagas.contains(vaga))
			return;
		this.conjVagas.add(vaga);
		vaga.setResponsavel(this);
	}

	public void removeVaga(Vaga vaga) throws DadosException {
		if (!this.conjVagas.contains(vaga))
			return;
		this.conjVagas.remove(vaga);
		vaga.setResponsavel(null);
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return this.getCpf();
	}

}
