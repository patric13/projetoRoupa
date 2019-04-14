package controller;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

import model.dao.DAO;
import model.util.DadosException;
import viewer.UIHome;
import viewer.UILogin;
import viewer.ViewerManager;
import viewer.web.util.JSFUtil;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.Administrador;
import dominio.ResponsavelEmpresa;
import dominio.Usuario;
import dominio.dao.AdministradorDAO;
import dominio.dao.ResponsavelEmpresaDAO;
import dominio.dao.UsuarioDAO;

/**
 * Este é o controlador que gerencia a execução do meu programa.
 * @author Alessandro Cerqueira
 */
public class CtrlSessaoUsuario implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	// ---------
	// O controlador do programa deve ter um atributo para
	// cada controlador de caso de uso do programa.
	//
	// Também o controlador do programa deve ter um atributo 
	// para referenciar a janela principal do programa.
	//
	/**
	 * Referência para o controlador do caso de uso "Manter Departamentos"
	 */
	private CtrlManterDepartamentos 	ctrlDepartamentos;
	/**
	 * Referência para o controlador do caso de uso "Manter Empregados"
	 */
	private CtrlManterEmpregados    	ctrlEmpregados;
	/**
	 * Referência para o controlador do caso de uso "Manter Projetos"
	 */
	private CtrlManterProjetos    		ctrlProjetos;
	/**
	 * Referência para o controlador do caso de uso "Manter Empresas"
	 */
	private CtrlManterEmpresas    		ctrlEmpresas;
	/**
	 * Referência para o controlador do caso de uso "Manter Empresas"
	 */
	private CtrlManterResponsavelEmpresas    		ctrlResponsavelEmpresas;
	/**
	 * 
	 */
	/**
	 * Referência para o controlador do caso de uso "Manter Empresas"
	 */
	private CtrlManterAdministradores   		ctrlAdministradores;
	/**
	 * 
	 */
	/**
	 * Referência para o controlador do caso de uso "Manter Empresas"
	 */
	private CtrlManterVagas ctrlVagas;
	/**
	 * 
	 */
	private boolean autenticado = false;
	
	/**
	 * 
	 */
	private Usuario usuario = new Usuario(null, "(não autenticado)", null);
	
	/**
	 * Referência para a UI login do programa
	 */
	private UILogin          	uiLogin;	
	/**
	 * Referência para a UI home do programa
	 */
	private UIHome          	uiHome;	
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor do CtrlPrograma
	 */
	public CtrlSessaoUsuario(){
	}

	/* (non-Javadoc)
	 * @see controle.ICtrlCasoDeUso#iniciar()
	 */
	@Override
	public void iniciar() {
		// Recupera a UI associada à abertura da sessão
		this.uiLogin = (UILogin)ViewerManager.obterViewer(this, UILogin.class);
		// Inicializa os DAOs
		DAO.inicializarDAOs();
		// Solicita a exibição da uiPrincipal
		this.uiLogin.exibir();
	}

	/* (non-Javadoc)
	 * @see controle.ICtrlCasoDeUso#terminar()
	 */
	@Override
	public void terminar(){
		// Inicializa os DAOs
		DAO.fecharDAOs();
		// Método estático da classe System que encerra o programa
		//System.exit(0);
	}
	
	public boolean isAutenticado() {
		return autenticado;
	}

	public boolean isAdmin(){
		
		if(this.usuario.getId() == null)
			return false;
		Administrador objeto = new AdministradorDAO().lerPorId(this.usuario.getPessoa().getId());
		if(objeto == null)
			return false;
		else
		return true;
	}
	
	public ResponsavelEmpresa getResponsavel(){
		return 	new ResponsavelEmpresaDAO().lerPorId(this.usuario.getPessoa().getId());
	}
	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	/**
	 * 
	 */
	public void iniciarAutenticacao(String login, String senha) throws ControleException{
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuarioDoBanco = dao.lerPorLogin(login);
		if (usuarioDoBanco == null)
			throw new ControleException(new ErroDeControle("Usuário não existe"));
		else if(!usuarioDoBanco.ehSenhaCorreta(senha))
			throw new ControleException(new ErroDeControle("Senha inválida"));

		// guardar o obteto usuário
		this.setUsuario(usuarioDoBanco);
		this.autenticado = true;

		// Recupera a UI associada à abertura da sessão
		this.uiHome = (UIHome)ViewerManager.obterViewer(this, UIHome.class);
		// Solicita a exibição da uiPrincipal
		this.uiHome.exibir();
	}

	/**
	 * 
	 */
	public void acaoLogout(){
		this.usuario = null;
		this.autenticado = false;
	}
	/**
	 * 
	 */
	/** 
	 * 
	 */
	/** 
	 * 
	 */

	public void iniciarCasoDeUsoManterVagas() throws ControleException, DadosException {
		this.ctrlVagas = new CtrlManterVagas(this);
	}

	/**
	 * 
	 */
	public void terminarCasoDeUsoManterVagas() throws ControleException {
		if (this.ctrlVagas != null)
			this.ctrlVagas.terminar();
		this.ctrlVagas = null;
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoManterDepartamentos() throws ControleException, DadosException {
		this.ctrlDepartamentos = new CtrlManterDepartamentos(this);
	}
	
	/**
	 * 
	 */
	public void terminarCasoDeUsoManterDepartamentos() throws ControleException {
		if(this.ctrlDepartamentos != null)
			this.ctrlDepartamentos.terminar();
		this.ctrlDepartamentos = null;
	}
	
	/** 
	 * 
	 */


	public void iniciarCasoDeUsoManterResponsavelEmpresas() throws ControleException, DadosException {
		this.ctrlResponsavelEmpresas = new CtrlManterResponsavelEmpresas(this);
	}
	
	/**
	 * 
	 */
	public void terminarCasoDeUsoManterResponsavelEmpresas() throws ControleException {
		if(this.ctrlResponsavelEmpresas != null)
			this.ctrlResponsavelEmpresas.terminar();
		this.ctrlResponsavelEmpresas = null;
	}
	
	/** 
	 * 
	 */
	/** 
	 * 
	 */


	public void iniciarCasoDeUsoManterAdministradores() throws ControleException, DadosException {
		this.ctrlAdministradores = new CtrlManterAdministradores(this);
	}
	
	/**
	 * 
	 */
	public void terminarCasoDeUsoManterAdministradores() throws ControleException {
		if(this.ctrlAdministradores != null)
			this.ctrlAdministradores.terminar();
		this.ctrlAdministradores = null;
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoManterEmpresas() throws ControleException, DadosException {
		this.ctrlEmpresas = new CtrlManterEmpresas(this);
	}
	
	/**
	 * 
	 */
	public void terminarCasoDeUsoManterEmpresas() throws ControleException {
		if(this.ctrlEmpresas != null)
			this.ctrlEmpresas.terminar();
		this.ctrlEmpresas = null;
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoManterEmpregados() throws ControleException, DadosException{
		// Instanciando os controladores de caso de uso do sistema
		this.ctrlEmpregados = new CtrlManterEmpregados(this);
	}
	
	/**
	 *  
	 */
	public void terminarCasoDeUsoManterEmpregados() throws ControleException{
		if(this.ctrlEmpregados != null)
			this.ctrlEmpregados.terminar();
		this.ctrlEmpregados = null;
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoManterProjetos() throws ControleException, DadosException{
		// Instanciando os controladores de caso de uso do sistema
		this.ctrlProjetos = new CtrlManterProjetos(this);
	}
	
	/**
	 *  
	 */
	public void terminarCasoDeUsoManterProjetos() throws ControleException{
		if(this.ctrlProjetos != null)
			this.ctrlProjetos.terminar();
		this.ctrlProjetos = null;
	}
	
	/**
	 * O método main corresponde ao ponto inicial de execução
	 * de um programa em Java.
	 * @param args
	 */
	public static void main(String[] args) throws ControleException, DadosException {
		ICtrlCasoDeUso prg = new CtrlSessaoUsuario();
		prg.iniciar();
	}
}
