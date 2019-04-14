package viewer.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.swing.JOptionPane;

import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroResponsavelEmpresas;
import viewer.UIExcluirResponsavelEmpresa;
import viewer.web.util.JSFUtil;
import controller.CtrlExcluirResponsavelEmpresa;
import controller.CtrlManterResponsavelEmpresas;
import controller.util.ControleException;
import dominio.ResponsavelEmpresa;
import dominio.dao.ResponsavelEmpresaDAO;

@ManagedBean(name = "cadastroResponsavelEmpresasMB")
@RequestScoped
public class CadastroResponsavelEmpresasMB implements Serializable, UICadastroResponsavelEmpresas,UIExcluirResponsavelEmpresa  {
	private CtrlManterResponsavelEmpresas ctrl;
	private CtrlExcluirResponsavelEmpresa ctrlex;
	private ResponsavelEmpresa responsavelEmpresa = new ResponsavelEmpresa();
	private ResponsavelEmpresaDAO dao = new ResponsavelEmpresaDAO();
	private ResponsavelEmpresa selecionada;
	private List<ResponsavelEmpresa> responsavelEmpresas = null;
	public final static String  CTRL_SESSAO = "CtrlResponsavelEmpresa";
	
	
	
	
	public CadastroResponsavelEmpresasMB(){
		
	}
	public CadastroResponsavelEmpresasMB(CtrlManterResponsavelEmpresas c ) {
		this.ctrl = c;
		
		
	}
	
	public CadastroResponsavelEmpresasMB(CtrlExcluirResponsavelEmpresa c ) {
		this.ctrlex = c;
		
		
	}
	
	public List<ResponsavelEmpresa> getResponsavelEmpresas() {
		if (this.responsavelEmpresas == null)
			this.responsavelEmpresas = dao.lerTodos();

		return this.responsavelEmpresas;
	}

	public ResponsavelEmpresa getResponsavelEmpresa() {
		return responsavelEmpresa;
	}

	public void setResponsavelEmpresa(ResponsavelEmpresa responsavelEmpresa) {
		this.responsavelEmpresa = responsavelEmpresa;
	}

	/**
	 * 
	 */
	

	/**
	 * 
	 */
	public void acaoAbrirInclusao() {
		this.ctrl = (CtrlManterResponsavelEmpresas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		this.setResponsavelEmpresa(new ResponsavelEmpresa());
		solicitarExecucaoDeIncluirResponsavelEmpresa();
	}

	public ResponsavelEmpresa getSelecionada() {
		return selecionada;
	}
	public void setSelecionada(ResponsavelEmpresa selecionada) {
		this.selecionada = selecionada;
	}
	/**
	 * 
	 */
	public void acaoAbrirAlteracao() {
		this.ctrl = (CtrlManterResponsavelEmpresas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeAlterarResponsavelEmpresa();

	}
	/**
	 * 
	 */
	public void acaoAbrirExclusao(){
		this.ctrl = (CtrlManterResponsavelEmpresas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeExcluirResponsavelEmpresa();
	}
	/**
	 * 
	 */
	

	
	
	@Override
	public void exibirObjetos(List<IDadosParaTabela> objetos) {
		this.getResponsavelEmpresas();
		
		
	}
	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solicitarExecucaoDeIncluirResponsavelEmpresa() {
		try {
			JSFUtil.getSessionMap().put("ResponsavelEmpresa", null);
			this.ctrl.iniciarCasoDeUsoIncluirResponsavelEmpresa();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeExcluirResponsavelEmpresa() {
		
		ResponsavelEmpresa objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
		this.responsavelEmpresas.remove(selecionada);
		this.selecionada = null;
		try {
			this.ctrl.iniciarCasoDeUsoExcluirResponsavelEmpresa(objetoDoBanco);
		} catch (DadosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeAlterarResponsavelEmpresa() {
		try {
			ResponsavelEmpresa objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
			this.responsavelEmpresas.remove(selecionada);
			this.selecionada = null;
			this.setResponsavelEmpresa(objetoDoBanco);

			this.ctrl.iniciarCasoDeUsoAlterarResponsavelEmpresa(responsavelEmpresa);
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarTerminoDeManterResponsavelEmpresas() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void exibir() {
		JSFUtil.getSessionMap().put(CTRL_SESSAO, this.ctrl);
		JSFUtil.navigation("responsavelEmpresaListar");
	
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
