package viewer.web;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.util.DadosException;
import viewer.UIHome;
import viewer.UILogin;
import viewer.web.util.JSFUtil;
import controller.CtrlSessaoUsuario;
import controller.util.ControleException;
import dominio.Usuario;

@ManagedBean(name = "HomeMB")
@ViewScoped
public class HomeMB extends TemplateMB implements Serializable, UIHome {
	
	//
	// Métodos da interface UI
	//
	public HomeMB() {
	}

	public HomeMB(CtrlSessaoUsuario c) {
		super(c);
		this.criarUI();
	}

	public void exibir() {
		JSFUtil.redirecionar("home.xhtml");
	}

	public void iniciarCasoDeUsoManterEmpresas() throws ControleException, DadosException{
		this.setCtrlSessaoUsuario((CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO));
		this.getCtrlSessao().iniciarCasoDeUsoManterEmpresas();
	}
	
	public void iniciarCasoDeUsoManterResponsavelEmpresas() throws ControleException, DadosException{
		this.setCtrlSessaoUsuario((CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO));
		this.getCtrlSessao().iniciarCasoDeUsoManterResponsavelEmpresas();
	}
	
	public void iniciarCasoDeUsoManterAdministradores() throws ControleException, DadosException{
		this.setCtrlSessaoUsuario((CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO));
		this.getCtrlSessao().iniciarCasoDeUsoManterAdministradores();
	}
	
	public void iniciarCasoDeUsoManterVagas() throws ControleException, DadosException{
		this.setCtrlSessaoUsuario((CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO));
		this.getCtrlSessao().iniciarCasoDeUsoManterVagas();
	}

}
