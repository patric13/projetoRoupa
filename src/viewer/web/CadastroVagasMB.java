package viewer.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.swing.JOptionPane;

import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroVagas;
import viewer.UIExcluirVaga;
import viewer.web.util.JSFUtil;
import controller.CtrlExcluirVaga;
import controller.CtrlManterVagas;
import controller.util.ControleException;
import dominio.Vaga;
import dominio.dao.VagaDAO;

@ManagedBean(name = "cadastroVagasMB")
@RequestScoped
public class CadastroVagasMB implements Serializable, UICadastroVagas,UIExcluirVaga  {
	private CtrlManterVagas ctrl;
	private CtrlExcluirVaga ctrlex;
	private Vaga vaga = new Vaga();
	private VagaDAO dao = new VagaDAO();
	private Vaga selecionada;
	private List<Vaga> vagas = null;
	public final static String  CTRL_SESSAO = "CtrlVaga";
	
	
	
	
	public CadastroVagasMB(){
		
	}
	public CadastroVagasMB(CtrlManterVagas c ) {
		this.ctrl = c;
		
		
	}
	
	public CadastroVagasMB(CtrlExcluirVaga c ) {
		this.ctrlex = c;
		
		
	}
	
	public List<Vaga> getVagas() {
		if (this.vagas == null)
			this.vagas = new VagaDAO().lerTodos();

		return this.vagas;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	/**
	 * 
	 */
	

	/**
	 * 
	 */
	public void acaoAbrirInclusao() {
		this.ctrl = (CtrlManterVagas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		this.setVaga(new Vaga());
		solicitarExecucaoDeIncluirVaga();
	}

	public Vaga getSelecionada() {
		return selecionada;
	}
	public void setSelecionada(Vaga selecionada) {
		this.selecionada = selecionada;
	}
	/**
	 * 
	 */
	public void acaoAbrirAlteracao() {
		this.ctrl = (CtrlManterVagas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeAlterarVaga();

	}
	/**
	 * 
	 */
	public void acaoAbrirExclusao(){
		this.ctrl = (CtrlManterVagas) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		solicitarExecucaoDeExcluirVaga();
	}
	/**
	 * 
	 */
	

	
	
	@Override
	public void exibirObjetos(List<IDadosParaTabela> objetos) {
		this.getVagas();
		
		
	}
	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solicitarExecucaoDeIncluirVaga() {
		try {
			JSFUtil.getSessionMap().put("Vaga", null);
			this.ctrl.iniciarCasoDeUsoIncluirVaga();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeExcluirVaga() {
		
		Vaga objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
		this.vagas.remove(selecionada);
		this.selecionada = null;
		try {
			this.ctrl.iniciarCasoDeUsoExcluirVaga(objetoDoBanco);
		} catch (DadosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarExecucaoDeAlterarVaga() {
		try {
			Vaga objetoDoBanco = this.dao.lerPorId(this.selecionada.getId());
			this.vagas.remove(selecionada);
			this.selecionada = null;
			this.setVaga(objetoDoBanco);

			this.ctrl.iniciarCasoDeUsoAlterarVaga(vaga);
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}
	@Override
	public void solicitarTerminoDeManterVagas() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void exibir() {
		JSFUtil.getSessionMap().put(CTRL_SESSAO, this.ctrl);
		JSFUtil.navigation("vagaListar");
	
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
