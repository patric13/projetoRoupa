package viewer.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroEmpresas;
import viewer.UIEmpresa;
import viewer.web.util.JSFUtil;
import controller.CtrlManterEmpresas;
import controller.util.ControleException;
import controller.util.ICtrlCasoDeUso;
import dominio.Empresa;
import dominio.dao.EmpresaDAO;

@ManagedBean(name = "empresaMB")
@SessionScoped
public class EmpresaMB extends TemplateMB implements Serializable, UIEmpresa  {
	private  ICtrlCasoDeUso ctrl = null;

	private Empresa empresa = new Empresa();
	private EmpresaDAO dao = new EmpresaDAO();

	private List<Empresa> empresas = null;
	
	
	
	
	
	public EmpresaMB(){
		
	}
	public EmpresaMB(ICtrlCasoDeUso c ) {
		this.ctrl = c;
		this.exibir();
		
	}
	
	public List<Empresa> getEmpresas() {
		if (this.empresas == null)
			this.empresas = new EmpresaDAO().lerTodos();

		return this.empresas;
	}

	public Empresa getEmpresa() {
		if((Empresa) JSFUtil.getSessionMap().get("empresa") != null)
		this.empresa =(Empresa) JSFUtil.getSessionMap().get("empresa");
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;

	}

	/**
	 * 
	 */
	public String acaoListar() {
		return "empresaListar";
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
		Empresa objetoDoBanco = this.dao.lerPorId(id);
		this.setEmpresa(objetoDoBanco);

		return "empresaEditar";
	}

	/**
	 * 
	 */
	public String acaoSalvar() {
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getEmpresa().getId() != null)
				&& (this.getEmpresa().getId().longValue() == 0))
			this.getEmpresa().setId(null);

		this.dao.salvar(this.getEmpresa());
		// limpa a lista
		this.empresas = null;
		this.setEmpresa(null);
		// limpar o objeto da página
		this.setEmpresa(new Empresa());
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.addMessage(null, new FacesMessage("Empresa adicionada com sucesso!", "Empresa adicionada com sucesso!"));
		return "home";
	}

	/**
	 * 
	 */
	public String acaoCancelar() {
		// limpar o objeto da página
		this.setEmpresa(new Empresa());

		return "empresaListar";
	}

	/**
	 * 
	 */
	public String acaoExcluir() {
		Long id = JSFUtil.getParametroLong("itemId");
		Empresa objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		// limpar o objeto da página
		this.setEmpresa(new Empresa());
		// limpa a lista
		this.empresas = null;

		return "empresaListar";
	}
	
	public void exibir() {
		JSFUtil.navigation("empresaEditar");
		
		
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
	public void atualizarCampos(Empresa empresa) {
		JSFUtil.getSessionMap().put("empresa", empresa);
		
	}
	

}
