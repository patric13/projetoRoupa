package dominio;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.net.MalformedURLException;
import java.net.URL;

import model.DadosException;
import model.ErroDeDominio;
import model.Pessoa;
import model.RegistroDeAcao;
import model.dao.IDados;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;

public class ContaDeAcesso implements IDados,IDadosParaTabela, Comparable<ContaDeAcesso>,
		Serializable {
	//
	// CONSTANTES
	//
	public static final int TAMANHO_LOGIN = 10;
	public static final int TAMANHO_SENHA = 18;

	//
	// ATRIBUTOS
	//
	private String login;
	private String senha;
	private Calendar dataCriacao;
	private Pessoa pessoa;
	private List<RegistroDeAcao> conjRegistro= new ArrayList<RegistroDeAcao>();

	//
	// MÉTODOS
	//

	public ContaDeAcesso() {
		super();
	}

	public ContaDeAcesso(String login, String senha,Pessoa pessoa) throws DadosException {
		super();
		this.setLogin(login);
		this.setSenha(senha);
		Calendar hoje = Calendar.getInstance();
		this.setDataCriacao(hoje);
		this.setPessoa(pessoa);
		this.conjRegistro = new ArrayList<RegistroDeAcao>();

	}

	public List<RegistroDeAcao> getRegistroDeAcao() {
	    return conjRegistro;
	    }
	
	

	 public void addResponsavel(RegistroDeAcao registro) throws DadosException{
		  if(this.conjRegistro.contains(registro))
				return;
			this.conjRegistro.add(registro);
			registro.setConta(this);
		}
	  
	  public void removeResponsavel(RegistroDeAcao registro) throws DadosException{
		  if(!this.conjRegistro.contains(registro))
				return;
			this.conjRegistro.remove(registro);
			registro.setConta(null);
			}
	
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) throws DadosException {
		validarLogin(senha);
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws DadosException {
		validarSenha(senha);
		this.senha = senha;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar hoje) {
		this.dataCriacao = hoje;
	}

	/**
	 * public void setDepto(Departamento depto) throws DadosException {
	 * if(this.depto == depto) return; if(depto == null) {
	 * this.setStatus(Status.DESALOCADO); Departamento antigo = this.depto;
	 * this.depto = null; antigo.removerEmpregado(this); } else {
	 * this.setStatus(Status.ALOCADO); if(this.depto != null)
	 * this.depto.removerEmpregado(this); this.depto = depto;
	 * this.depto.adicionarEmpregado(this); } }
	 * 
	 * 
	 * 
	 * public void adicionarProjeto(Projeto novo) throws DadosException {
	 * Empregado.validarProjeto(novo); if(!this.conjProjetos.contains(novo)) {
	 * this.conjProjetos.add(novo); novo.adicionarEmpregado(this); } }
	 * 
	 * public void removerProjeto(Projeto ex) throws DadosException {
	 * Empregado.validarProjeto(ex); if(this.conjProjetos.contains(ex)) {
	 * this.conjProjetos.remove(ex); ex.removerEmpregado(this); } }
	 **/

	public Object getChave() {
		return login;
	}

	@RegraDeDominio
	public static void validarLogin(String nome) throws DadosException {
		if (nome == null || nome.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O Nome não pode ser nulo!"));
		if (nome.length() > TAMANHO_LOGIN)
			throw new DadosException(new ErroDeDominio(
					"O Nome não deve exceder a " + TAMANHO_LOGIN
							+ " caracteres!"));
	}

	public static void validarSenha(String senha) throws DadosException {
		if (senha == null || senha.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O Nome não pode ser nulo!"));
		if (senha.length() > TAMANHO_SENHA)
			throw new DadosException(new ErroDeDominio(
					"O Nome não deve exceder a " + TAMANHO_SENHA
							+ " caracteres!"));
	}

	/**
	 * Implementação do método toString que retorna uma String que descreve o
	 * objeto Empregado
	 */
	public String toString() {
		return this.login + "-" + this.senha;
	}

	/**
	 * Retorna um array de Objects com os estados dos atributos dos objetos
	 * 
	 * @return
	 * 
	 *         public Object[] getData() { String nomeDepto = this.depto == null
	 *         ? "Não Alocado" : this.depto.getNome(); return new Object[] {
	 *         this.cpf, this.nome, nomeDepto}; }
	 */
	/**
	 * Método utilizado para colocar os empregados em ordem
	 */
	public int compareTo(ContaDeAcesso d) {
		return this.login.compareTo(d.login);
	}



	@Override
	public String[] getCamposDeTabela() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getDadosParaTabela() {
		// TODO Auto-generated method stub
		return null;
	}
}