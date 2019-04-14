package viewer.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.util.DadosException;
import viewer.UIExcluirDepartamento;
import controller.CtrlAlterarDepartamento;
import controller.CtrlExcluirDepartamento;
import controller.CtrlIncluirDepartamento;
import controller.util.ControleException;

public class DialogExcluirDepartamento implements UIExcluirDepartamento {
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para o controlador do caso de uso Excluir Departamento
	 */
	private final CtrlExcluirDepartamento ctrl;
	/**
	 * Content Pane da Janela
	 */
	private JPanel contentPane;

	private String mensagem;
	
	/**
	 * Construtor para exclusão
	 * @param ct
	 * @throws DadosException
	 * @throws ControleException
	 */
	public DialogExcluirDepartamento(CtrlExcluirDepartamento ct) throws DadosException, ControleException {
		this.ctrl = ct;
	}

	/**
	 * Criação da UI
	 */
	public void criarUI() {
	}

	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#solicitarExecucaoDeEfetivacao()
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
	 * @see viewer.UIDepartamento#solicitarCancelamentoDeEfetivacao()
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
	 * @see viewer.UIDepartamento#fechar()
	 */
	@Override
	public void exibir() {
		if(JOptionPane.showConfirmDialog(null, this.mensagem) == JOptionPane.YES_OPTION)
			this.solicitarExecucaoDeEfetivacao();
		else
			this.solicitarCancelamentoDeEfetivacao();
	}

	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#fechar()
	 */
	@Override
	public void fechar() {
	}

	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#limpar()
	 */
	@Override
	public void limpar() {
	}

	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#atualizarCampos(java.lang.String, java.lang.String)
	 */
	@Override
	public void atualizarCampos(String sigla, String nome) {
		this.mensagem = "Deseja excluir o Departamento " + sigla + "-" + nome + "?";
	}
}
