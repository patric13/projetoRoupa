package model;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.MalformedURLException;
import java.net.URL;

import model.dao.IDados;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;


public class LocaldeAtuacao implements IDados, IDadosParaTabela, Comparable<LocaldeAtuacao>,
		Serializable {
	//
	// CONSTANTES
	//

	public static final int TAMANHO_ENDERECO = 20;
	public static final int TAMANHO_CEP = 8;


	//
	// ATRIBUTOS
	//

	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private int numero;
	private int cep;
	private String complemento;
	private List<Vaga> conjVaga = new ArrayList<Vaga>();
	

	//
	// MÉTODOS
	//

	public LocaldeAtuacao() {

	}

	/**
	 * @param cpf
	 * @param nome
	 * @param dt_nasc
	 * @param email
	 * @param estado
	 * @param cidade
	 * @param bairro
	 * @param rua
	 * @param numero
	 * @param status
	 * @param telefone
	 * @param celular
	 * @throws DadosException
	 * @throws IOException 
	 */
	public LocaldeAtuacao(
			String estado, String cidade, String bairro, String logradouro,
			int numero,int cep, String complemento)
			throws DadosException, IOException {

		this.setEstado(estado);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setLogradouro(logradouro);
		this.setNumero(numero);
		this.setCep(cep);
		this.setComplemento(complemento);
		this.conjVaga =  new ArrayList<Vaga>();
		
		
	}

	public List<Vaga> getVaga() {
	    return conjVaga;
	    }
	
	 public void addVaga(Vaga vaga) throws DadosException{
		  if(this.conjVaga.contains(vaga))
				return;
			this.conjVaga.add(vaga);
			vaga.setLocalAtuacao(this);
		}
	  
	  public void removeVaga(Vaga vaga) throws DadosException{
		  if(!this.conjVaga.contains(vaga))
				return;
			this.conjVaga.remove(vaga);
			vaga.setLocalAtuacao(null);
			}
	
	
	

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) throws DadosException {
		validarLogradouro(logradouro);
		this.logradouro = logradouro;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) throws DadosException {
		validarCep(cep);
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) throws DadosException {
		validarComplemento(complemento);
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) throws DadosException {
		validarBairro(bairro);
		this.bairro = bairro;
	}

	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) throws DadosException {
		validarEstado(estado);
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) throws DadosException {
		validarCidade(cidade);
		this.cidade = cidade;
	}



	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) throws DadosException {
		validarNumero(numero);
		this.numero = numero;
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
	/*
	public Object getChave() {
		return cpf;
	}
*/
	
	
	@RegraDeDominio
	public static void validarEstado(String estado) throws DadosException {
		if (estado == null || estado.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O estado não pode ser nulo!"));
		if (estado.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O estado não deve exceder a " + TAMANHO_ENDERECO
							+ " caracteres!"));
	}

	@RegraDeDominio
	public static void validarCidade(String cidade) throws DadosException {
		if (cidade == null || cidade.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"A cidade não pode ser nulo!"));
		if (cidade.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"A cidade não deve exceder a " + TAMANHO_ENDERECO
							+ " caracteres!"));
	}

	@RegraDeDominio
	public static void validarLogradouro(String logradouro) throws DadosException {
		if (logradouro == null || logradouro.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"A Logradouro não pode ser nulo!"));
		if (logradouro.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"A logradouro não deve exceder a " + TAMANHO_ENDERECO
							+ " caracteres!"));
	}
	
	@RegraDeDominio
	public static void validarComplemento(String complemento) throws DadosException {
		if (complemento == null || complemento.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O complemento não pode ser nulo!"));
		if (complemento.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O complemento não deve exceder a " + TAMANHO_ENDERECO
							+ " caracteres!"));
	}
	@RegraDeDominio
    public static void validarCep(int cep) throws DadosException
    {
		
			String a = Integer.toString(cep);
    

		if (a.equals("00000000000") || a.equals("11111111111")
				|| a.equals("22222222222") || a.equals("33333333333")
				|| a.equals("44444444444") || a.equals("55555555555")
				|| a.equals("66666666666") || a.equals("77777777777")
				|| a.equals("88888888888") || a.equals("99999999999")
				|| (a.length() != TAMANHO_CEP))
			throw new DadosException(new ErroDeDominio("O cep invialido"));

		
    }

	

	@RegraDeDominio
	public static void validarBairro(String bairro) throws DadosException {
		if (bairro == null || bairro.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O bairro não pode ser nulo!"));
		if (bairro.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O bairro não deve exceder a " + TAMANHO_ENDERECO
							+ " caracteres!"));
	}

	@RegraDeDominio
	public static void validarNumero(int numero) throws DadosException {
		if (numero <= 0)
			throw new DadosException(new ErroDeDominio(
					"O numero não pode ser nulo!"));
		if (numero > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O numero deve ser menor que" + TAMANHO_ENDERECO
							+ " caracteres!"));
	}

	
	
	/**
	 * Implementação do método toString que retorna uma String que descreve o
	 * objeto Empregado
	 */
	/*
	public String toString() {
		return this.cpf + "-" + this.nome;
	}
*/
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
	/*
	public int compareTo(LocaldeAtuacao d) {
		return this.nome.compareTo(d.nome);
	}
*/

	@Override
	public int compareTo(LocaldeAtuacao o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getChave() {
		// TODO Auto-generated method stub
		return null;
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