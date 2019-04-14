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

import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroProjetos;
import controller.CtrlManterProjetos;
import controller.util.ControleException;

public class JanelaCadastroProjetos extends JFrame implements UICadastroProjetos {
	/**
	 * Referência para o controlador do caso de uso
	 * Manter Projetos
	 */
	private CtrlManterProjetos ctrl;
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
	public JanelaCadastroProjetos(CtrlManterProjetos c) {
		this.ctrl = c;
		this.criarUI();
	}
	
	/**
	 * Cria Visualmente a UI
	 */
	public void criarUI() {	
		setTitle("Projetos");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 569, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeIncluirProjeto();
			}
		});
		btnIncluir.setBounds(81, 234, 89, 23);
		contentPane.add(btnIncluir);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeExcluirProjeto();
			}
		});
		btnExcluir.setBounds(180, 234, 89, 23);
		contentPane.add(btnExcluir);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeAlterarProjeto();
			}
		});
		btnAlterar.setBounds(279, 234, 89, 23);
		contentPane.add(btnAlterar);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarTerminoDeManterProjetos();
			}
		});
		btnSair.setBounds(378, 234, 89, 23);
		contentPane.add(btnSair);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 533, 212);
		contentPane.add(scrollPane);
		
		table = new JTable();
		this.tableModel = new DefaultTableModel(null,new String[] {"Projetos"});
		table.setModel(this.tableModel);
		scrollPane.setViewportView(table);
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroProjetos#exibirObjetos(java.util.List)
	 */
	@Override
	public void exibirObjetos(List<IDadosParaTabela> objetos) {
		this.limpar();
		if(objetos.size() > 0) {
			this.tableModel = new DefaultTableModel(null, objetos.get(0).getCamposDeTabela());
			table.setModel(this.tableModel);
			table.getColumnModel().getColumn(0).setPreferredWidth(108);
			table.getColumnModel().getColumn(1).setPreferredWidth(269);
			table.getColumnModel().getColumn(2).setPreferredWidth(247);
			table.getColumnModel().getColumn(3).setPreferredWidth(307);
		}
		
		this.objetos = objetos;		
		for(IDadosParaTabela d : objetos)
			this.tableModel.addRow(d.getDadosParaTabela());
	}
		
	/* (non-Javadoc)
	 * @see viewer.UICadastroProjetos#limpar()
	 */
	@Override
	public void limpar() {
		while(this.tableModel.getRowCount() > 0)
			this.tableModel.removeRow(0);
	}

	/* (non-Javadoc)
	 * @see viewer.UICadastroProjetos#solicitarExecucaoDeIncluirProjeto()
	 */
	@Override
	public void solicitarExecucaoDeIncluirProjeto() {
		try {
			this.ctrl.iniciarCasoDeUsoIncluirProjeto();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroProjetos#solicitarExecucaoDeExcluirProjeto()
	 */
	@Override
	public void solicitarExecucaoDeExcluirProjeto() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de exclusão
		try {
			ctrl.iniciarCasoDeUsoExcluirProjeto(this.objetos.get(pos));
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
	 * @see viewer.UICadastroProjetos#solicitarExecucaoDeAlterarProjeto()
	 */
	@Override
	public void solicitarExecucaoDeAlterarProjeto() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de alteração
		try {
			ctrl.iniciarCasoDeUsoAlterarProjeto(this.objetos.get(pos));
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
	 * @see viewer.UICadastroProjetos#solicitarTerminoDeManterProjetos()
	 */
	@Override
	public void solicitarTerminoDeManterProjetos() {
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