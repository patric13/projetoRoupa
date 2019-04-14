package controller;

import java.util.ArrayList;
import java.util.List;

import model.Departamento;
import model.Empregado;
import model.Projeto;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIDepartamento;
import viewer.UIProjeto;
import viewer.ViewerManager;
import viewer.desktop.JanelaProjeto;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlAlterarProjeto implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		ALTERANDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == ALTERANDO || 
			   anterior == ALTERANDO  && novo == ENCERRADO)
				return;
			throw new ControleException(new ErroDeControle("N�o se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Refer�ncia para o controlador do caso de uso manter.
	 */
	private final CtrlManterProjetos ctrlManterProjetos;

	/**
	 * Refer�ncia para o objeto a ser atualizado 
	 */
	private Projeto atual;
	
	/**
	 * Refer�ncia para a janela Projeto que permitir� a inclus�o e
	 * altera��o do Projeto
	 */
	private UIProjeto uiProjeto;

	/**
	 * Refer�ncia para o objeto DaoProjeto
	 */
	private IDAO<Projeto> dao = (IDAO<Projeto>)DAO.getDAO(Projeto.class);

	/**
	 * Atributo que indica qual opera��o est� em curso
	 */
	private Status status;

	//
	// M�TODOS
	//

	/**
	 * Construtor da classe CtrlManterProjetos
	 */
	public CtrlAlterarProjeto(CtrlManterProjetos ctrl, Projeto d) throws ControleException, DadosException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlManterProjetos = ctrl;
		// Guardo a refer�ncia para o objeto a ser alterado
		this.atual = d;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Informo que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.ALTERANDO);
		// Recupero os objetos departamento e empregado
		IDAO<Departamento> daoDepto = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<Object> deptos = daoDepto.getListaObjs();
		IDAO<Empregado> daoEmp = (IDAO<Empregado>)DAO.getDAO(Empregado.class);
		List<Object> emps = daoEmp.getListaObjs();

		// Crio e abro a janela de cadastro
		this.uiProjeto = (UIProjeto)ViewerManager.obterViewer(this, UIProjeto.class);		
		// Solicito � interface que atualize os campos 
		this.uiProjeto.atualizarCampos(this.atual.getNome(), deptos, emps, this.atual.getDepto(), new ArrayList(this.atual.getConjEmpregados()));
		// Solicito � interface que carregue os objetos
		this.uiProjeto.exibir();
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// N�o h� Projeto em manipula��o
		this.atual = null;
		// Fecho a janela
		this.uiProjeto.fechar();
		// Informo que o controlador est� dispon�vel
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o t�rmino deste caso de uso
		ctrlManterProjetos.terminarCasoDeUsoAlterarProjeto();
	}

	/** 
	 * 
	 */
	public void cancelarAlterar() throws DadosException, ControleException {
		// Se o controlador n�o tinha ativado uma inclusao, n�o fa�o nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel cancelar uma opera��o de altera��o"));
		// Termino o caso de uso
		this.terminar();
	}

	/** 
	 * 
	 */
	public void alterar(String nome, Object depto, List emps) throws DadosException, ControleException {
		// Se o controlador n�o tinha ativado uma inclusao, n�o fa�o nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel concluir uma opera��o de altera��o"));
		// Atualizo os campos
		this.atual.setNome(nome);
		this.atual.setDepto((Departamento)depto);
		this.atual.removerTodosEmpregados();
		this.atual.adicionarTodosEmpregados(emps);
		// Salvo o objeto Projeto usando o DAO
		dao.atualizar(this.atual);
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
