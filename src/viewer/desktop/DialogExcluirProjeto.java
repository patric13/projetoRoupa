package viewer.desktop;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.util.DadosException;
import viewer.UIExcluirProjeto;
import controller.CtrlExcluirProjeto;
import controller.util.ControleException;

public class DialogExcluirProjeto implements UIExcluirProjeto {
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para o controlador do caso de uso Excluir Projeto
	 */
	private final CtrlExcluirProjeto ctrl;
	/**
	 * Content Pane da Janela
	 */
	private JPanel contentPane;
	/**
	 * Mensagem de Exclusão
	 */
	private String mensagem;
	
	/**
	 * Construtor para exclusão
	 * @param ct
	 * @throws DadosException
	 * @throws ControleException
	 */
	public DialogExcluirProjeto(CtrlExcluirProjeto ct) throws DadosException, ControleException {
		this.ctrl = ct;
	}

	/**
	 * Criação da UI
	 */
	public void criarUI() {
	}

	/* (non-Javadoc)
	 * @see viewer.UIProjeto#solicitarExecucaoDeEfetivacao()
	 */
	@Override
	public void solicitarExecucaoDeEfetivacao() {
		try {
			ctrl.excluir();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see viewer.UIProjeto#solicitarCancelamentoDeEfetivacao()
	 */
	@Override
	public void solicitarCancelamentoDeEfetivacao() {
		try {
			ctrl.cancelarExcluir();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see viewer.UIProjeto#fechar()
	 */
	@Override
	public void exibir() {
		if(JOptionPane.showConfirmDialog(null, this.mensagem) == JOptionPane.YES_OPTION)
			this.solicitarExecucaoDeEfetivacao();
		else
			this.solicitarCancelamentoDeEfetivacao();
	}

	/* (non-Javadoc)
	 * @see viewer.UIProjeto#fechar()
	 */
	@Override
	public void fechar() {
	}

	/* (non-Javadoc)
	 * @see viewer.UIProjeto#fechar()
	 */
	@Override
	public void limpar() {
	}

	/* (non-Javadoc)
	 * @see viewer.UIProjeto#atualizarCampos(java.lang.String, java.lang.String)
	 */
	@Override
	public void atualizarCampos(String nome, List<Object> todosDeptos, List<Object> todosEmps, Object depto, List<Object> empsSelecionados) {
		this.mensagem = "Deseja excluir o Projeto " + nome + " do departamento " + depto + "?";
	}
}
