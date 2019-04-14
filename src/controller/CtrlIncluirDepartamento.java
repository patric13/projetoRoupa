package controller;

import model.Departamento;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIDepartamento;
import viewer.ViewerManager;
import viewer.desktop.JanelaDepartamento;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlIncluirDepartamento implements ICtrlCasoDeUso {
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
	private final CtrlManterDepartamentos ctrlManterDepartamentos;

	/**
	 * Referência para a UI Departamento que permitirá a inclusão e
	 * alteração do Departamento
	 */
	private UIDepartamento uiDepartamento;

	/**
	 * Referência para o objeto Departamento sendo manipulado
	 */
	private Departamento atual;

	/**
	 * Referência para o objeto DaoDepartamento
	 */
	private IDAO<Departamento> dao = (IDAO<Departamento>)DAO.getDAO(Departamento.class);

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
	public CtrlIncluirDepartamento(CtrlManterDepartamentos ctrl) throws DadosException, ControleException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterDepartamentos = ctrl;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.INCLUINDO);
		// Crio e abro a janela de cadastro
		this.uiDepartamento = (UIDepartamento)ViewerManager.obterViewer(this, UIDepartamento.class);
		// Solicito à interface que carregue os objetos
		this.uiDepartamento.exibir();
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
		this.uiDepartamento.fechar();
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterDepartamentos.terminarCasoDeUsoIncluirDepartamento();
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

	/** 
	 * 
	 */
	public void incluir(String sigla, String nome) throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.INCLUINDO)
			throw new ControleException(new ErroDeControle("Não é possível concluir uma operação de inclusão"));
		// Crio um novo objeto Departamento
		this.atual = new Departamento(sigla, nome);
		// Salvo o objeto Departamento usando o DAO
		dao.salvar(this.atual);
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
