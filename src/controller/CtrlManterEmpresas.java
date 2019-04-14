package controller;

import java.util.List;

import dominio.Empresa;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroEmpresas;
import viewer.ViewerManager;
import viewer.web.CadastroEmpresasMB;
import viewer.web.util.JSFUtil;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlManterEmpresas implements ICtrlCasoDeUso {
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
	 * Referência para o controlador do caso de uso Incluir Empresa.
	 */
	private CtrlIncluirEmpresa ctrlIncluirEmpresa;

	/**
	 * Referência para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlAlterarEmpresa ctrlAlterarEmpresa;

	/**
	 * Referência para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlExcluirEmpresa ctrlExcluirEmpresa;

	/**
	 * Referência para a janela do cadastro de Empresas
	 */
	private UICadastroEmpresas uiCadastro;

	/**
	 * Referência para o objeto DaoEmpresa
	 */
	private IDAO<Empresa> dao = (IDAO<Empresa>)DAO.getDAO(Empresa.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterEmpresas
	 */
	public CtrlManterEmpresas(CtrlSessaoUsuario p) throws ControleException, DadosException {
		// Guardo a referência para o controlador do programa
		this.ctrlPrg = p;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * Inicia o caso de uso "Manter Empresas"
	 */
	public void iniciar() throws ControleException, DadosException {
		
		// Crio e abro a janela de cadastro
		this.uiCadastro = (UICadastroEmpresas)ViewerManager.obterViewer(this, UICadastroEmpresas.class);
		// Solicito à interface que carregue os objetos
		CadastroEmpresasMB cadastroEmpresasMB = (CadastroEmpresasMB) JSFUtil.getVariavelApplication("cadastroEmpresasMB");
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
		this.ctrlPrg.terminarCasoDeUsoManterEmpresas();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirEmpresa() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de empresa
		this.ctrlIncluirEmpresa = new CtrlIncluirEmpresa(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirEmpresa() throws DadosException, ControleException {
		if(this.ctrlIncluirEmpresa != null)
			this.ctrlIncluirEmpresa.terminar();
		this.ctrlIncluirEmpresa = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Empresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(emp);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarEmpresa(Empresa empresa) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		Empresa d = (Empresa)empresa;
		// Abro a janela de empresa
		this.ctrlAlterarEmpresa = new CtrlAlterarEmpresa(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarEmpresa() throws DadosException, ControleException{
		if(this.ctrlAlterarEmpresa != null)
			this.ctrlAlterarEmpresa.terminar();
		this.ctrlAlterarEmpresa = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos empresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(emp);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirEmpresa(Empresa objetoDoBanco) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		Empresa d = (Empresa)objetoDoBanco;
		// Abro a janela de empresa
		this.ctrlExcluirEmpresa = new CtrlExcluirEmpresa(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirEmpresa() throws DadosException, ControleException{
		if(this.ctrlExcluirEmpresa != null)
			this.ctrlExcluirEmpresa.terminar();
		this.ctrlExcluirEmpresa = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Empresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
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
