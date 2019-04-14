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
			throw new ControleException(new ErroDeControle("Não se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Referência para o controlador do caso de uso manter.
	 */
	private final CtrlManterProjetos ctrlManterProjetos;

	/**
	 * Referência para o objeto a ser atualizado 
	 */
	private Projeto atual;
	
	/**
	 * Referência para a janela Projeto que permitirá a inclusão e
	 * alteração do Projeto
	 */
	private UIProjeto uiProjeto;

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
	public CtrlAlterarProjeto(CtrlManterProjetos ctrl, Projeto d) throws ControleException, DadosException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterProjetos = ctrl;
		// Guardo a referência para o objeto a ser alterado
		this.atual = d;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Informo que o controlador de caso de uso está disponível
		this.setStatus(Status.ALTERANDO);
		// Recupero os objetos departamento e empregado
		IDAO<Departamento> daoDepto = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<Object> deptos = daoDepto.getListaObjs();
		IDAO<Empregado> daoEmp = (IDAO<Empregado>)DAO.getDAO(Empregado.class);
		List<Object> emps = daoEmp.getListaObjs();

		// Crio e abro a janela de cadastro
		this.uiProjeto = (UIProjeto)ViewerManager.obterViewer(this, UIProjeto.class);		
		// Solicito à interface que atualize os campos 
		this.uiProjeto.atualizarCampos(this.atual.getNome(), deptos, emps, this.atual.getDepto(), new ArrayList(this.atual.getConjEmpregados()));
		// Solicito à interface que carregue os objetos
		this.uiProjeto.exibir();
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Não há Projeto em manipulação
		this.atual = null;
		// Fecho a janela
		this.uiProjeto.fechar();
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterProjetos.terminarCasoDeUsoAlterarProjeto();
	}

	/** 
	 * 
	 */
	public void cancelarAlterar() throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("Não é possível cancelar uma operação de alteração"));
		// Termino o caso de uso
		this.terminar();
	}

	/** 
	 * 
	 */
	public void alterar(String nome, Object depto, List emps) throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("Não é possível concluir uma operação de alteração"));
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
