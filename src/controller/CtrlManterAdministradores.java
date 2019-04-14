package controller;

import java.util.List;

import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroAdministradores;
import viewer.ViewerManager;
import viewer.web.CadastroAdministradoresMB;
import viewer.web.util.JSFUtil;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.Administrador;

public class CtrlManterAdministradores implements ICtrlCasoDeUso {
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
	 * Referência para o controlador do caso de uso Incluir ResponsavelEmpresa.
	 */
	private CtrlIncluirAdministrador ctrlIncluirAdministrador;

	/**
	 * Referência para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlAlterarAdministrador ctrlAlterarAdministrador;

	/**
	 * Referência para o controlador do caso de uso Alterar Empresa.
	 */
	private CtrlExcluirAdministrador ctrlExcluirAdministrador;

	/**
	 * Referência para a janela do cadastro de Empresas
	 */
	private UICadastroAdministradores uiCadastro;

	/**
	 * Referência para o objeto DaoEmpresa
	 */
	private IDAO<Administrador> dao = (IDAO<Administrador>)DAO.getDAO(Administrador.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterResponsavelEmpresas
	 */
	public CtrlManterAdministradores(CtrlSessaoUsuario p) throws ControleException, DadosException {
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
		this.uiCadastro = (UICadastroAdministradores)ViewerManager.obterViewer(this, UICadastroAdministradores.class);
		// Solicito à interface que carregue os objetos
		CadastroAdministradoresMB cadastroAdministradoresMB = (CadastroAdministradoresMB) JSFUtil.getVariavelApplication("cadastroAdministradoresMB");
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
		this.ctrlPrg.terminarCasoDeUsoManterAdministradores();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirAdministrador() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de ResponsavelEmpresa
		this.ctrlIncluirAdministrador = new CtrlIncluirAdministrador(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirAdministrador() throws DadosException, ControleException {
		if(this.ctrlIncluirAdministrador != null)
			this.ctrlIncluirAdministrador.terminar();
		this.ctrlIncluirAdministrador = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos ResponsavelEmpresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(emp);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarAdministrador(Administrador administrador) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		Administrador d = (Administrador)administrador;
		// Abro a janela de ResponsavelEmpresa
		this.ctrlAlterarAdministrador = new CtrlAlterarAdministrador(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarAdministrador() throws DadosException, ControleException{
		if(this.ctrlAlterarAdministrador != null)
			this.ctrlAlterarAdministrador.terminar();
		this.ctrlAlterarAdministrador = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos ResponsavelEmpresa do DAO
		List<IDadosParaTabela> emp = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(emp);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirAdministrador(Administrador objetoDoBanco) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		Administrador d = (Administrador)objetoDoBanco;
		// Abro a janela de ResponsavelEmpresa
		this.ctrlExcluirAdministrador = new CtrlExcluirAdministrador(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirAdministrador() throws DadosException, ControleException{
		if(this.ctrlExcluirAdministrador != null)
			this.ctrlExcluirAdministrador.terminar();
		this.ctrlExcluirAdministrador = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos ResponsavelEmpresa do DAO
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
