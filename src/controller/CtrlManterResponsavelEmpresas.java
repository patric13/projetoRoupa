package controller;

import java.util.List;

import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroResponsavelEmpresas;
import viewer.ViewerManager;
import viewer.web.CadastroResponsavelEmpresasMB;
import viewer.web.util.JSFUtil;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.ResponsavelEmpresa;

public class CtrlManterResponsavelEmpresas implements ICtrlCasoDeUso {
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
	 * Refer�ncia para o controlador do caso de uso Incluir ResponsavelEmpresa.
	 */
	private CtrlIncluirResponsavelEmpresa ctrlIncluirResponsavelEmpresa;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlAlterarResponsavelEmpresa ctrlAlterarResponsavelEmpresa;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlExcluirResponsavelEmpresa ctrlExcluirResponsavelEmpresa;

	/**
	 * Refer�ncia para a janela do cadastro de Empresas
	 */
	private UICadastroResponsavelEmpresas uiCadastro;

	/**
	 * Refer�ncia para o objeto DaoEmpresa
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
	 * Construtor da classe CtrlManterResponsavelEmpresas
	 */
	public CtrlManterResponsavelEmpresas(CtrlSessaoUsuario p) throws ControleException, DadosException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlPrg = p;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * Inicia o caso de uso "Manter Empresas"
	 */
	public void iniciar() throws ControleException, DadosException {
		
		// Crio e abro a janela de cadastro
		this.uiCadastro = (UICadastroResponsavelEmpresas)ViewerManager.obterViewer(this, UICadastroResponsavelEmpresas.class);
		// Solicito � interface que carregue os objetos
		CadastroResponsavelEmpresasMB cadastroResponsavelEmpresasMB = (CadastroResponsavelEmpresasMB) JSFUtil.getVariavelApplication("cadastroResponsavelEmpresasMB");
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
		this.ctrlPrg.terminarCasoDeUsoManterResponsavelEmpresas();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirResponsavelEmpresa() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de ResponsavelEmpresa
		this.ctrlIncluirResponsavelEmpresa = new CtrlIncluirResponsavelEmpresa(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirResponsavelEmpresa() throws DadosException, ControleException {
		if(this.ctrlIncluirResponsavelEmpresa != null)
			this.ctrlIncluirResponsavelEmpresa.terminar();
		this.ctrlIncluirResponsavelEmpresa = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos ResponsavelEmpresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(emp);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarResponsavelEmpresa(ResponsavelEmpresa responsavelEmpresa) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		ResponsavelEmpresa d = (ResponsavelEmpresa)responsavelEmpresa;
		// Abro a janela de ResponsavelEmpresa
		this.ctrlAlterarResponsavelEmpresa = new CtrlAlterarResponsavelEmpresa(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarResponsavelEmpresa() throws DadosException, ControleException{
		if(this.ctrlAlterarResponsavelEmpresa != null)
			this.ctrlAlterarResponsavelEmpresa.terminar();
		this.ctrlAlterarResponsavelEmpresa = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos ResponsavelEmpresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(emp);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirResponsavelEmpresa(ResponsavelEmpresa objetoDoBanco) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		ResponsavelEmpresa d = (ResponsavelEmpresa)objetoDoBanco;
		// Abro a janela de ResponsavelEmpresa
		this.ctrlExcluirResponsavelEmpresa = new CtrlExcluirResponsavelEmpresa(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirResponsavelEmpresa() throws DadosException, ControleException{
		if(this.ctrlExcluirResponsavelEmpresa != null)
			this.ctrlExcluirResponsavelEmpresa.terminar();
		this.ctrlExcluirResponsavelEmpresa = null;
		// Indico que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos ResponsavelEmpresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualiza��o da interface ap�s as a��es de inclus�o
		this.uiCadastro.exibirObjetos(emp);
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
