package model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import model.dao.IDados;
import model.util.DadosException;
import model.util.ErroDeDominio;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;

/**
 * Implementa��o da classe Departamento 
 * @author Alessandro Cerqueira
 *
 */
public class Departamento implements IDados, IDadosParaTabela, Comparable<Departamento>, Serializable {	
	//
	// CONSTANTES
	//
	public static final int TAMANHO_SIGLA = 2;
	public static final int TAMANHO_NOME = 40;
	
	//
	// ATRIBUTOS
	//
	private String sigla;
	private String nome;
	private Set<Empregado> conjEmpregados;
	private Set<Projeto> conjProjetos;
	
	//
	// M�TODOS
	//
	/**
	 * Construtor com assinatura vazia
	 */
	public Departamento() {	
	}
	
	/**
	 * Construtor 
	 * @param sigla
	 * @param nome
	 * @throws DadosException
	 */
	public Departamento(String sigla, String nome) throws DadosException {
		super();
		this.setSigla(sigla);
		this.setNome(nome);
		this.conjEmpregados = new TreeSet<Empregado>();
		this.conjProjetos = new TreeSet<Projeto>();
	}

	/**
	 * Retorna a sigla do Departamento
	 * @return
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * Altera a sigla do Departamento
	 * @param sigla
	 * @throws DadosException
	 */
	public void setSigla(String sigla) throws DadosException {
		Departamento.validarSigla(sigla);
		this.sigla = sigla;
	}
	
	/**
	 * Retorna o nome do Departamento
	 * @return
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Altera o nome do Departamento
	 * @param nome
	 * @throws DadosException
	 */
	public void setNome(String nome) throws DadosException {
		Departamento.validarNome(nome);
		this.nome = nome;
	}
	
	/**
	 * M�todo para adicionar um empregado ao conjunto de empregados do Departamento
	 * @param novo
	 * @throws DadosException
	 */
	public void adicionarEmpregado(Empregado novo) throws DadosException {
		Departamento.validarEmpregado(novo);
		if(this.conjEmpregados.contains(novo))
			return;
		this.conjEmpregados.add(novo);
		novo.setDepto(this);
	}

	/**
	 * M�todo para remover um empregado do conjunto de empregados do Departamento
	 * @param ex
	 * @throws DadosException
	 */
	public void removerEmpregado(Empregado ex) throws DadosException {
		Departamento.validarEmpregado(ex);
		if(!this.conjEmpregados.contains(ex))
			return;
		this.conjEmpregados.remove(ex);
		ex.setDepto(null);
	}

	/**
	 * M�todo para informar todos os empregados do Departamento
	 * @throws DadosException
	 */
	public Set<Empregado> informarEmpregados() throws DadosException {
		return new TreeSet<Empregado>(this.conjEmpregados);
	}

	/**
	 * M�todo para adicionar um projeto ao conjunto de projetos do Departamento
	 * @param novo
	 * @throws DadosException
	 */
	public void adicionarProjeto(Projeto novo) throws DadosException {
		Departamento.validarProjeto(novo);
		if(this.conjProjetos.contains(novo))
			return;
		this.conjProjetos.add(novo);
		novo.setDepto(this);
	}

	/**
	 * M�todo para remover um projeto do conjunto de projetos do Departamento
	 * @param ex
	 * @throws DadosException
	 */
	public boolean removerProjeto(Projeto ex) throws DadosException {
		Departamento.validarProjeto(ex);
		if(!this.conjProjetos.contains(ex))
			return false;
		this.conjProjetos.remove(ex);
		ex.setDepto(null);
		return true;	
	}

	/**
	 * Regra de valida��o das siglas dos departamentos
	 * @param sigla
	 * @throws DadosException
	 */
	@RegraDeDominio
	public static void validarSigla(String sigla) throws DadosException {
		if(sigla == null) 
			throw new DadosException(new ErroDeDominio("A Sigla n�o pode ser nula!"));
		if(sigla.length() != TAMANHO_SIGLA)
			throw new DadosException(new ErroDeDominio("A Sigla obrigatoriamente deve apresentar dois caracteres!"));
		if(!Character.isUpperCase(sigla.charAt(0)) || !Character.isUpperCase(sigla.charAt(1)))
			throw new DadosException(new ErroDeDominio("A Sigla deve ser composta de letras mai�sculas"));
	}

	/**
	 * Regra de valida��o dos nomes dos departamentos
	 * @param nome
	 * @throws DadosException
	 */
	@RegraDeDominio
	public static void validarNome(String nome) throws DadosException {
		if(nome == null || nome.length() == 0) 
			throw new DadosException(new ErroDeDominio("O Nome n�o pode ser nulo!"));
		if(nome.length() > TAMANHO_NOME)
			throw new DadosException(new ErroDeDominio("O Nome n�o deve exceder a " + TAMANHO_NOME + " caracteres!"));		
	}

	/**
	 * Regra para valida��o dos projetos do departamento
	 * @param proj
	 * @throws DadosException
	 */
	@RegraDeDominio
	public static void validarProjeto(Projeto proj) throws DadosException {
		if(proj == null) 
			throw new DadosException(new ErroDeDominio("O projeto n�o pode ser nulo!"));
	}

	/**
	 * Regra para valida��o dos empregados do departamento
	 * @param emp
	 * @throws DadosException
	 */
	@RegraDeDominio
	public static void validarEmpregado(Empregado emp) throws DadosException {
		if(emp == null) 
			throw new DadosException(new ErroDeDominio("O empregado n�o pode ser nulo!"));
	}

	/** 
	 * Implementa��o do m�todo toString que retorna uma String
	 * que descreve o objeto Departamento
	 */
	public String toString() {
		return this.sigla + "-" + this.nome;
	}

	/**
	 * M�todo chamado pelos DAOs para recupera��o de um objeto pela chave
	 */
	public Object getChave() {
		return sigla;
	}

	/**
	 * Retorna um array de Strings com os nomes dos campos para apresenta��o numa
	 * tabela de interface 
	 * dos objetos
	 * @return array de string com os campos da tabela de interface
	 */
	public String[] getCamposDeTabela() {
		return new String[]{"Sigla", "Nome", "#Empregados"};
	}
	
	/**
	 * Retorna um array de Objects com os estados dos campos do ITabelavel para 
	 * apresenta��o numa tabela 
	 * @return array de objects com valores para tabela
	 */
	public Object[] getDadosParaTabela() {
		return new Object[]{this.sigla, this.nome, this.conjEmpregados.size()};
	}
	
	/**
	 * M�todo utilizado para colocar os departamentos em ordem 
	 */
	public int compareTo(Departamento d) {
		return this.nome.compareTo(d.nome);
	}
}
