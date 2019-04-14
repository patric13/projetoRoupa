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

@Entity
public class Vaga implements EntityIdSequencial, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "VAGA_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "VAGA_ID", sequenceName = "SEQ_VAGA", allocationSize = 1)
	private Long id;

	//
	//
	private String descricao;
	private int qtd;
	private Double pSalario;
	private String hTrabalho;
	private TipoVaga tipo;
	private boolean disponivel;
	private Calendar data;
	private TipoArea area;
	private  String cep;
	private String cidade;
	private  String bairro;
	private String estado;
	private String numero;
	private String complemento;

	@ManyToOne
	private Empresa empresa;


	@ManyToOne
	private ResponsavelEmpresa responsavel;

	public Vaga() {
		super();
	}

	public Vaga(String descricao, int qtd, Double pSalario, String hTrabalho, TipoVaga tipo, boolean disponivel,TipoArea area, String cep,String cidade, 
			String bairro, String estado, String numero, String complemento, Empresa empresa,ResponsavelEmpresa responavelEmpresa) throws DadosException {

		this.setDescricao(descricao);	
		this.setQtd(qtd);
		this.setpSalario(pSalario);
		this.sethTrabalho(hTrabalho);
		this.setTipo(tipo);
		this.setDisponivel(disponivel);
		Calendar hoje = Calendar.getInstance();
		this.setArea(area);
		this.setCep(cep);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setEstado(estado);
		this.setNumero(numero);
		this.setComplemento(complemento);
		this.setData(hoje);
		this.setEmpresa(empresa);
		this.setResponsavel(responavelEmpresa);
		
		

	}
	

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public TipoArea getArea() {
		return area;
	}

	public void setArea(TipoArea area) {
		this.area = area;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public TipoVaga getTipo() {
		return tipo;
	}

	public void setTipo(TipoVaga tipo) {
		this.tipo = tipo;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ResponsavelEmpresa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(ResponsavelEmpresa responsavel) {
		this.responsavel = responsavel;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}



	public Double getpSalario() {
		return pSalario;
	}

	public void setpSalario(Double pSalario) {
		this.pSalario = pSalario;
	}

	public String gethTrabalho() {
		return hTrabalho;
	}

	public void sethTrabalho(String hTrabalho) {
		this.hTrabalho = hTrabalho;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		Vaga other = (Vaga) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
