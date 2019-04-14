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

import viewer.UIDepartamento;
import model.util.DadosException;
import controller.CtrlAlterarDepartamento;
import controller.CtrlIncluirDepartamento;
import controller.util.ControleException;
import controller.util.ICtrlCasoDeUso;

public class JanelaDepartamento extends JFrame implements UIDepartamento {
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para o controlador do caso de uso Incluir Departamento
	 */
	private final CtrlIncluirDepartamento ctrlIncluir;
	/**
	 * Referência para o controlador do caso de uso Alterar Departamento
	 */
	private final CtrlAlterarDepartamento ctrlAlterar;
	/**
	 * Indica se estou fazendo uma operação de inclusão ou alteração
	 */
	private boolean ehAlteração;
	/**
	 * Content Pane da Janela
	 */
	private JPanel contentPane;
	/**
	 * TextField para a sigla do departamento
	 */
	private JTextField tfSigla;
	/**
	 * TextField para o nome do departamento
	 */
	private JTextField tfNome;

	/**
	 * Construtor para inclusão
	 * @param ct
	 * @throws DadosException
	 * @throws ControleException
	 */
	public JanelaDepartamento(CtrlIncluirDepartamento ct) throws DadosException, ControleException {
		this.ehAlteração = false;
		this.ctrlIncluir = ct;
		this.ctrlAlterar = null;
		this.criarUI();
	}

	/**
	 * Construtor para alteração
	 * @param ct
	 * @throws DadosException
	 * @throws ControleException
	 */
	public JanelaDepartamento(CtrlAlterarDepartamento ct) throws DadosException, ControleException {
		this.ehAlteração = true;
		this.ctrlIncluir = null;
		this.ctrlAlterar = ct;
		this.criarUI();
	}
	
	/**
	 * Criação da UI
	 */
	public void criarUI() {
		setTitle("Departamento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSigla = new JLabel("Sigla:");
		lblSigla.setBounds(20, 11, 46, 14);
		contentPane.add(lblSigla);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 48, 46, 14);
		contentPane.add(lblNome);

		tfSigla = new JTextField();
		tfSigla.setBounds(76, 8, 86, 20);
		contentPane.add(tfSigla);
		tfSigla.setColumns(10);

		tfNome = new JTextField();
		tfNome.setBounds(76, 45, 334, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeEfetivacao();
			}
		});
		btnOk.setBounds(73, 95, 143, 23);
		contentPane.add(btnOk);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarCancelamentoDeEfetivacao();
			}
		});
		btnCancelar.setBounds(256, 95, 154, 23);
		contentPane.add(btnCancelar);
	}

	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#solicitarExecucaoDeEfetivacao()
	 */
	@Override
	public void solicitarExecucaoDeEfetivacao() {
		try {
			// Recupero os valores digitados nos textfields
			String sigla = tfSigla.getText();
			String nome = tfNome.getText();
			// Verifico qual é a operação que estou fazendo
			// e notifico ao controlador
			if(!ehAlteração)
				ctrlIncluir.incluir(sigla, nome);
			else
				ctrlAlterar.alterar(sigla, nome);
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
			if (!ehAlteração)
				ctrlIncluir.cancelarIncluir();
			else
				ctrlAlterar.cancelarAlterar();
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
		this.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#fechar()
	 */
	@Override
	public void fechar() {
		this.setVisible(false);
	}
	
	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#limpar()
	 */
	@Override
	public void limpar() {
		this.tfSigla.setText(null);
		this.tfNome.setText(null);
	}

	/* (non-Javadoc)
	 * @see viewer.UIDepartamento#atualizarCampos(java.lang.String, java.lang.String)
	 */
	@Override
	public void atualizarCampos(String sigla, String nome) {
		limpar();
		this.tfSigla.setText(sigla);
		this.tfNome.setText(nome);
	}
}
