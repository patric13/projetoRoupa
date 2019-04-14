package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import dominio.EntityIdSequencial;

@Entity
public class Empresa implements EntityIdSequencial, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "EMPRESA_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "EMPRESA_ID", sequenceName = "SEQ_EMPRESA", allocationSize = 1)
	private Long id;

	//
	private String cnpj;
	private String nome;
	private String urlsite;

	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String complemento;
	private int cep;
	private int numero;
	private boolean registroValidado;

	@OneToMany(mappedBy = "empresa")
	private List<ResponsavelEmpresa> conjResp = new ArrayList<ResponsavelEmpresa>();
	@OneToMany(mappedBy = "empresa")
	private List<Vaga> conjVagas = new ArrayList<Vaga>();

	public Empresa(String cnpj, String nome, String urlsite, String estado,
			String cidade, String logradouro, int cep, String complemento,
			int numero, String bairro, boolean registroValidado)
			throws DadosException {
		super();
		this.setCnpj(cnpj);
		this.setNome(nome);
		this.setUrlsite(urlsite);
		this.setEstado(estado);
		this.setCidade(cidade);
		this.setLogradouro(logradouro);
		this.setCep(cep);
		this.setNumero(numero);
		this.setBairro(bairro);
		this.setRegistroValidado(registroValidado);
		this.conjResp = new ArrayList<ResponsavelEmpresa>();
		this.conjVagas = new ArrayList<Vaga>();
		this.setComplemento(complemento);
	}

	public Empresa() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Vaga> getVaga() {
		return conjVagas;
	}

	public void addVaga(Vaga vaga) throws DadosException {
		if (this.conjVagas.contains(vaga))
			return;
		this.conjVagas.add(vaga);
		vaga.setEmpresa(this);
	}

	public void removeVaga(Vaga vaga) throws DadosException {
		if (!this.conjVagas.contains(vaga))
			return;
		this.conjVagas.remove(vaga);
		vaga.setEmpresa(null);
	}

	public List<ResponsavelEmpresa> getResponsavel() {
		return conjResp;
	}

	public void addResponsavel(ResponsavelEmpresa responsavel)
			throws DadosException {
		if (this.conjResp.contains(responsavel))
			return;
		this.conjResp.add(responsavel);
		responsavel.setEmpresa(this);
	}

	public void removeResponsavel(ResponsavelEmpresa responsavel)
			throws DadosException {
		if (!this.conjResp.contains(responsavel))
			return;
		this.conjResp.remove(responsavel);
		responsavel.setEmpresa(null);
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrlsite() {
		return urlsite;
	}

	public void setUrlsite(String urlsite) {
		this.urlsite = urlsite;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isRegistroValidado() {
		return registroValidado;
	}

	public void setRegistroValidado(boolean registroValidado) {
		this.registroValidado = registroValidado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		if (this.id == null)
			return 0;

		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.cnpj;
	}

}
