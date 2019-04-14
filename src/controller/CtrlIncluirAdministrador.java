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
			throw new ControleException(new ErroDeControle("Não se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Referência para o controlador do caso de uso Manter Departamentos.
	 */
	private final CtrlManterAdministradores ctrlManterAdministradores;

	/**
	 * Referência para a UI Departamento que permitirá a inclusão e
	 * alteração do Departamento
	 */
	private UIAdministrador uiAdministrador;

	/**
	 * Referência para o objeto Departamento sendo manipulado
	 */
	private Administrador atual;

	/**
	 * Referência para o objeto DaoDepartamento
	 */
	private IDAO<Administrador> dao = (IDAO<Administrador>)DAO.getDAO(Administrador.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterDepartamentos
	 */
	public CtrlIncluirAdministrador(CtrlManterAdministradores ctrl) throws DadosException, ControleException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterAdministradores = ctrl;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a UI de departamento
		this.uiAdministrador = new AdministradorMB(this);
		// Não há departamento em manipulação
		this.atual = null;
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Não há departamento em manipulação
		this.atual = null;
		// Fecho a UI
		this.uiAdministrador.fechar();
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterAdministradores.terminarCasoDeUsoIncluirAdministrador();
	}

	/** 
	 * 
	 */
	public void cancelarIncluir() throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.INCLUINDO)
			throw new ControleException(new ErroDeControle("Não é possível cancelar uma operação de inclusão"));
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

		// limpar o objeto da página
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
