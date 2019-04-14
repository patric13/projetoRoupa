package viewer.web;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.util.DadosException;
import viewer.UILogin;
import viewer.web.util.JSFUtil;
import controller.CtrlSessaoUsuario;
import controller.util.ControleException;
import dominio.Usuario;

@ManagedBean(name = "LoginMB")
@SessionScoped
public class LoginMB extends TemplateMB implements Serializable, UILogin {
	/**
	 * Dados presentes na UI
	 */
	private String login;
	/**
	 * Dados presentes na UI
	 */
	private String senha;
	
	//
	// Métodos da interface UI
	//
	public LoginMB() {
	}

	public LoginMB(CtrlSessaoUsuario c) {
		super(c);
		this.criarUI();
	}

	public void exibir() {
		JSFUtil.redirecionar("xhtml/login.xhtml");
	}

	//
	// Métodos do ManagedBean
	//
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * processa a ação ação de autenticar
	 */
	public void acaoAutenticar() {
		try {
			System.out.println(this.getCtrlSessao());
			this.getCtrlSessao().iniciarAutenticacao(login, senha);
		} catch (ControleException e) {
			JSFUtil.retornarMensagemErro(e.getMessage(), null, null);
		
		}
	}
	
	public void acaoLogout(){
		this.getCtrlSessao().acaoLogout();
		JSFUtil.redirecionar("login.xhtml");
	}
	
	public void acaoExpirado(){
		JSFUtil.redirecionar("../index.jsp");
	}
}
