package model;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dominio.ContaDeAcesso;

import java.net.MalformedURLException;
import java.net.URL;

import model.dao.IDados;
import model.util.IDadosParaTabela;
import model.util.RegraDeDominio;


public class Pessoa implements IDados, IDadosParaTabela, Comparable<Pessoa>,
		Serializable {
	//
	// CONSTANTES
	//
	public static final int TAMANHO_CPF = 11;
	public static final int TAMANHO_NOME = 40;
	public static final int TAMANHO_ENDERECO = 20;
	public static final int TAMANHO_NUMERO = 10000;
	public static final int TAMANHO_TELEFONE = 8;
	public static final int TAMANHO_CELULAR = 9;
	public static final int TAMANHO_CEP = 8;



	//
	// ATRIBUTOS
	//
	private String cpf;
	private String nome;
	private String dt_nasc;
	private String email;
	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String complemento;
	private int numero;
	private int telefone;
	private int celular;
	private ContaDeAcesso conta;
	private int cep;

	//
	// MÉTODOS
	//

	public Pessoa() {

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
	public Pessoa(String cpf, String nome, String dt_nasc, String email,
			String estado, String cidade, String bairro,int cep, String logradouro,String complemento,
			int numero, int telefone, int celular, ContaDeAcesso conta)
			throws DadosException, IOException {
		super();
		this.setCpf(cpf);
		this.setNome(nome);
		this.setDt_nasc(dt_nasc);
		this.setEmail(email);
		this.setEstado(estado);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setLogradouro(logradouro);
		this.setComplemento(complemento);
		this.setNumero(numero);
		this.setTelefone(telefone);
		this.setCelular(celular);
		this.setConta(conta);
		this.setCep(cep);
	}
	
	
	

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) throws DadosException {
		validarComplemento(complemento);
		complemento = complemento;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) throws DadosException {
		validarCep(cep);
		this.cep = cep;
	}

	public String getCpf() {
		return cpf;
	}
	
	

	public ContaDeAcesso getConta() {
		return conta;
	}

	public void setConta(ContaDeAcesso conta) {
		this.conta = conta;
	}

	public void setCpf(String cpf) throws DadosException {
		validarCpf(cpf);
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setNome(String nome) throws DadosException {
		validarNome(nome);
		this.nome = nome;
	}

	public String getDt_nasc() {
		return dt_nasc;
	}

	public void setDt_nasc(String dt_nasc) throws DadosException, IOException {
		validarData(dt_nasc);
		this.dt_nasc = dt_nasc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws DadosException {
		validarEmail(email);
		this.email = email;
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

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) throws DadosException {
		validarTelefone(telefone);
		this.telefone = telefone;
	}

	public int getCelular() {
		return celular;
	}

	public void setCelular(int celular) throws DadosException {
		validarCelular(celular);
		this.celular = celular;
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
		return cpf;
	}

	@RegraDeDominio
	public static void validarCpf(String cpf) throws DadosException {
		if (cpf == null || cpf.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O CPF não pode ser nulo!"));
		for (int i = 0; i < cpf.length(); i++)
			if (!Character.isDigit(cpf.charAt(i)))
				throw new DadosException(new ErroDeDominio(
						"O CPF só deve digitos!"));
		if (cpf.length() != TAMANHO_CPF)
			throw new DadosException(new ErroDeDominio("O CPF deve ter "
					+ TAMANHO_CPF + " digitos!"));

		if (cpf.equals("00000000000") || cpf.equals("11111111111")
				|| cpf.equals("22222222222") || cpf.equals("33333333333")
				|| cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777")
				|| cpf.equals("88888888888") || cpf.equals("99999999999")
				|| (cpf.length() != 11))
			throw new DadosException(new ErroDeDominio("O CPF invialido"));

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere
											// numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if (!(dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				throw new DadosException(new ErroDeDominio("O CPF invialido"));

		} catch (InputMismatchException erro) {
			throw new DadosException(new ErroDeDominio("O CPF invialido"));
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
	public static void validarLogradouro(String logradouro) throws DadosException {
		if (logradouro == null || logradouro.length() == 0)
			throw new DadosException(new ErroDeDominio(
					"O logradouro não pode ser nulo!"));
		if (logradouro.length() > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O logradouro não deve exceder a " + TAMANHO_ENDERECO
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
	public static void validarData(String data) throws DadosException,
			IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);

		try {
			Date data1 = sdf.parse(data);
			// se passou pra cá, é porque a data é válida
		} catch (ParseException e) {
			// se cair aqui, a data é inválida
			throw new DadosException(new ErroDeDominio(
					"Data esta no formato errado"));
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
		if (numero > TAMANHO_ENDERECO)
			throw new DadosException(new ErroDeDominio(
					"O numero deve ser menor que" + TAMANHO_ENDERECO
							+ " caracteres!"));
	}

	@RegraDeDominio
	public static void validarEmail(String email) throws DadosException {
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				throw new DadosException(new ErroDeDominio("Email Invalido"));
			} else
				throw new DadosException(new ErroDeDominio("Email Invalido"));

		}
	}

	@RegraDeDominio
	public static void validarTelefone(int telefone) throws DadosException {

		
		String a = Integer.toString(telefone);
		if (telefone <= 0)
			throw new DadosException(new ErroDeDominio(
					"O numero não pode ser nulo!"));
		if (a.length() > TAMANHO_TELEFONE)
			throw new DadosException(new ErroDeDominio(
					"O numero deve ser menor que" + TAMANHO_TELEFONE
							+ " caracteres!"));
		if (a.length() < TAMANHO_TELEFONE)
			throw new DadosException(new ErroDeDominio("O numero deve ter"
					+ TAMANHO_TELEFONE + " caracteres!"));
	}

	@RegraDeDominio
	public static void validarCelular(int celular) throws DadosException {

		
		String a = Integer.toString(celular);
		if (celular <= 0)
			throw new DadosException(new ErroDeDominio(
					"O numero não pode ser nulo!"));
		if (a.length() > TAMANHO_CELULAR)
			throw new DadosException(new ErroDeDominio(
					"O numero deve ser menor que" + TAMANHO_CELULAR
							+ " caracteres!"));
		if (a.length() < TAMANHO_CELULAR)
			throw new DadosException(new ErroDeDominio("O numero deve ter"
					+ TAMANHO_CELULAR + " caracteres!"));
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
	
	
	
	
	
	
	
	/**
	 * Implementação do método toString que retorna uma String que descreve o
	 * objeto Empregado
	 */
	public String toString() {
		return this.cpf + "-" + this.nome;
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
	public int compareTo(Pessoa d) {
		return this.nome.compareTo(d.nome);
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