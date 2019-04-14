package viewer.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.swing.JOptionPane;

import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroAdministradores;
import viewer.UIExcluirAdministrador;
import viewer.web.util.JSFUtil;
import controller.CtrlExcluirAdministrador;
import controller.CtrlManterAdministradores;
import controller.util.ControleException;
import dominio.Administrador;
import dominio.dao.AdministradorDAO;

@ManagedBean(name = "cadastroAdministradoresMB")
@RequestScoped
public class CadastroAdministradoresMB implements Serializable, UICadastroAdministradores,UIExcluirAdministrador  {
	private CtrlManterAdministradores ctrl;
	private CtrlExcluirAdministrador ctrlex;
	private Administrador administrador = new Administrador();
	private AdministradorDAO dao = new AdministradorDAO();
	private Administrador selecionada;
	private List<Administrador> administradores = null;
	public final static String  CTRL_SESSAO = "CtrlAdministrador";
	
	
	
	
	public CadastroAdministradoresMB(){
		
	}
	public CadastroAdministradoresMB(CtrlManterAdministradores c ) {
		this.ctrl = c;
		
		
	}
	
	public CadastroAdministradoresMB(CtrlExcluirAdministrador c ) {
		this.ctrlex = c;
		
		
	}
	
	public List<Administrador> getAdministradores() {
		if (this.administradores == null)
			this.administradores = dao.lerTodos();

		return this.administradores;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	/**
	 * 
	 */
	

	/**
	 * 
	 */
	public void acaoAbrirInclusao() {
		this.ctrl = (CtrlManterAdministradores) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		this.setAdministrador(new Administrador());
		solicitarExecucaoDeIncluirAdministrador();
	}

	public Administrador getSelecionada() {
		return selecionada;
	}
	public void setSelecionada(Administrador selecionada) {
		this.selecionada = selecionada;
	}
	/**
	 * 
	 */
	public void acaoAbrirAlteracao() {
		this.ctrl = (CtrlManterAdministradores) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeAlterarAdministrador();

	}
	/**
	 * 
	 */
	public void acaoAbrirExclusao(){
		this.ctrl = (CtrlManterAdministradores) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeExcluirAdministrador();
	}
	/**
	 * 
	 */
	

	
	
	@Override
	public void exibirObjetos(List<IDadosParaTabela> objetos) {
		this.getAdministradores();
		
		
	}
	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solicitarExecucaoDeIncluirAdministrador() {
		try {
			JSFUtil.getSessionMap().put("Administrador", null);
			this.ctrl.iniciarCasoDeUsoIncluirAdministrador();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeExcluirAdministrador() {
		
		Administrador objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
		this.administradores.remove(selecionada);
		this.selecionada = null;
		try {
			this.ctrl.iniciarCasoDeUsoExcluirAdministrador(objetoDoBanco);
		} catch (DadosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeAlterarAdministrador() {
		try {
			Administrador objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
			this.administradores.remove(selecionada);
			this.selecionada = null;
			this.setAdministrador(objetoDoBanco);

			this.ctrl.iniciarCasoDeUsoAlterarAdministrador(administrador);
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarTerminoDeManterAdministradores() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void exibir() {
		JSFUtil.getSessionMap().put(CTRL_SESSAO, this.ctrl);
		JSFUtil.navigation("administradorListar");
	
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
