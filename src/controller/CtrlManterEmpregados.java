package controller;

import java.util.List;

import model.Empregado;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroEmpregados;
import viewer.ViewerManager;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlManterEmpregados implements ICtrlCasoDeUso {
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
	 * Refer�ncia para o controlador do caso de uso Incluir Empregado.
	 */
	private CtrlIncluirEmpregado ctrlIncluirEmpregado;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Empregado.
	 */
	private CtrlAlterarEmpregado ctrlAlterarEmpregado;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Empregado.
	 */
	private CtrlExcluirEmpregado ctrlExcluirEmpregado;

	/**
	 * Refer�ncia para a janela do cadastro de Empregados
	 */
	private UICadastroEmpregados uiCadastro;

	/**
	 * Refer�ncia para o objeto DaoEmpregado
	 */
	private IDAO<Empregado> dao = (IDAO<Empregado>)DAO.getDAO(Empregado.class);

	/**
	 * Atributo que indica qual opera��o est� em curso
	 */
	private Status status;

	//
	// M�TODOS
	//

	/**
	 * Construtor da classe CtrlManterEmpregados
	 */
	public CtrlManterEmpregados(CtrlSessaoUsuario p) throws ControleException, DadosException {
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
		this.dao = (IDAO<Empregado>)DAO.getDAO(Empregado.class);
		List<IDadosParaTabela> emps = dao.getListaObjs();
		// Crio e abro a janela de cadastro
		this.uiCadastro = (UICadastroEmpregados)ViewerManager.obterViewer(this, UICadastroEmpregados.class);
		// Solicito � interface que carregue os objetos
		this.uiCadastro.exibirObjetos(emps);
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
		this.ctrlPrg.terminarCasoDeUsoManterEmpregados();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirEmpregado() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de Empregado
		this.ctrlIncluirEmpregado = new CtrlIncluirEmpregado(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirEmpregado() throws DadosException, ControleException {
		if(this.ctrlIncluirEmpregado != null)
			this.ctrlIncluirEmpregado.terminar();
		this.ctrlIncluirEmpregado = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Empregado do DAO
		List<IDadosParaTabela> emps = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(emps);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarEmpregado(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		Empregado d = (Empregado)selecionado;
		// Abro a janela de Empregado
		this.ctrlAlterarEmpregado = new CtrlAlterarEmpregado(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarEmpregado() throws DadosException, ControleException{
		if(this.ctrlAlterarEmpregado != null)
			this.ctrlAlterarEmpregado.terminar();
		this.ctrlAlterarEmpregado = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Empregado do DAO
		List<IDadosParaTabela> emps  = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(emps);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirEmpregado(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		Empregado d = (Empregado)selecionado;
		// Abro a janela de Empregado
		this.ctrlExcluirEmpregado = new CtrlExcluirEmpregado(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirEmpregado() throws DadosException, ControleException{
		if(this.ctrlExcluirEmpregado != null)
			this.ctrlExcluirEmpregado.terminar();
		this.ctrlExcluirEmpregado = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Empregado do DAO
		List<IDadosParaTabela> emps  = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(emps);
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
