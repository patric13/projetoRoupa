package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.dao.IDados;
import model.util.DadosException;
import model.util.ErroDeDominio;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;

public class Projeto implements IDados, IDadosParaTabela, Comparable<Projeto>, Serializable {
	//
	// CONSTANTES
	//
	public static final int TAMANHO_NOME = 40;
	
	
	public enum Status {
		EM_FUNCIONAMENTO, PARADO, DESCONTINUADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws DadosException {
			if(anterior == null && novo == EM_FUNCIONAMENTO || 
			   anterior == EM_FUNCIONAMENTO && novo == PARADO || 
			   anterior == EM_FUNCIONAMENTO && novo == DESCONTINUADO ||
			   anterior == PARADO && novo == EM_FUNCIONAMENTO ||
			   anterior == PARADO && novo == DESCONTINUADO)
				return;
			throw new DadosException(new ErroDeDominio("Um empregado não pode deixar de ser " + (anterior==null?"NULO":anterior) + " e passar a ser " + novo));
		}
	}
	
	//
	// ATRIBUTOS
	//
	private String nome;
	private Departamento depto;
	private Set<Empregado> conjEmpregados;
	private Status status;
	
	//
	// MÉTODOS
	//
	public Projeto() {
	}
	
	public Projeto(String nome, Departamento d, List<Empregado> conjEmpregados) throws DadosException {
		super();
		this.setNome(nome);
		this.setDepto(d);
		this.conjEmpregados = new TreeSet<Empregado>();
		for(Empregado e : conjEmpregados)
			this.adicionarEmpregado(e);
		this.setStatus(Status.EM_FUNCIONAMENTO);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws DadosException {
		validarNome(nome);
		this.nome = nome;
	}

	public Departamento getDepto() {
		return depto;
	}

	public void setDepto(Departamento depto) throws DadosException {		
		if(this.depto == depto)
			return;
		if(depto == null) {
			Departamento antigo = this.depto; 
			this.depto = null;
			antigo.removerProjeto(this);
			this.setStatus(Status.DESCONTINUADO);
		}
		else {
			if(this.depto != null)
				this.depto.removerProjeto(this);
			this.depto = depto;
			this.depto.adicionarProjeto(this);							
		}
	}
	
	public Set<Empregado> getConjEmpregados() {
		return new HashSet<Empregado>(this.conjEmpregados);
	}
	
	public void adicionarEmpregado(Empregado novo) throws DadosException {
		Projeto.validarEmpregado(novo);
		if(!this.conjEmpregados.contains(novo)) {
			this.conjEmpregados.add(novo);
			novo.adicionarProjeto(this);
		}
	}

	public void adicionarTodosEmpregados(Collection<Empregado> colecao) throws DadosException {
		for(Empregado e : colecao)
			this.adicionarEmpregado(e);
	}

	public void removerEmpregado(Empregado ex) throws DadosException {
		Projeto.validarEmpregado(ex);
		if(this.conjEmpregados.contains(ex)) {
			this.conjEmpregados.remove(ex);
			ex.removerProjeto(this);
		}
	}

	public void removerTodosEmpregados() throws DadosException {
		Collection<Empregado> antigos = new HashSet<Empregado>(this.conjEmpregados);
		for(Empregado e : antigos)
			this.removerEmpregado(e);
	}

	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status novo) throws DadosException {
		Status.validarTransicaoStatus(this.status,novo);
		this.status = novo;
	}

	public Object getChave() {
		return nome;
	}

	@RegraDeDominio
	public static void validarNome(String nome) throws DadosException {
		if(nome == null || nome.length() == 0) 
			throw new DadosException(new ErroDeDominio("O Nome não pode ser nulo!"));
		if(nome.length() > 40)
			throw new DadosException(new ErroDeDominio("O Nome não deve exceder a " + TAMANHO_NOME + " caracteres!"));		
	}

	@RegraDeDominio
	public static void validarDepto(Departamento depto) throws DadosException {
		if(depto == null)
			throw new DadosException(new ErroDeDominio("Departamento não informado"));		
	}

	@RegraDeDominio
	public static void validarEmpregado(Empregado emp) throws DadosException {
		if(emp == null)
			throw new DadosException(new ErroDeDominio("Empregado não informado"));		
	}

	/**
	 * Implementação do método toString que retorna uma String que descreve o
	 * objeto Empregado
	 */
	public String toString() {
		return this.nome + "-" + this.status + "-" + this.depto.getNome() + "-" + this.conjEmpregados;
	}

	
	/**
	 * Retorna um array de Strings com os nomes dos campos para apresentação numa
	 * tabela de interface 
	 * dos objetos
	 * @return array de string com os campos da tabela de interface
	 */
	public String[] getCamposDeTabela() {
		return new String[] {"Nome", "Depto Responsável", "Status", "#Empregados"};
	}
	
	/**
	 * Retorna um array de Objects com os estados dos campos do ITabelavel para 
	 * apresentação numa tabela 
	 * @return array de objects com valores para tabela
	 */
	public Object[] getDadosParaTabela() {
		return new Object[] { this.nome, this.depto != null? this.depto.getNome() : "-", this.status, this.conjEmpregados.size() };
	}

	/**
	 * Método utilizado para colocar os empregados em ordem
	 */
	public int compareTo(Projeto d) {
		return this.nome.compareTo(d.nome);
	}
}