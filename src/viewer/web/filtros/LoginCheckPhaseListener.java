package viewer.web.filtros;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import viewer.web.LoginMB;
import viewer.web.util.JSFUtil;
import controller.CtrlSessaoUsuario;

@SuppressWarnings("serial")
public class LoginCheckPhaseListener implements PhaseListener
{

	// CICLO DE VIDA DO JSF
	// 1. Restore view
	// 2. Apply request values; process events
	// 3. Process validations; process events
	// 4. Update model values; process events
	// 5. Invoke application; process events
	// 6. Render response

	@Override
	public PhaseId getPhaseId()
	{
		return PhaseId.RESTORE_VIEW; // executar na primeira fase (início do
												// processamento)
	}

	@Override
	public void afterPhase(PhaseEvent event)
	{
		boolean usuarioAutenticado = false;
		CtrlSessaoUsuario ctrlSessaoUsuario = (CtrlSessaoUsuario) JSFUtil.getVariavelApplication("CtrlSessaoUsuario");
		LoginMB loginMB = (LoginMB) JSFUtil.getVariavelApplication("LoginMB");
		if (ctrlSessaoUsuario == null)
			ctrlSessaoUsuario = (CtrlSessaoUsuario)JSFUtil.getSessionMap().get("Ctrl");
		if (ctrlSessaoUsuario == null)
			ctrlSessaoUsuario = loginMB.getCtrlSessao();
		if (ctrlSessaoUsuario != null)
			usuarioAutenticado = ctrlSessaoUsuario.isAutenticado();

		// ------------------------------------
		FacesContext contexto = event.getFacesContext();

		// Check to see if they are on the login page.
		boolean estaNaPaginaDeLogin = false;
		boolean estaNaPaginaInicial = false;
		String str = contexto.getViewRoot().getViewId();
		
		estaNaPaginaDeLogin = contexto.getViewRoot().getViewId().lastIndexOf("login") > -1 ? true : false;
		if (!estaNaPaginaDeLogin) {
			estaNaPaginaInicial = contexto.getViewRoot().getViewId().lastIndexOf("inicio") > -1 ? true : false;
			if (!estaNaPaginaInicial) 
				estaNaPaginaDeLogin = contexto.getViewRoot().getViewId().lastIndexOf("_expirado") > -1 ? true : false;
		}
		if(!estaNaPaginaDeLogin && !usuarioAutenticado && !estaNaPaginaInicial)
		{
			NavigationHandler nh = contexto.getApplication().getNavigationHandler();
			nh.handleNavigation(contexto, null, "_expirado.jsf");
		}

	}

	@Override
	public void beforePhase(PhaseEvent event)
	{

	}

}
