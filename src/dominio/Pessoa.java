package dominio;

import java.io.IOException;
import java.io.Serializable;
import javax.persistence.InheritanceType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.SequenceGenerator;
import dominio.EntityIdSequencial;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements EntityIdSequencial, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PESSOA_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "PESSOA_ID", sequenceName = "SEQ_PESSOA", allocationSize = 1)
	private Long id;

	//
	private String cpf;
	private String nome;
	private String dt_nasc;
	private String email;
	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String complemento;
	private int numero;
	private int telefone;
	private int celular;

	private int cep;

	public Pessoa(String cpf, String nome, String dt_nasc, String email,
			String estado, String cidade, String bairro, int cep,
			String logradouro, String complemento, int numero, int telefone,
			int celular) throws IOException {
		super();
		this.setCpf(cpf);
		this.setNome(nome);
		this.setDt_nasc(dt_nasc);
		this.setEmail(email);
		this.setEstado(estado);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setLogradouro(logradouro);
		this.setComplemento(complemento);
		this.setNumero(numero);
		this.setTelefone(telefone);
		this.setCelular(celular);

		this.setCep(cep);
	}

	public Pessoa() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDt_nasc() {
		return dt_nasc;
	}

	public void setDt_nasc(String dt_nasc) {
		this.dt_nasc = dt_nasc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	public int getCelular() {
		return celular;
	}

	public void setCelular(int celular) {
		this.celular = celular;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.cpf;
	}

}
