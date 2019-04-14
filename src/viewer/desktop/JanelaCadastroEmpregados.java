package viewer.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Empregado;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroEmpregados;
import controller.CtrlManterEmpregados;
import controller.util.ControleException;

public class JanelaCadastroEmpregados extends JFrame implements UICadastroEmpregados {
	/**
	 * Referência para o controlador do caso de uso
	 * Manter Empregados
	 */
	private CtrlManterEmpregados ctrl;
	/**
	 * Lista de objetos a serem exibidos 
	 */
	private List<IDadosParaTabela> objetos;
	/**
	 * Referência ao contentPane da Janela 
	 */
	private JPanel contentPane;
	/**
	 * Referência ao JTable embutido na janela
	 */
	private JTable table;
	/**
	 * Referência para o TableModel da tabela
	 */
	private DefaultTableModel tableModel;
	
	/**
	 * Create the frame.
	 */
	public JanelaCadastroEmpregados(CtrlManterEmpregados c) {
		this.ctrl = c;
		this.criarUI();
	}
	
	/**
	 * Cria Visualmente a Janela
	 */
	@Override
	public void criarUI() {
		setTitle("Empregados");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 569, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeIncluirEmpregado();
			}
		});
		btnIncluir.setBounds(81, 234, 89, 23);
		contentPane.add(btnIncluir);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeExcluirEmpregado();
			}
		});
		btnExcluir.setBounds(180, 234, 89, 23);
		contentPane.add(btnExcluir);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeAlterarEmpregado();
			}
		});
		btnAlterar.setBounds(279, 234, 89, 23);
		contentPane.add(btnAlterar);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarTerminoDeManterEmpregados();
			}
		});
		btnSair.setBounds(378, 234, 89, 23);
		contentPane.add(btnSair);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 533, 212);
		contentPane.add(scrollPane);
		
		table = new JTable();
		this.tableModel = new DefaultTableModel(null,new String[] {"Empregados"}); 
		table.setModel(this.tableModel);
		scrollPane.setViewportView(table);
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpregados#exibirObjetos(java.util.List)
	 */
	@Override
	public void exibirObjetos(List<IDadosParaTabela> objetos) {
		this.limpar();
		if(objetos.size() > 0) {
			this.tableModel = new DefaultTableModel(null, objetos.get(0).getCamposDeTabela());
			table.setModel(this.tableModel);
			table.getColumnModel().getColumn(0).setPreferredWidth(108);
			table.getColumnModel().getColumn(1).setPreferredWidth(70);
			table.getColumnModel().getColumn(2).setPreferredWidth(269);
			table.getColumnModel().getColumn(3).setPreferredWidth(247);
		}		
		this.objetos = objetos;		
		for(IDadosParaTabela d : objetos)
			this.tableModel.addRow(d.getDadosParaTabela());
	}
		
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpregados#limpar()
	 */
	@Override
	public void limpar() {
		while(this.tableModel.getRowCount() > 0)
			this.tableModel.removeRow(0);
	}

	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpregados#solicitarExecucaoDeIncluirEmpregado()
	 */
	@Override
	public void solicitarExecucaoDeIncluirEmpregado() {
		try {
			this.ctrl.iniciarCasoDeUsoIncluirEmpregado();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpregados#solicitarExecucaoDeExcluirEmpregado()
	 */
	@Override
	public void solicitarExecucaoDeExcluirEmpregado() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de exclusão
		try {
			ctrl.iniciarCasoDeUsoExcluirEmpregado(this.objetos.get(pos));
		} catch(ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}	
		catch(DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}	
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpregados#solicitarExecucaoDeAlterarEmpregado()
	 */
	@Override
	public void solicitarExecucaoDeAlterarEmpregado() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de alteração
		try {
			ctrl.iniciarCasoDeUsoAlterarEmpregado(this.objetos.get(pos));
		} catch(ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		catch(DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpregados#solicitarTerminoDeManterEmpregados()
	 */
	@Override
	public void solicitarTerminoDeManterEmpregados() {
		try {
			ctrl.terminar();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroDepartamentos#fechar()
	 */
	@Override
	public void exibir() {
		this.setVisible(true);
	}
		
	/* (non-Javadoc)
	 * @see viewer.UICadastroDepartamentos#fechar()
	 */
	@Override
	public void fechar() {
		this.setVisible(false);
	}		
}