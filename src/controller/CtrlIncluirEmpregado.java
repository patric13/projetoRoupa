package controller;

import java.util.List;

import model.Departamento;
import model.Empregado;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIDepartamento;
import viewer.UIEmpregado;
import viewer.ViewerManager;
import viewer.desktop.JanelaEmpregado;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlIncluirEmpregado implements ICtrlCasoDeUso {
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
	 * Referência para o controlador do caso de uso Manter Empregados.
	 */
	private final CtrlManterEmpregados ctrlManterEmpregados;

	/**
	 * Referência para a UI Empregado que permitirá a inclusão e
	 * alteração do Empregado
	 */
	private UIEmpregado uiEmpregado;

	/**
	 * Referência para o objeto Empregado sendo manipulado
	 */
	private Empregado atual;

	/**
	 * Referência para o objeto DaoEmpregado
	 */
	private IDAO<Empregado> dao = (IDAO<Empregado>)DAO.getDAO(Empregado.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterEmpregados
	 */
	public CtrlIncluirEmpregado(CtrlManterEmpregados ctrl) throws DadosException, ControleException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterEmpregados = ctrl;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.INCLUINDO);
		// Recupero os objetos Departamento
		IDAO<Departamento> daoDepartamento = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<Object> deptos = daoDepartamento.getListaObjs();
		// Crio e abro a janela de cadastro
		this.uiEmpregado = (UIEmpregado)ViewerManager.obterViewer(this, UIEmpregado.class);
		// Solicito à interface que atualize os campos 
		this.uiEmpregado.atualizarCampos(deptos);
		// Solicito à interface que carregue os objetos
		this.uiEmpregado.exibir();
		// Não há Empregado em manipulação
		this.atual = null;
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Não há Empregado em manipulação
		this.atual = null;
		// Fecho a UI
		this.uiEmpregado.fechar();
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterEmpregados.terminarCasoDeUsoIncluirEmpregado();
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
	public void incluir(String cpf, int matrFuncional, String nome, Object depto) throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.INCLUINDO)
			throw new ControleException(new ErroDeControle("Não é possível concluir uma operação de inclusão"));
		// Crio um novo objeto Empregado
		this.atual = new Empregado(cpf, matrFuncional, nome, (Departamento)depto);
		// Salvo o objeto Empregado usando o DAO
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
