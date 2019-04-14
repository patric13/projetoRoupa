package model;

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

import model.dao.IDados;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;

public class Vaga implements IDados, IDadosParaTabela, Comparable<Vaga>,
		Serializable {
	//
	// CONSTANTES
	//
	public static final int TAMANHO_LOGIN = 10;
	public static final int TAMANHO_SENHA = 18;

	//
	// ATRIBUTOS
	//
	private String descricao;
	private boolean ehTemporario;
	private TipoVaga tipo;
	private int qtd;
	private boolean pSalario;
	private String hTrabalho;
	private Empresa empresa;
	private LocaldeAtuacao localAtuacao;
	private TipoArea area;
	private Cargo cargo;
	private ResponsavelEmpresa responsavel;
	//
	// MÉTODOS
	//

	public Vaga() {
		super();
	}

	public Vaga(String descricao, boolean ehTemporaria, TipoVaga tipo, int qtd,
			boolean pSalario, String hTrabalho, Empresa empresa,
			LocaldeAtuacao localAtuacao, TipoArea area, Cargo cargo, ResponsavelEmpresa responsavel) throws DadosException {

		this.setDescricao(descricao);
		this.setEhTemporario(ehTemporaria);
		this.setTipo(tipo);
		this.setQtd(qtd);
		this.setpSalario(pSalario);
		this.sethTrabalho(hTrabalho);
		this.setTipo(tipo);
		this.setEmpresa(empresa);
		this.setLocalAtuacao(localAtuacao);
		this.setArea(area);
		this.setCargo(cargo);
		this.setResponsavel(responsavel);

	}
	
	
	

	public ResponsavelEmpresa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(ResponsavelEmpresa responsavel) {
		this.responsavel = responsavel;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public TipoArea getArea() {
		return area;
	}

	public void setArea(TipoArea area) {
		this.area = area;
	}

	public LocaldeAtuacao getLocalAtuacao() {
		return localAtuacao;
	}

	public void setLocalAtuacao(LocaldeAtuacao localAtuacao) {
		this.localAtuacao = localAtuacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isEhTemporario() {
		return ehTemporario;
	}

	public void setEhTemporario(boolean ehTemporario) {
		this.ehTemporario = ehTemporario;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public boolean ispSalario() {
		return pSalario;
	}

	public void setpSalario(boolean pSalario) {
		this.pSalario = pSalario;
	}

	public String gethTrabalho() {
		return hTrabalho;
	}

	public void sethTrabalho(String hTrabalho) {
		this.hTrabalho = hTrabalho;
	}

	public TipoVaga getTipo() {
		return tipo;
	}

	public void setTipo(TipoVaga tipo) {
		this.tipo = tipo;
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
		return this.descricao + "-" + this.descricao;
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
	public int compareTo(Vaga d) {
		return this.descricao.compareTo(d.descricao);
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

	@Override
	public Object getChave() {
		// TODO Auto-generated method stub
		return null;
	}
}