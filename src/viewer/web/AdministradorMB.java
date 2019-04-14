package viewer.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import viewer.UIAdministrador;
import viewer.web.util.JSFUtil;
import controller.util.ICtrlCasoDeUso;
import dominio.Administrador;
import dominio.dao.AdministradorDAO;

@ManagedBean(name = "administradorMB")
@SessionScoped
public class AdministradorMB extends TemplateMB implements Serializable, UIAdministrador  {
	private  ICtrlCasoDeUso ctrl = null;

	private Administrador administrador = new Administrador();
	private AdministradorDAO dao = new AdministradorDAO();

	private List<Administrador> administradores = null;
	
	
	
	
	
	public AdministradorMB(){
		
	}
	public AdministradorMB(ICtrlCasoDeUso c ) {
		this.ctrl = c;
		this.exibir();
		
	}
	
	public List<Administrador> getAdministradores() {
		if (this.administradores == null)
			this.administradores = new AdministradorDAO().lerTodos();

		return this.administradores;
	}

	public Administrador getAdministrador() {
		if((Administrador) JSFUtil.getSessionMap().get("administrador") != null)
		this.administrador =(Administrador) JSFUtil.getSessionMap().get("administrador");
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;

	}

	/**
	 * 
	 */
	public String acaoListar() {
		return "administrador";
	}

	/**
	 * 
	 */
	
	/**
	 * 
	 */
	public String acaoAbrirAlteracao() {
		// pega o ID escolhido que veio no parâmetro
		Long id = JSFUtil.getParametroLong("itemId");
		Administrador objetoDoBanco = this.dao.lerPorId(id);
		this.setAdministrador(objetoDoBanco);

		return "administradorEditar";
	}

	/**
	 * 
	 */
	public String acaoSalvar() {
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getAdministrador().getId() != null)
				&& (this.getAdministrador().getId().longValue() == 0))
			this.getAdministrador().setId(null);

		this.dao.salvar(this.getAdministrador());
		// limpa a lista
		this.administradores = null;
		this.setAdministrador(null);
		// limpar o objeto da página
		this.setAdministrador(new Administrador());

		return "home";
	}

	/**
	 * 
	 */
	public String acaoCancelar() {
		// limpar o objeto da página
		this.setAdministrador(new Administrador());

		return "administradorListar";
	}

	/**
	 * 
	 */
	public String acaoExcluir() {
		Long id = JSFUtil.getParametroLong("itemId");
		Administrador objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		// limpar o objeto da página
		this.setAdministrador(new Administrador());
		// limpa a lista
		this.administradores = null;

		return "administradorListar";
	}
	
	public void exibir() {
		JSFUtil.navigation("administradorEditar");
		
		
	}
	@Override
	public void fechar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solicitarExecucaoDeEfetivacao() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solicitarCancelamentoDeEfetivacao() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void atualizarCampos(Administrador administrador) {
		JSFUtil.getSessionMap().put("administrador", administrador);
		
	}
	

}
