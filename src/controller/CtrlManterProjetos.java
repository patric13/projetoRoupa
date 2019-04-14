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
			throw new ControleException(new ErroDeControle("Não se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Referência para o controlador do programa.
	 */
	private final CtrlSessaoUsuario ctrlPrg;

	/**
	 * Referência para o controlador do caso de uso Incluir Projeto.
	 */
	private CtrlIncluirProjeto ctrlIncluirProjeto;

	/**
	 * Referência para o controlador do caso de uso Alterar Projeto.
	 */
	private CtrlAlterarProjeto ctrlAlterarProjeto;

	/**
	 * Referência para o controlador do caso de uso Alterar Projeto.
	 */
	private CtrlExcluirProjeto ctrlExcluirProjeto;

	/**
	 * Referência para a janela do cadastro de Projetos
	 */
	private UICadastroProjetos uiCadastro;

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
	public CtrlManterProjetos(CtrlSessaoUsuario p) throws ControleException, DadosException {
		// Guardo a referência para o controlador do programa
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
		// Solicito à interface que carregue os objetos
		this.uiCadastro.exibirObjetos(projetos);
		// Solicito à interface que carregue os objetos
		this.uiCadastro.exibir();
		// Informo que o controlador de caso de uso está disponível
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
		// Informo que o controlador está encerrado
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		this.ctrlPrg.terminarCasoDeUsoManterProjetos();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirProjeto() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
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
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Projeto do DAO
		List<IDadosParaTabela> projetos = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(projetos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarProjeto(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
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
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Projeto do DAO
		List<IDadosParaTabela> projetos = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(projetos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirProjeto(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
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
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Projeto do DAO
		List<IDadosParaTabela> projetos = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
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
