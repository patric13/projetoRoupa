package viewer.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Empregado;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UIEmpregado;
import viewer.desktop.util.JIntField;
import viewer.util.InterfaceException;
import controller.CtrlAlterarEmpregado;
import controller.CtrlAlterarEmpregado;
import controller.CtrlIncluirEmpregado;
import controller.CtrlIncluirEmpregado;
import controller.util.ControleException;
import controller.util.ICtrlCasoDeUso;

public class JanelaEmpregado extends JFrame implements UIEmpregado {
	//
	// CONSTANTES
	//
	public final static Object SELECAO_NULA = "Selecione...";
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para o controlador do caso de uso Incluir Empregado
	 */
	private final CtrlIncluirEmpregado ctrlIncluir;
	/**
	 * Referência para o controlador do caso de uso Alterar Empregado
	 */
	private final CtrlAlterarEmpregado ctrlAlterar;
	/**
	 * Indica se estou fazendo uma operação de inclusão ou alteração
	 */
	private boolean ehAlteração;
	/**
	 * Content Pane da Janela
	 */
	private JPanel contentPane;
	/**
	 * TextField para o cpf do empregado
	 */
	private JTextField tfCpf;
	/**
	 * TextField para a matr funcional do empregado
	 */
	private JTextField tfMatrFuncional;
	/**
	 * TextField para o nome do empregado
	 */
	private JTextField tfNome;
	/**
	 * ComboBox para seleção do Empregado do empregado
	 */
	private JComboBox  cbDeptos;
	
	/**
	 * Construtor para inclusão
	 * @param ct
	 * @throws DadosException
	 * @throws ControleException
	 */
	public JanelaEmpregado(CtrlIncluirEmpregado ct) throws DadosException, ControleException {
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
	public JanelaEmpregado(CtrlAlterarEmpregado ct) throws DadosException, ControleException {
		this.ehAlteração = true;
		this.ctrlIncluir = null;
		this.ctrlAlterar = ct;
		this.criarUI();
	}
	
	/**
	 * Criação da UI
	 */
	public void criarUI() {
		setTitle("Empregado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCpf = new JLabel("Cpf:");
		lblCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCpf.setBounds(42, 14, 46, 14);
		contentPane.add(lblCpf);
		
		JLabel lblMatrculaFuncional = new JLabel("Matrícula:");
		lblMatrculaFuncional.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMatrculaFuncional.setBounds(0, 45, 88, 14);
		contentPane.add(lblMatrculaFuncional);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(42, 76, 46, 14);
		contentPane.add(lblNome);
		
		tfCpf = new JTextField();
		tfCpf.setBounds(98, 11, 86, 20);
		contentPane.add(tfCpf);
		tfCpf.setColumns(10);
		
		tfMatrFuncional = new JIntField();
		tfMatrFuncional.setColumns(10);
		tfMatrFuncional.setBounds(98, 42, 86, 20);
		contentPane.add(tfMatrFuncional);

		tfNome = new JTextField();
		tfNome.setBounds(98, 73, 334, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);
		
		JButton btnOk = new JButton("Ok");

		// Implementação do Listener do Botão com uma Classe Interna formalmente declarada (final do código)
		btnOk.addActionListener( new ObservadorDoBotaoOk() );
		btnOk.setBounds(59, 136, 143, 23);
		contentPane.add(btnOk);
		
		// Implementação do Listener do Botão com uma Classe Interna Anônima
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarCancelamentoDeEfetivacao();
			}
		});
		btnCancelar.setBounds(247, 136, 154, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblEmpregado = new JLabel("Empregado:");
		lblEmpregado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmpregado.setBounds(8, 107, 80, 14);
		contentPane.add(lblEmpregado);
		
		cbDeptos = new JComboBox();
		cbDeptos.setBounds(98, 104, 292, 20);
		contentPane.add(cbDeptos);
	}

	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#solicitarExecucaoDeEfetivacao()
	 */
	@Override
	public void solicitarExecucaoDeEfetivacao() {
		try {
			// Recupero os valores digitados nos textfields
			String cpf = tfCpf.getText();
			String nome = tfNome.getText();
			int    matrFuncional = ((JIntField)tfMatrFuncional).getValue();
			
			Object selecionado = cbDeptos.getSelectedItem();
			Object depto = selecionado == SELECAO_NULA ? null : selecionado;
			// Verifico qual é a operação que estou fazendo
			// e notifico ao controlador
			if (!ehAlteração)
				ctrlIncluir.incluir(cpf, matrFuncional, nome, depto);
			else
				ctrlAlterar.alterar(cpf, matrFuncional, nome, depto);
		} catch (InterfaceException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} 
	}
	
	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#solicitarCancelamentoDeEfetivacao()
	 */
	@Override
	public void solicitarCancelamentoDeEfetivacao() {
		try {
			if (!ehAlteração)
				ctrlIncluir.cancelarIncluir();
			else
				ctrlAlterar.cancelarAlterar();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#exibir()
	 */
	@Override
	public void exibir() {
		this.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#fechar()
	 */
	@Override
	public void fechar() {
		this.setVisible(false);
	}
	
	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#limpar()
	 */
	@Override
	public void limpar() {
		this.tfCpf.setText(null);
		this.tfNome.setText(null);
		this.tfMatrFuncional.setText(null);
		this.cbDeptos.removeAllItems();
	}
	
	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#atualizarCampos(java.lang.String, int, java.lang.String, java.lang.Object, java.util.List)
	 */
	@Override
	public void atualizarCampos(List<Object> deptos) {
		this.limpar();
		cbDeptos.addItem(SELECAO_NULA);
		for(Object o : deptos)
			if(o != null)
				cbDeptos.addItem(o);
		this.cbDeptos.setSelectedItem(SELECAO_NULA);
	}

	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#atualizarCampos(java.lang.String, int, java.lang.String, java.lang.Object, java.util.List)
	 */
	@Override
	public void atualizarCampos(String cpf, int matrFuncional, String nome, Object depto, List<Object> deptos) {
		atualizarCampos(deptos);
		this.tfCpf.setText(cpf);
		this.tfNome.setText(nome);
		((JIntField)this.tfMatrFuncional).setValue(matrFuncional);
		if(depto != null)
			this.cbDeptos.setSelectedItem(depto);
		else
			this.cbDeptos.setSelectedItem(SELECAO_NULA);
	}

	//
	// Exemplo de Classe Interna - Listener do Botão Ok
	//
	public class ObservadorDoBotaoOk implements ActionListener {			
		public void actionPerformed(ActionEvent arg0) {
			solicitarExecucaoDeEfetivacao();
		}
	}
}
