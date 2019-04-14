package viewer.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import model.Departamento;
import model.Empregado;
import model.util.DadosException;
import viewer.UIProjeto;
import controller.CtrlAlterarProjeto;
import controller.CtrlIncluirProjeto;
import controller.util.ControleException;

public class JanelaProjeto extends JFrame implements UIProjeto {
	//
	// CONSTANTES
	//
	public final static Object SELECAO_NULA = "Selecione...";
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para o controlador do caso de uso Incluir Projeto
	 */
	private final CtrlIncluirProjeto ctrlIncluir;
	/**
	 * Referência para o controlador do caso de uso Alterar Projeto
	 */
	private final CtrlAlterarProjeto ctrlAlterar;
	/**
	 * Indica se estou fazendo uma operação de inclusão ou alteração
	 */
	private boolean ehAlteração;
	/**
	 * Content Pane da Janela
	 */
	private JPanel contentPane;
	/**
	 * TextField para o nome do projeto
	 */
	private JTextField 		 tfNome;
	/**
	 * ComboBox do departamento do projeto
	 */
	private JComboBox  		 cbDeptos;
	/**
	 * ListModel dos Empregados
	 */
	private DefaultListModel lstEmpregadosModel;
	/**
	 * List dos Empregados
	 */
	private JList 	   		 lstEmpregados;
	
	/**
	 * Construtor para inclusão
	 * @param ct
	 * @throws DadosException
	 * @throws ControleException
	 */
	public JanelaProjeto(CtrlIncluirProjeto ct) throws DadosException, ControleException {
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
	public JanelaProjeto(CtrlAlterarProjeto ct) throws DadosException, ControleException {
		this.ehAlteração = true;
		this.ctrlIncluir = null;
		this.ctrlAlterar = ct;
		this.criarUI();
	}
	
	/**
	 * Criação da UI
	 */
	public void criarUI() {
		setTitle("Projeto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome do Projeto:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(10, 14, 105, 14);
		contentPane.add(lblNome);
			
		tfNome = new JTextField();
		tfNome.setBounds(125, 11, 299, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeEfetivacao();
			}
		});
		btnOk.setBounds(51, 251, 143, 23);
		contentPane.add(btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarCancelamentoDeEfetivacao();
			}
		});
		btnCancelar.setBounds(234, 251, 154, 23);
		contentPane.add(btnCancelar);
		
		cbDeptos = new JComboBox();
		cbDeptos.setBounds(10, 42, 414, 50);
		cbDeptos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Departamento Responsável"));
		contentPane.add(cbDeptos);
		
		this.lstEmpregadosModel = new DefaultListModel<Empregado>();
		this.lstEmpregados = new JList(lstEmpregadosModel);
		this.lstEmpregados.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.lstEmpregados.setBounds(8, 100, 416, 144);
		this.lstEmpregados.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Empregados Alocados"));
		contentPane.add(lstEmpregados);
	}

	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#solicitarExecucaoDeEfetivacao()
	 */
	@Override
	public void solicitarExecucaoDeEfetivacao() {
		try {
			// Recupero os valores digitados nos textfields
			String nome = tfNome.getText();
			Object selecionado = cbDeptos.getSelectedItem();
			Object depto = selecionado == SELECAO_NULA ? null : (Departamento)selecionado;
			List<Object> emps = lstEmpregados.getSelectedValuesList();
			// Verifico qual é a operação que estou fazendo
			// e notifico ao controlador
			if (!ehAlteração)
				ctrlIncluir.incluir(nome, depto, emps);
			else
				ctrlAlterar.alterar(nome, depto, emps);
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
		this.tfNome.setText(null);
		cbDeptos.removeAllItems();
		lstEmpregados.removeAll();
	}

	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#atualizarCampos(java.util.List, java.util.List)
	 */
	@Override
	public void atualizarCampos(List<Object> todosDeptos, List<Object> todosEmps) {
		this.limpar();
		// Adicionando os deptos na combobox
		cbDeptos.addItem(SELECAO_NULA);
		for(Object o : todosDeptos)
			if(o != null)
				cbDeptos.addItem(o);
		this.cbDeptos.setSelectedItem(SELECAO_NULA);
		// Adicionando os Empregados na List
		for(Object o : todosEmps)
			if(o != null)
				this.lstEmpregadosModel.addElement(o);
		this.lstEmpregados.clearSelection();
	}

	/* (non-Javadoc)
	 * @see viewer.UIEmpregado#atualizarCampos(java.lang.String, java.lang.Object, java.util.List)
	 */
	@Override
	public void atualizarCampos(String nome, List<Object> todosDeptos, List<Object> todosEmps, Object depto, List<Object> empsSelecionados) {
		atualizarCampos(todosDeptos,todosEmps);
		this.tfNome.setText(nome);
		if(depto != null)
			this.cbDeptos.setSelectedItem(depto);
		else
			this.cbDeptos.setSelectedItem(SELECAO_NULA);
		// Adicionando os Empregados na List
		for(int i = 0; i < this.lstEmpregadosModel.getSize(); i++) {
			if(empsSelecionados.contains(this.lstEmpregadosModel.elementAt(i)))
	            this.lstEmpregados.addSelectionInterval(i, i);			
		}
	}
}
