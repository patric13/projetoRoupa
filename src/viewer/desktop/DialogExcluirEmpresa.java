package viewer.desktop;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.util.DadosException;
import viewer.UIExcluirEmpresa;
import controller.CtrlExcluirEmpresa;
import controller.util.ControleException;
import dominio.Empresa;

public class DialogExcluirEmpresa implements UIExcluirEmpresa {
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para o controlador do caso de uso Excluir Empresa
	 */
	private final CtrlExcluirEmpresa ctrl;
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
	public DialogExcluirEmpresa(CtrlExcluirEmpresa ct) throws DadosException, ControleException {
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
		} catch (model.DadosException e) {
			// TODO Auto-generated catch block
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
	public void atualizarCampos(Empresa empresa) {
		
	}
}
