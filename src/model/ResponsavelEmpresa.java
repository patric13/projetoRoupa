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

import dominio.ContaDeAcesso;

import java.net.MalformedURLException;
import java.net.URL;

import model.util.RegraDeDominio;

public class ResponsavelEmpresa extends Pessoa {
	//
	// CONSTANTES
	//

	//
	// ATRIBUTOS
	//
	private boolean master;
	private Empresa empresa;
	private List<Vaga> conjVagas = new ArrayList<Vaga>();
	//
	// MÉTODOS
	//

	public ResponsavelEmpresa() {
		super();
	}

	public ResponsavelEmpresa(boolean master, String cpf, String nome, String dt_nasc, String email, String estado,
			String cidade, String bairro,int cep, String logradouro,String complemento, int numero, int telefone, int celular, Empresa empresa,ContaDeAcesso conta	)
			throws DadosException, IOException {
		super(cpf, nome, dt_nasc, email, estado, cidade, bairro,cep, logradouro,complemento, numero, telefone, celular,conta);
		this.setMaster(master);
		this.setEmpresa(empresa);
		this.conjVagas = new ArrayList<Vaga>();
	}
	
	
	
	
	public List<Vaga> getVaga() {
	    return conjVagas;
	    }
	
	 public void addVaga(Vaga vaga) throws DadosException{
		  if(this.conjVagas.contains(vaga))
				return;
			this.conjVagas.add(vaga);
			vaga.setResponsavel(this);
		}
	  
	  public void removeResponsavel(Vaga vaga) throws DadosException{
		  if(!this.conjVagas.contains(vaga))
				return;
			this.conjVagas.remove(vaga);
			vaga.setResponsavel(null);
			}
	

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String[] getCamposDeTabela() {
		return new String[] { "CPF", "Nome", "Empresa", "Master?" };
	}

	@Override
	public Object[] getDadosParaTabela() {
		return new Object[] { this.getCpf(), this.getNome(), this.empresa,
				this.master };
		
	}
}