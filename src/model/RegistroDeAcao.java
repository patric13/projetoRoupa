package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.TreeSet;

import dominio.ContaDeAcesso;

import java.net.MalformedURLException;
import java.net.URL;

import model.dao.IDados;
import model.util.IDadosParaTabela;

public class RegistroDeAcao implements IDados, IDadosParaTabela,
		Comparable<RegistroDeAcao>, Serializable {
	//
	// CONSTANTES
	//

	//
	// ATRIBUTOS
	//
	private String tipo;
	private Calendar data;
	private ContaDeAcesso conta;

	//
	// MÉTODOS
	//

	public RegistroDeAcao() {
		super();
	}

	public RegistroDeAcao(String tipo, ContaDeAcesso conta) throws DadosException {
		super();
		this.setTipo(tipo);
		Calendar hoje = Calendar.getInstance();
		this.setDate(hoje);
		this.setConta(conta);
		
	}

	
	
	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public ContaDeAcesso getConta() {
		return conta;
	}

	public void setConta(ContaDeAcesso conta) {
		this.conta = conta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) throws DadosException {
		this.tipo = tipo;
	}

	public Calendar getDate() {
		return data;
	}

	public void setDate(Calendar data) {
		this.data = data;
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
		return data;
	}

	/**
	 * Implementação do método toString que retorna uma String que descreve o
	 * objeto Empregado
	 */
	public String toString() {
		return this.data + "-" + this.tipo;
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
	public int compareTo(RegistroDeAcao d) {
		return this.data.compareTo(d.data);
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