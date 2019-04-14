package controller;

import java.util.List;

import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.Vaga;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroVagas;
import viewer.ViewerManager;
import viewer.web.CadastroVagasMB;
import viewer.web.util.JSFUtil;

public class CtrlManterVagas implements ICtrlCasoDeUso {
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
	private CtrlIncluirVaga ctrlIncluirVaga;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlAlterarVaga ctrlAlterarVaga;

	/**
	 * Refer�ncia para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlExcluirVaga ctrlExcluirVaga;

	/**
	 * Refer�ncia para a janela do cadastro de Empresas
	 */
	private UICadastroVagas uiCadastro;

	/**
	 * Refer�ncia para o objeto DaoEmpresa
	 */
	private IDAO<Vaga> dao = (IDAO<Vaga>)DAO.getDAO(Vaga.class);

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
	public CtrlManterVagas(CtrlSessaoUsuario p) throws ControleException, DadosException {
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
		this.uiCadastro = (UICadastroVagas)ViewerManager.obterViewer(this, UICadastroVagas.class);
		// Solicito � interface que carregue os objetos
		CadastroVagasMB cadastroVagasMB = (CadastroVagasMB) JSFUtil.getVariavelApplication("cadastroVagasMB");
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
		this.ctrlPrg.terminarCasoDeUsoManterVagas();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirVaga() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de ResponsavelEmpresa
		this.ctrlIncluirVaga = new CtrlIncluirVaga(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirVaga() throws DadosException, ControleException {
		if(this.ctrlIncluirVaga != null)
			this.ctrlIncluirVaga.terminar();
		this.ctrlIncluirVaga = null;
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
	public void iniciarCasoDeUsoAlterarVaga(Vaga vaga) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		Vaga d = (Vaga)vaga;
		// Abro a janela de ResponsavelEmpresa
		this.ctrlAlterarVaga = new CtrlAlterarVaga(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarVaga() throws DadosException, ControleException{
		if(this.ctrlAlterarVaga != null)
			this.ctrlAlterarVaga.terminar();
		this.ctrlAlterarVaga = null;
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
	public void iniciarCasoDeUsoExcluirVaga(Vaga objetoDoBanco) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso est� incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		Vaga d = (Vaga)objetoDoBanco;
		// Abro a janela de ResponsavelEmpresa
		this.ctrlExcluirVaga = new CtrlExcluirVaga(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirVaga() throws DadosException, ControleException{
		if(this.ctrlExcluirVaga != null)
			this.ctrlExcluirVaga.terminar();
		this.ctrlExcluirVaga = null;
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
