package viewer.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroResponsavelEmpresas;
import viewer.UIResponsavelEmpresa;
import viewer.web.util.JSFUtil;
import controller.CtrlManterResponsavelEmpresas;
import controller.util.ControleException;
import controller.util.ICtrlCasoDeUso;
import dominio.Empresa;
import dominio.ResponsavelEmpresa;
import dominio.dao.EmpresaDAO;
import dominio.dao.ResponsavelEmpresaDAO;

@ManagedBean(name = "responsavelEmpresaMB")
@SessionScoped
public class ResponsavelEmpresaMB extends TemplateMB implements Serializable, UIResponsavelEmpresa  {
	private  ICtrlCasoDeUso ctrl = null;

	private ResponsavelEmpresa responsavelEmpresa = new ResponsavelEmpresa();
	private ResponsavelEmpresaDAO dao = new ResponsavelEmpresaDAO();
	private List<Empresa> empresas = null;
	private List<ResponsavelEmpresa> responsavelEmpresas = null;
	private Empresa empresa;
	
	
	
	
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public ResponsavelEmpresaMB(){
		
	}
	public ResponsavelEmpresaMB(ICtrlCasoDeUso c ) {
		this.ctrl = c;
		this.exibir();
		
	}
	
	

	
	public List<Empresa> getEmpresas() {
		if (this.empresas == null)
			this.empresas = new EmpresaDAO().lerTodos();

		return this.empresas;

	}
	
	public List<ResponsavelEmpresa> getResponsavelEmpresas() {
		if (this.responsavelEmpresas == null)
			this.responsavelEmpresas = new ResponsavelEmpresaDAO().lerTodos();

		return this.responsavelEmpresas;
	}

	public ResponsavelEmpresa getResponsavelEmpresa() {
		if((ResponsavelEmpresa) JSFUtil.getSessionMap().get("responsavelEmpresa") != null)
		this.responsavelEmpresa =(ResponsavelEmpresa) JSFUtil.getSessionMap().get("responsavelEmpresa");
		return responsavelEmpresa;
	}

	public void setResponsavelEmpresa(ResponsavelEmpresa responsavelEmpresa) {
		this.responsavelEmpresa = responsavelEmpresa;

	}

	/**
	 * 
	 */
	public String acaoListar() {
		return "responsavelEmpresaListar";
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
		ResponsavelEmpresa objetoDoBanco = this.dao.lerPorId(id);
		this.setResponsavelEmpresa(objetoDoBanco);

		return "responsavelEmpresaEditar";
	}

	/**
	 * 
	 */
	public String acaoSalvar() {
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getResponsavelEmpresa().getId() != null)
				&& (this.getResponsavelEmpresa().getId().longValue() == 0))
			this.getResponsavelEmpresa().setId(null);

		this.dao.salvar(this.getResponsavelEmpresa());
		// limpa a lista
		this.responsavelEmpresas = null;
		this.setResponsavelEmpresa(null);
		// limpar o objeto da página
		this.setResponsavelEmpresa(new ResponsavelEmpresa());

		return "home";
	}

	/**
	 * 
	 */
	public String acaoCancelar() {
		// limpar o objeto da página
		this.setResponsavelEmpresa(new ResponsavelEmpresa());

		return "responsavelEmpresaListar";
	}

	/**
	 * 
	 */
	public String acaoExcluir() {
		Long id = JSFUtil.getParametroLong("itemId");
		ResponsavelEmpresa objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		// limpar o objeto da página
		this.setResponsavelEmpresa(new ResponsavelEmpresa());
		// limpa a lista
		this.responsavelEmpresas = null;

		return "responsavelEmpresaListar";
	}
	
	public void exibir() {
		JSFUtil.navigation("responsavelEmpresaEditar");
		
		
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
	public void atualizarCampos(ResponsavelEmpresa responsavelEmpresa) {
		JSFUtil.getSessionMap().put("responsavelEmpresa", responsavelEmpresa);
		
	}
	

}
