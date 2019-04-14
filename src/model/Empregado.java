package model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import model.dao.IDados;
import model.util.DadosException;
import model.util.ErroDeDominio;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;

public class Empregado implements IDados, IDadosParaTabela, Comparable<Empregado>, Serializable {
	//
	// CONSTANTES
	//
	public static final int TAMANHO_CPF = 11;
	public static final int TAMANHO_NOME = 40;
	
	
	public enum Status {
		ALOCADO, DESALOCADO, DESEMPREGADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws DadosException {
			if(anterior == null && novo == ALOCADO || 
			   anterior == ALOCADO && novo == ALOCADO ||
			   anterior == ALOCADO && novo == DESALOCADO || 
			   anterior == ALOCADO && novo == DESEMPREGADO ||
			   anterior == DESALOCADO && novo == ALOCADO ||
			   anterior == DESALOCADO && novo == DESEMPREGADO)
				return;
			throw new DadosException(new ErroDeDominio("Um empregado não pode deixar de ser " + (anterior==null?"NULO":anterior) + " e passar a ser " + novo));
		}
	}
	
	//
	// ATRIBUTOS
	//
	private String cpf;
	private int	   matrFuncional;
	private String nome;
	private Status status;
	private Departamento depto;
	private Set<Projeto> conjProjetos;	

	//
	// MÉTODOS
	//
	public Empregado() {		
	}
	
	public Empregado(String cpf, int matrFuncional, String nome, Departamento d) throws DadosException {
		super();
		this.setCpf(cpf);
		this.setMatrFuncional(matrFuncional);
		this.setNome(nome);
		this.setDepto(d);
		this.conjProjetos = new TreeSet<Projeto>();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) throws DadosException {
		validarCpf(cpf);
		this.cpf = cpf;
	}

	public int getMatrFuncional() {
		return matrFuncional;
	}

	public void setMatrFuncional(int matrFuncional) throws DadosException {
		validarMatrFuncional(matrFuncional);
		this.matrFuncional = matrFuncional;
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
		if(this.depto == depto && this.getStatus() != null)
			return;
		if(depto == null) {
			this.setStatus(Status.DESALOCADO);
			Departamento antigo = this.depto; 
			this.depto = null;
			antigo.removerEmpregado(this);
		}
		else {
			this.setStatus(Status.ALOCADO);
			if(this.depto != null)
				this.depto.removerEmpregado(this);
			this.depto = depto;
			this.depto.adicionarEmpregado(this);							
		}
	}

	public void adicionarProjeto(Projeto novo) throws DadosException {
		Empregado.validarProjeto(novo);
		if(!this.conjProjetos.contains(novo)) {
			this.conjProjetos.add(novo);
			novo.adicionarEmpregado(this);
		}
	}

	public void removerProjeto(Projeto ex) throws DadosException {
		Empregado.validarProjeto(ex);
		if(this.conjProjetos.contains(ex)) {
			this.conjProjetos.remove(ex);
			ex.removerEmpregado(this);
		}
	}

	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status novo) throws DadosException {
		Status.validarTransicaoStatus(this.status,novo);
		this.status = novo;
	}

	public Object getChave() {
		return cpf;
	}

	@RegraDeDominio
	public static void validarCpf(String cpf) throws DadosException {
		if(cpf == null || cpf.length() == 0) 
			throw new DadosException(new ErroDeDominio("O CPF não pode ser nulo!"));
		for(int i = 0; i < cpf.length(); i++)
			if(!Character.isDigit(cpf.charAt(i)))
				throw new DadosException(new ErroDeDominio("O CPF só deve digitos!"));		
		if(cpf.length() != TAMANHO_CPF)
			throw new DadosException(new ErroDeDominio("O CPF deve ter " + TAMANHO_CPF + " digitos!"));		
	}

	@RegraDeDominio
	public static void validarMatrFuncional(int matrFuncional) throws DadosException {
		if(matrFuncional <= 0 || matrFuncional > 10000) 
			throw new DadosException(new ErroDeDominio("Valor inválido para a Matrícula Funcional (entre 1 e 9999) !"));
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
		// Não há regras de validação 
	}

	@RegraDeDominio
	public static void validarProjeto(Projeto proj) throws DadosException {
		if(proj == null) 
			throw new DadosException(new ErroDeDominio("O projeto não pode ser nulo!"));
	}

	/**
	 * Implementação do método toString que retorna uma String que descreve o
	 * objeto Empregado
	 */
	public String toString() {
		return this.cpf + "-" + this.nome;
	}

	/**
	 * Retorna um array de Strings com os nomes dos campos para apresentação numa
	 * tabela de interface 
	 * dos objetos
	 * @return array de string com os campos da tabela de interface
	 */
	public String[] getCamposDeTabela() {
		return new String[] {"CPF", "Matrícula", "Nome", "Departamento"};
	}
	
	/**
	 * Retorna um array de Objects com os estados dos campos do ITabelavel para 
	 * apresentação numa tabela 
	 * @return array de objects com valores para tabela
	 */
	public Object[] getDadosParaTabela() {
		String nomeDepto = this.depto == null ? "Não Alocado" : this.depto.getNome(); 
		return new Object[] { this.cpf, this.matrFuncional, this.nome, nomeDepto};
	}

	/**
	 * Método utilizado para colocar os empregados em ordem
	 */
	public int compareTo(Empregado d) {
		return this.nome.compareTo(d.nome);
	}
}