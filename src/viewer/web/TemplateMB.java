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

public class TemplateMB implements Serializable, UILogin {

	public final static Usuario NULL_OBJECT_USUARIO = new Usuario(null, "(não autenticado)", null);
	public final static String  CTRL_SESSAO = "CtrlSessao";
	
	/**
	 * Referência para o controlador
	 */
	private CtrlSessaoUsuario ctrlSessao;
	
	//
	// Métodos da interface UI
	//
	public TemplateMB() {
	}

	public TemplateMB(CtrlSessaoUsuario c) {
		this.ctrlSessao = c;
		JSFUtil.getSessionMap().put(CTRL_SESSAO, this.ctrlSessao);
	}

	public void criarUI() {
	}

	public CtrlSessaoUsuario getCtrlSessao() {
		return this.ctrlSessao;
	}

	
	public void setCtrlSessaoUsuario(CtrlSessaoUsuario ctrl){
		this.ctrlSessao = ctrl;
	}
	public void limpar() {
	}

	public void exibir() {
	}

	public void fechar() {
	}

	public boolean isAutenticado() {
		if(this.ctrlSessao == null) {
			this.ctrlSessao = (CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO);
			if(this.ctrlSessao == null)
				return false;
		}
		return this.ctrlSessao.isAutenticado();
	}

	public Usuario getUsuario() {
		if(this.ctrlSessao == null) {
			this.ctrlSessao = (CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO);
			if(this.ctrlSessao == null)
				return NULL_OBJECT_USUARIO;
		}
		return this.ctrlSessao.getUsuario();
	}
	public boolean isAdmin(){
		if(this.ctrlSessao == null) {
			this.ctrlSessao = (CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO);
			if(this.ctrlSessao == null)
				return false;
		}
		return this.ctrlSessao.isAdmin();
	}
	
}
