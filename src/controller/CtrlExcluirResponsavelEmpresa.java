package controller;

import dominio.ResponsavelEmpresa;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIExcluirResponsavelEmpresa;
import viewer.ViewerManager;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.dao.ResponsavelEmpresaDAO;

public class CtrlExcluirResponsavelEmpresa implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		EXCLUINDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == EXCLUINDO || 
			   anterior == EXCLUINDO && novo == ENCERRADO)
				return;
			throw new ControleException(new ErroDeControle("Não se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Referência para o controlador do caso de uso
	 */
	private final CtrlManterResponsavelEmpresas ctrlManterResponsavelEmpresas;

	/**
	 * Referência para o objeto a ser atualizado 
	 */
	private ResponsavelEmpresa atual;
	
	/**
	 * Referência para a janela Departamento que permitirá a exclusão do Departamento
	 */
	private UIExcluirResponsavelEmpresa uiExcluirResponsavelEmpresa;

	/**
	 * Referência para o objeto DaoDepartamento
	 */
	private IDAO<ResponsavelEmpresa> dao = (IDAO<ResponsavelEmpresa>)DAO.getDAO(ResponsavelEmpresa.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	/**
	 * Referência para o objeto ResponsavelEmpresa
	 */
	private ResponsavelEmpresaDAO dao2 = new ResponsavelEmpresaDAO();
	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterDepartamentos
	 */
	public CtrlExcluirResponsavelEmpresa(CtrlManterResponsavelEmpresas ctrl, ResponsavelEmpresa d) throws ControleException, DadosException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterResponsavelEmpresas = ctrl;
		// Guardo a referência para o objeto a ser alterado
		this.atual = d;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Informo que o controlador de caso de uso está excluindo
		this.setStatus(Status.EXCLUINDO);
		// Crio e abro a janela de cadastro
		this.uiExcluirResponsavelEmpresa = (UIExcluirResponsavelEmpresa)ViewerManager.obterViewer(this, UIExcluirResponsavelEmpresa.class);
		
		// Solicito à interface que carregue os objetos
		this.uiExcluirResponsavelEmpresa.exibirExcluir();
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Não há departamento em manipulação
		this.atual = null;
		// Solicito o fechamento da janela
		this.uiExcluirResponsavelEmpresa.fechar();		
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterResponsavelEmpresas.terminarCasoDeUsoExcluirResponsavelEmpresa();
	}

	/** 
	 * 
	 */
	public void cancelarExcluir() throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.EXCLUINDO)
			throw new ControleException(new ErroDeControle("Não é possível cancelar uma operação de exclusão"));
		// Termino o caso de uso
		this.terminar();
	}

	/**
	 * @throws model.DadosException  
	 * 
	 */
	public void excluir() throws DadosException, ControleException, model.DadosException {
		// Se o controlador não tinha ativado uma exclusão, não faço nada!
		if(this.status != Status.EXCLUINDO)
			throw new ControleException(new ErroDeControle("Não é possível concluir uma operação de exclusão"));
		// Desaloco todos os empregados do departamento
		
		// Salvo o objeto Departamento usando o DAO
		this.dao2.excluir(this.atual);
		
		// Termino o caso de uso
		this.terminar();
	}

	/**
	 * 
	 * @return
	 */
	public Status getStatus() {
		return this.status;
	}
	
	/**
	 * 
	 * @param novo
	 * @throws ControleException
	 */
	public void setStatus(Status novo) throws ControleException {
		Status.validarTransicaoStatus(this.status,novo);
		this.status = novo;
	}
}
