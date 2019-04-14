package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.dao.IDados;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;

public class Cargo implements IDados, IDadosParaTabela, Comparable<Cargo>,
		Serializable {
	//
	// CONSTANTES
	//

	public static final int TAMANHO_NOME = 40;

	//

	private String nome;
	private List<Vaga> conjVaga = new ArrayList<Vaga>();
	// to do..
	private List<Cargo> conjCargoSemelhantes = new ArrayList<Cargo>();

	//
	// MÉTODOS
	//

	public Cargo() {

	}

	public Cargo(String nome) throws DadosException, IOException {
		this.setNome(nome);
		this.conjVaga = new ArrayList<Vaga>();
		this.conjCargoSemelhantes = new ArrayList<Cargo>();
	}

	public List<Vaga> getVaga() {
		return conjVaga;
	}

	public void addVaga(Vaga vaga) throws DadosException {
		if (this.conjVaga.contains(vaga))
			return;
		this.conjVaga.add(vaga);
		vaga.setCargo(this);
	}

	public void removerVaga(Vaga vaga) throws DadosException {
		if (!this.conjVaga.contains(vaga))
			return;
		this.conjVaga.remove(vaga);
		vaga.setCargo(null);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws DadosException {
		validarNome(nome);
		this.nome = nome;
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
		return nome;
	}

	@RegraDeDominio
	public static void validarNome(String nome) throws DadosException {
		if (nome == null || nome.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O Nome não pode ser nulo!"));
		if (nome.length() > 40)
			throw new DadosException(new ErroDeDominio(
					"O Nome não deve exceder a " + TAMANHO_NOME
							+ " caracteres!"));
	}

	@Override
	public Object[] getDadosParaTabela() {
		return new Object[] { this.nome, this.getNome() };
	}

	@Override
	public int compareTo(Cargo o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getCamposDeTabela() {
		// TODO Auto-generated method stub
		return null;
	}
}