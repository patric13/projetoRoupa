package controller;

import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.Administrador;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIAdministrador;
import viewer.web.AdministradorMB;

public class CtrlIncluirAdministrador implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		INCLUINDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == INCLUINDO || 
			   anterior == INCLUINDO && novo == ENCERRADO)
				return;
			throw new ControleException(new ErroDeControle("N�o se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Refer�ncia para o controlador do caso de uso Manter Departamentos.
	 */
	private final CtrlManterAdministradores ctrlManterAdministradores;

	/**
	 * Refer�ncia para a UI Departamento que permitir� a inclus�o e
	 * altera��o do Departamento
	 */
	private UIAdministrador uiAdministrador;

	/**
	 * Refer�ncia para o objeto Departamento sendo manipulado
	 */
	private Administrador atual;

	/**
	 * Refer�ncia para o objeto DaoDepartamento
	 */
	private IDAO<Administrador> dao = (IDAO<Administrador>)DAO.getDAO(Administrador.class);

	/**
	 * Atributo que indica qual opera��o est� em curso
	 */
	private Status status;

	//
	// M�TODOS
	//

	/**
	 * Construtor da classe CtrlManterDepartamentos
	 */
	public CtrlIncluirAdministrador(CtrlManterAdministradores ctrl) throws DadosException, ControleException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlManterAdministradores = ctrl;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a UI de departamento
		this.uiAdministrador = new AdministradorMB(this);
		// N�o h� departamento em manipula��o
		this.atual = null;
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// N�o h� departamento em manipula��o
		this.atual = null;
		// Fecho a UI
		this.uiAdministrador.fechar();
		// Informo que o controlador est� dispon�vel
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o t�rmino deste caso de uso
		ctrlManterAdministradores.terminarCasoDeUsoIncluirAdministrador();
	}

	/** 
	 * 
	 */
	public void cancelarIncluir() throws DadosException, ControleException {
		// Se o controlador n�o tinha ativado uma inclusao, n�o fa�o nada!
		if(this.status != Status.INCLUINDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel cancelar uma opera��o de inclus�o"));
		// Termino o caso de uso
		this.terminar();
	}

	public Administrador getAtual() {
		return atual;
	}

	public void setAtual(Administrador atual) {
		this.atual = atual;
	}

	/**
	 * @throws model.DadosException  
	 * 
	 */
	public void incluir(Administrador administrador) throws DadosException, ControleException, model.DadosException {
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getAtual().getId() != null)
				&& (this.getAtual().getId().longValue() == 0))
			this.getAtual().setId(null);

		this.dao.salvar(this.getAtual());
		// limpa a lista
		this.atual = null;

		// limpar o objeto da p�gina
		this.setAtual(new Administrador());
		
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
