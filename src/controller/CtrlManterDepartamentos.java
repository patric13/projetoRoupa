package controller;

import java.util.List;

import model.Departamento;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroDepartamentos;
import viewer.ViewerManager;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlManterDepartamentos implements ICtrlCasoDeUso {
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
	 * Refer�ncia para o controlador do caso de uso Incluir Departamento.
	 */
	private CtrlIncluirDepartamento ctrlIncluirDepartamento;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Departamento.
	 */
	private CtrlAlterarDepartamento ctrlAlterarDepartamento;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Departamento.
	 */
	private CtrlExcluirDepartamento ctrlExcluirDepartamento;

	/**
	 * Refer�ncia para a janela do cadastro de Departamentos
	 */
	private UICadastroDepartamentos uiCadastro;

	/**
	 * Refer�ncia para o objeto DaoDepartamento
	 */
	private IDAO<Departamento> dao = (IDAO<Departamento>)DAO.getDAO(Departamento.class);

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
	public CtrlManterDepartamentos(CtrlSessaoUsuario p) throws ControleException, DadosException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlPrg = p;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * Inicia o caso de uso "Manter Departamentos"
	 */
	public void iniciar() throws ControleException, DadosException {
		// Recupero os objetos Departamento do DAO
		this.dao = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Crio e abro a janela de cadastro
		this.uiCadastro = (UICadastroDepartamentos)ViewerManager.obterViewer(this, UICadastroDepartamentos.class);
		// Solicito � interface que carregue os objetos
		this.uiCadastro.exibirObjetos(deptos);
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
		this.ctrlPrg.terminarCasoDeUsoManterDepartamentos();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirDepartamento() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de departamento
		this.ctrlIncluirDepartamento = new CtrlIncluirDepartamento(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirDepartamento() throws DadosException, ControleException {
		if(this.ctrlIncluirDepartamento != null)
			this.ctrlIncluirDepartamento.terminar();
		this.ctrlIncluirDepartamento = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Departamento do DAO
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(deptos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarDepartamento(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		Departamento d = (Departamento)selecionado;
		// Abro a janela de departamento
		this.ctrlAlterarDepartamento = new CtrlAlterarDepartamento(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarDepartamento() throws DadosException, ControleException{
		if(this.ctrlAlterarDepartamento != null)
			this.ctrlAlterarDepartamento.terminar();
		this.ctrlAlterarDepartamento = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Departamento do DAO
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(deptos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirDepartamento(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		Departamento d = (Departamento)selecionado;
		// Abro a janela de departamento
		this.ctrlExcluirDepartamento = new CtrlExcluirDepartamento(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirDepartamento() throws DadosException, ControleException{
		if(this.ctrlExcluirDepartamento != null)
			this.ctrlExcluirDepartamento.terminar();
		this.ctrlExcluirDepartamento = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Departamento do DAO
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(deptos);
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
