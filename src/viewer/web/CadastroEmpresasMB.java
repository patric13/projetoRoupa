package viewer.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroEmpresas;
import viewer.UIExcluirEmpresa;
import viewer.web.util.JSFUtil;
import controller.CtrlExcluirEmpresa;
import controller.CtrlManterEmpresas;
import controller.CtrlSessaoUsuario;
import controller.util.ControleException;
import dominio.Empresa;
import dominio.dao.EmpresaDAO;

@ManagedBean(name = "cadastroEmpresasMB")
@RequestScoped
public class CadastroEmpresasMB implements Serializable, UICadastroEmpresas,UIExcluirEmpresa  {
	private CtrlManterEmpresas ctrl;
	private CtrlExcluirEmpresa ctrlex;
	private Empresa empresa = new Empresa();
	private EmpresaDAO dao = new EmpresaDAO();
	private Empresa selecionada;
	private List<Empresa> empresas = null;
	public final static String  CTRL_SESSAO = "CtrlEmpresa";
	
	
	
	
	public CadastroEmpresasMB(){
		
	}
	public CadastroEmpresasMB(CtrlManterEmpresas c ) {
		this.ctrl = c;
		
		
	}
	
	public CadastroEmpresasMB(CtrlExcluirEmpresa c ) {
		this.ctrlex = c;
		
		
	}
	
	public List<Empresa> getEmpresas() {
		if (this.empresas == null)
			this.empresas = new EmpresaDAO().lerTodos();

		return this.empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * 
	 */
	

	/**
	 * 
	 */
	public void acaoAbrirInclusao() {
		this.ctrl = (CtrlManterEmpresas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		this.setEmpresa(new Empresa());
		solicitarExecucaoDeIncluirEmpresa();
	}

	public Empresa getSelecionada() {
		return selecionada;
	}
	public void setSelecionada(Empresa selecionada) {
		this.selecionada = selecionada;
	}
	/**
	 * 
	 */
	public void acaoAbrirAlteracao() {
		this.ctrl = (CtrlManterEmpresas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeAlterarEmpresa();

	}
	/**
	 * 
	 */
	public void acaoAbrirExclusao(){
		this.ctrl = (CtrlManterEmpresas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeExcluirEmpresa();
	}
	/**
	 * 
	 */
	

	
	
	@Override
	public void exibirObjetos(List<IDadosParaTabela> objetos) {
		this.getEmpresas();
		
		
	}
	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solicitarExecucaoDeIncluirEmpresa() {
		try {
			JSFUtil.getSessionMap().put("empresa", null);
			this.ctrl.iniciarCasoDeUsoIncluirEmpresa();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeExcluirEmpresa() {
		
		Empresa objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
		this.empresas.remove(selecionada);
		this.selecionada = null;
		try {
			this.ctrl.iniciarCasoDeUsoExcluirEmpresa(objetoDoBanco);
		} catch (DadosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeAlterarEmpresa() {
		try {
			Empresa objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
			this.empresas.remove(selecionada);
			this.selecionada = null;
			this.setEmpresa(objetoDoBanco);

			this.ctrl.iniciarCasoDeUsoAlterarEmpresa(empresa);
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarTerminoDeManterEmpresas() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void exibir() {
		JSFUtil.getSessionMap().put(CTRL_SESSAO, this.ctrl);
		JSFUtil.navigation("empresaListar");
	
	}
	public void exibirExcluir() {
		JSFUtil.getSessionMap().put(CTRL_SESSAO, this.ctrl);
		this.solicitarExecucaoDeEfetivacao();
	
		
	}
	
	

	@Override
	public void fechar() {
		
	}
	@Override
	public void criarUI() {
		
	}
	@Override
	public void solicitarExecucaoDeEfetivacao() {
		try {
			ctrlex.excluir();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (model.DadosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarCancelamentoDeEfetivacao() {
		try {
			ctrlex.cancelarExcluir();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void atualizarCampos() {

		
	}
	

}
