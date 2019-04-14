package controller;

import java.util.List;

import model.Projeto;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroProjetos;
import viewer.ViewerManager;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlManterProjetos implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		DISPONIVEL, INCLUINDO, EXCLUINDO, ALTERANDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == DISPONIVEL || 
			   anterior == DISPONIVEL && novo == INCLUINDO  || 
			   anterior == DISPONIVEL && novo == EXCLUINDO  ||
			   anterior == DISPONIVEL && novo == ALTERANDO  ||
			   anterior == DISPONIVEL && novo == ENCERRADO  ||
			   anterior == INCLUINDO  && novo == DISPONIVEL ||
			   anterior == EXCLUINDO  && novo == DISPONIVEL ||
			   anterior == ALTERANDO  && novo == DISPONIVEL)
				return;
			throw new ControleException(new ErroDeControle("N�o se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Refer�ncia para o controlador do programa.
	 */
	private final CtrlSessaoUsuario ctrlPrg;

	/**
	 * Refer�ncia para o controlador do caso de uso Incluir Projeto.
	 */
	private CtrlIncluirProjeto ctrlIncluirProjeto;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Projeto.
	 */
	private CtrlAlterarProjeto ctrlAlterarProjeto;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Projeto.
	 */
	private CtrlExcluirProjeto ctrlExcluirProjeto;

	/**
	 * Refer�ncia para a janela do cadastro de Projetos
	 */
	private UICadastroProjetos uiCadastro;

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
	public CtrlManterProjetos(CtrlSessaoUsuario p) throws ControleException, DadosException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlPrg = p;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Recupero os objetos Departamento do DAO
		this.dao = (IDAO<Projeto>)DAO.getDAO(Projeto.class);
		List<IDadosParaTabela> projetos = dao.getListaObjs();
		// Crio e abro a janela de cadastro
		this.uiCadastro = (UICadastroProjetos)ViewerManager.obterViewer(this, UICadastroProjetos.class);
		// Solicito � interface que carregue os objetos
		this.uiCadastro.exibirObjetos(projetos);
		// Solicito � interface que carregue os objetos
		this.uiCadastro.exibir();
		// Informo que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
	}

	/** 
	 * 
	 */
	public void terminar() throws ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Fecho a janela
		this.uiCadastro.fechar();
		// Informo que o controlador est� encerrado
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o t�rmino deste caso de uso
		this.ctrlPrg.terminarCasoDeUsoManterProjetos();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirProjeto() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de Projeto
		this.ctrlIncluirProjeto = new CtrlIncluirProjeto(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirProjeto() throws DadosException, ControleException {
		if(this.ctrlIncluirProjeto != null)
			this.ctrlIncluirProjeto.terminar();
		this.ctrlIncluirProjeto = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Projeto do DAO
		List<IDadosParaTabela> projetos = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(projetos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarProjeto(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		Projeto d = (Projeto)selecionado;
		// Abro a janela de Projeto
		this.ctrlAlterarProjeto = new CtrlAlterarProjeto(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarProjeto() throws DadosException, ControleException{
		if(this.ctrlAlterarProjeto != null)
			this.ctrlAlterarProjeto.terminar();
		this.ctrlAlterarProjeto = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Projeto do DAO
		List<IDadosParaTabela> projetos = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(projetos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirProjeto(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		Projeto d = (Projeto)selecionado;
		// Abro a janela de Projeto
		this.ctrlExcluirProjeto = new CtrlExcluirProjeto(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirProjeto() throws DadosException, ControleException{
		if(this.ctrlExcluirProjeto != null)
			this.ctrlExcluirProjeto.terminar();
		this.ctrlExcluirProjeto = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Projeto do DAO
		List<IDadosParaTabela> projetos = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(projetos);
		this.uiCadastro.exibir();
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
