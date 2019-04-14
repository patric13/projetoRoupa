package viewer.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import javax.faces.bean.ViewScoped;

import controller.CtrlSessaoUsuario;

@ManagedBean(name = "inicioMB")
@ViewScoped
public class InicioMB implements Serializable
{
	private static final long serialVersionUID = 1L;

	public void acaoInicio() {
		CtrlSessaoUsuario ctrl = new CtrlSessaoUsuario();
		ctrl.iniciar();
		
	}
}
