package controller;

import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIResponsavelEmpresa;
import viewer.web.ResponsavelEmpresaMB;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.ResponsavelEmpresa;

public class CtrlIncluirResponsavelEmpresa implements ICtrlCasoDeUso {
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
	private final CtrlManterResponsavelEmpresas ctrlManterResponsavelEmpresas;

	/**
	 * Refer�ncia para a UI Departamento que permitir� a inclus�o e
	 * altera��o do Departamento
	 */
	private UIResponsavelEmpresa uiResponsavelEmpresa;

	/**
	 * Refer�ncia para o objeto Departamento sendo manipulado
	 */
	private ResponsavelEmpresa atual;

	/**
	 * Refer�ncia para o objeto DaoDepartamento
	 */
	private IDAO<ResponsavelEmpresa> dao = (IDAO<ResponsavelEmpresa>)DAO.getDAO(ResponsavelEmpresa.class);

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
	public CtrlIncluirResponsavelEmpresa(CtrlManterResponsavelEmpresas ctrl) throws DadosException, ControleException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlManterResponsavelEmpresas = ctrl;
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
		this.uiResponsavelEmpresa = new ResponsavelEmpresaMB(this);
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
		this.uiResponsavelEmpresa.fechar();
		// Informo que o controlador est� dispon�vel
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o t�rmino deste caso de uso
		ctrlManterResponsavelEmpresas.terminarCasoDeUsoIncluirResponsavelEmpresa();
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

	public ResponsavelEmpresa getAtual() {
		return atual;
	}

	public void setAtual(ResponsavelEmpresa atual) {
		this.atual = atual;
	}

	/**
	 * @throws model.DadosException  
	 * 
	 */
	public void incluir(ResponsavelEmpresa responsavelEmpresa) throws DadosException, ControleException, model.DadosException {
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
		this.setAtual(new ResponsavelEmpresa());
		
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
