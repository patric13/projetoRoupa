package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.net.MalformedURLException;
import java.net.URL;
import model.dao.IDados;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;

public class Empresa implements IDados, IDadosParaTabela, Comparable<Empresa>,
		Serializable {
	//
	// CONSTANTES
	//
	public static final int TAMANHO_CNPJ = 14;
	public static final int TAMANHO_NOME = 40;
	public static final int TAMANHO_ENDERECO = 20;
	public static final int TAMANHO_CEP = 8;
	public static final int TAMANHO_NUMERO = 10000;

	public enum Status {
		ALOCADO, DESALOCADO, DESEMPREGADO;

		public static void validarTransicaoStatus(Status anterior, Status novo)
				throws DadosException {
			if (anterior == null && novo == DESALOCADO || anterior == ALOCADO
					&& novo == DESALOCADO || anterior == ALOCADO
					&& novo == DESEMPREGADO || anterior == DESALOCADO
					&& novo == ALOCADO || anterior == DESALOCADO
					&& novo == DESEMPREGADO)
				return;
			throw new DadosException(new ErroDeDominio(
					"Um empregado não pode deixar de ser "
							+ (anterior == null ? "NULO" : anterior)
							+ " e passar a ser " + novo));
		}
	}

	//
	// ATRIBUTOS
	//
	private String cnpj;
	private String nome;
	private String urlsite;

	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String complemento;
	private int cep;
	private int numero;
	private Status status;
	private boolean registroValidado;
	private List<ResponsavelEmpresa> conjResp = new ArrayList<ResponsavelEmpresa>();

	//
	// MÉTODOS
	//

	public Empresa() {

	}

	public Empresa(String cnpj, String nome, String urlsite, String estado,
			String cidade, String logradouro, int cep, String complemento,
			int numero, String bairro, boolean registroValidado)
			throws DadosException {
		super();
		this.setCnpj(cnpj);
		this.setNome(nome);
		this.setUrlsite(urlsite);
		this.setEstado(estado);
		this.setCidade(cidade);
		this.setLogradouro(logradouro);
		this.setCep(cep);
		this.setNumero(numero);
		this.setBairro(bairro);
		this.setRegistroValidado(registroValidado);
		this.conjResp = new ArrayList<ResponsavelEmpresa>();
		this.setComplemento(complemento);
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

	public List<ResponsavelEmpresa> getResponsavel() {
		return conjResp;
	}

	public void addResponsavel(ResponsavelEmpresa responsavel)
			throws DadosException {
		if (this.conjResp.contains(responsavel))
			return;
		this.conjResp.add(responsavel);
		responsavel.setEmpresa(this);
	}

	public void removeResponsavel(ResponsavelEmpresa responsavel)
			throws DadosException {
		if (!this.conjResp.contains(responsavel))
			return;
		this.conjResp.remove(responsavel);
		responsavel.setEmpresa(null);
	}
	
	public List<ResponsavelEmpresa> informarResponsaveis() throws DadosException {
		return new ArrayList<ResponsavelEmpresa>(this.conjResp);
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) throws DadosException {
		validarCnpj(cnpj);
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws DadosException {
		validarNome(nome);
		this.nome = nome;
	}

	public String getUrlsite() {
		return urlsite;
	}

	public void setUrlsite(String urlsite) {
		this.urlsite = urlsite;
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

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) throws DadosException {
		validarLogradouro(logradouro);
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) throws DadosException {
		validarNumero(numero);
		this.numero = numero;
	}

	public boolean getRegistroValidado() {
		return registroValidado;
	}

	public void setRegistroValidado(boolean registroValidado) {
		this.registroValidado = registroValidado;
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
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status novo) throws DadosException {
		Status.validarTransicaoStatus(this.status, novo);
		this.status = novo;
	}

	public Object getChave() {
		return cnpj;
	}

	@RegraDeDominio
	public static void validarCnpj(String cnpj) throws DadosException {
		if (cnpj == null || cnpj.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O CNPJ não pode ser nulo!"));
		for (int i = 0; i < cnpj.length(); i++)
			if (!Character.isDigit(cnpj.charAt(i)))
				throw new DadosException(new ErroDeDominio(
						"O CNPJ só deve conter numeros!"));
		if (cnpj.length() != TAMANHO_CNPJ)
			throw new DadosException(new ErroDeDominio("O CNPJ deve ter "
					+ TAMANHO_CNPJ + " digitos!"));

		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
				|| cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333")
				|| cnpj.equals("44444444444444")
				|| cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666")
				|| cnpj.equals("77777777777777")
				|| cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999"))
			throw new DadosException(new ErroDeDominio("O CNPJ invalido"));

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos
			// informados.
			if (!(dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
				throw new DadosException(new ErroDeDominio("O CNPJ invalido"));

		} catch (InputMismatchException erro) {
			throw new DadosException(new ErroDeDominio("O CNPJ invalido"));
		}
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
	public static void validarLogradouro(String logradouro)
			throws DadosException {
		if (logradouro == null || logradouro.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O logradouro não pode ser nulo!"));
		if (logradouro.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O logradouro não deve exceder a " + TAMANHO_ENDERECO
							+ " caracteres!"));
	}

	@RegraDeDominio
	public static void validarComplemento(String complemento)
			throws DadosException {
		if (complemento == null || complemento.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O complemento não pode ser nulo!"));
		if (complemento.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O complemento não deve exceder a " + TAMANHO_ENDERECO
							+ " caracteres!"));
	}

	@RegraDeDominio
	public static void validarURL(String url) throws DadosException,
			IOException {

		// to do ...
		try {
			URL url1 = new URL(url);
			

		} catch (MalformedURLException e) {
			throw new DadosException(new ErroDeDominio("URL nâo existe!"));

		}

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
		if (numero > TAMANHO_NUMERO)
			throw new DadosException(new ErroDeDominio(
					"O numero deve ser menor que" + TAMANHO_NUMERO
							+ " caracteres!"));
	}

	@RegraDeDominio
	public static void validarCep(int cep) throws DadosException {

		String a = Integer.toString(cep);

		if (a.equals("00000000000") || a.equals("11111111111")
				|| a.equals("22222222222") || a.equals("33333333333")
				|| a.equals("44444444444") || a.equals("55555555555")
				|| a.equals("66666666666") || a.equals("77777777777")
				|| a.equals("88888888888") || a.equals("99999999999")
				|| (a.length() != TAMANHO_CEP))
			throw new DadosException(new ErroDeDominio("O cep invialido"));

	}

	/**
	 * Implementação do método toString que retorna uma String que descreve o
	 * objeto Empregado
	 */
	public String toString() {
		return this.cnpj + "-" + this.nome;
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
	public int compareTo(Empresa d) {
		return this.nome.compareTo(d.nome);
	}

	@Override
	public String[] getCamposDeTabela() {
		return new String[] { "CNPJ", "Nome", "Validado", "#Responsáveis" };
	}

	@Override
	public Object[] getDadosParaTabela() {
		return new Object[] { this.cnpj, this.nome, this.registroValidado,
				this.conjResp.size() };
	}
}