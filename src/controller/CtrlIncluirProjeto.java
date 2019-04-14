package controller;

import java.util.List;

import model.Departamento;
import model.Empregado;
import model.Projeto;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIEmpregado;
import viewer.UIProjeto;
import viewer.ViewerManager;
import viewer.desktop.JanelaProjeto;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlIncluirProjeto implements ICtrlCasoDeUso {
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
	 * Referência para o controlador do caso de uso Manter Projetos.
	 */
	private final CtrlManterProjetos ctrlManterProjetos;

	/**
	 * Referência para a UI Projeto que permitirá a inclusão e
	 * alteração do Projeto
	 */
	private UIProjeto uiProjeto;

	/**
	 * Referência para o objeto Projeto sendo manipulado
	 */
	private Projeto atual;

	/**
	 * Referência para o objeto DaoProjeto
	 */
	private IDAO<Projeto> dao = (IDAO<Projeto>)DAO.getDAO(Projeto.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterProjetos
	 */
	public CtrlIncluirProjeto(CtrlManterProjetos ctrl) throws DadosException, ControleException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterProjetos = ctrl;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.INCLUINDO);
		// Recupero os objetos Departamento do DAO
		IDAO<Departamento> daoDepto = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<Object> deptos = daoDepto.getListaObjs();
		IDAO<Empregado> daoEmp = (IDAO<Empregado>)DAO.getDAO(Empregado.class);
		List<Object> emps = daoEmp.getListaObjs();
		// Crio e abro a janela de cadastro
		this.uiProjeto = (UIProjeto)ViewerManager.obterViewer(this, UIProjeto.class);
		// Solicito à interface que atualize os campos 
		this.uiProjeto.atualizarCampos(deptos,emps);
		// Solicito à interface que carregue os objetos
		this.uiProjeto.exibir();
		// Não há Empregado em manipulação
		this.atual = null;
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Não há Projeto em manipulação
		this.atual = null;
		// Fecho a UI
		this.uiProjeto.fechar();
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterProjetos.terminarCasoDeUsoIncluirProjeto();
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
	public void incluir(String nome, Object depto, List emps) throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.INCLUINDO)
			throw new ControleException(new ErroDeControle("Não é possível concluir uma operação de inclusão"));
		// Crio um novo objeto Projeto
		this.atual = new Projeto(nome, (Departamento)depto, emps);
		// Salvo o objeto Projeto usando o DAO
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
