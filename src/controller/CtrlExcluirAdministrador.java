package controller;

import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIExcluirAdministrador;
import viewer.ViewerManager;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.Administrador;
import dominio.dao.AdministradorDAO;

public class CtrlExcluirAdministrador implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		EXCLUINDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == EXCLUINDO || 
			   anterior == EXCLUINDO && novo == ENCERRADO)
				return;
			throw new ControleException(new ErroDeControle("N�o se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Refer�ncia para o controlador do caso de uso
	 */
	private final CtrlManterAdministradores ctrlManterAdministradores;

	/**
	 * Refer�ncia para o objeto a ser atualizado 
	 */
	private Administrador atual;
	
	/**
	 * Refer�ncia para a janela Departamento que permitir� a exclus�o do Departamento
	 */
	private UIExcluirAdministrador uiExcluirAdministrador;

	/**
	 * Refer�ncia para o objeto DaoDepartamento
	 */
	private IDAO<Administrador> dao = (IDAO<Administrador>)DAO.getDAO(Administrador.class);

	/**
	 * Atributo que indica qual opera��o est� em curso
	 */
	private Status status;

	/**
	 * Refer�ncia para o objeto Administrador
	 */
	private AdministradorDAO dao2 = new AdministradorDAO();
	//
	// M�TODOS
	//

	/**
	 * Construtor da classe CtrlManterDepartamentos
	 */
	public CtrlExcluirAdministrador(CtrlManterAdministradores ctrl, Administrador d) throws ControleException, DadosException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlManterAdministradores = ctrl;
		// Guardo a refer�ncia para o objeto a ser alterado
		this.atual = d;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Informo que o controlador de caso de uso est� excluindo
		this.setStatus(Status.EXCLUINDO);
		// Crio e abro a janela de cadastro
		this.uiExcluirAdministrador = (UIExcluirAdministrador)ViewerManager.obterViewer(this, UIExcluirAdministrador.class);
		
		// Solicito � interface que carregue os objetos
		this.uiExcluirAdministrador.exibirExcluir();
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// N�o h� departamento em manipula��o
		this.atual = null;
		// Solicito o fechamento da janela
		this.uiExcluirAdministrador.fechar();		
		// Informo que o controlador est� dispon�vel
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o t�rmino deste caso de uso
		ctrlManterAdministradores.terminarCasoDeUsoExcluirAdministrador();
	}

	/** 
	 * 
	 */
	public void cancelarExcluir() throws DadosException, ControleException {
		// Se o controlador n�o tinha ativado uma inclusao, n�o fa�o nada!
		if(this.status != Status.EXCLUINDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel cancelar uma opera��o de exclus�o"));
		// Termino o caso de uso
		this.terminar();
	}

	/**
	 * @throws model.DadosException  
	 * 
	 */
	public void excluir() throws DadosException, ControleException, model.DadosException {
		// Se o controlador n�o tinha ativado uma exclus�o, n�o fa�o nada!
		if(this.status != Status.EXCLUINDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel concluir uma opera��o de exclus�o"));
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
