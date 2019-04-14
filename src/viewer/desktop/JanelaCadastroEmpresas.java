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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Empresa;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroEmpresas;
import controller.CtrlManterEmpresas;
import controller.util.ControleException;

public class JanelaCadastroEmpresas extends JFrame implements UICadastroEmpresas {
	/**
	 * Referência para o controlador do caso de uso
	 * Manter Empresas
	 */
	private CtrlManterEmpresas ctrl;

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
	public JanelaCadastroEmpresas(CtrlManterEmpresas c) {
		this.ctrl = c;
		setTitle("Empresas");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 419, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeIncluirEmpresa();
			}
		});
		btnIncluir.setBounds(10, 232, 89, 23);
		contentPane.add(btnIncluir);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeExcluirEmpresa();
			}
		});
		btnExcluir.setBounds(109, 232, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarExecucaoDeAlterarEmpresa();
			}
		});
		btnAlterar.setBounds(208, 232, 89, 23);
		contentPane.add(btnAlterar);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarTerminoDeManterEmpresas();
			}
		});
		btnSair.setBounds(307, 232, 89, 23);
		contentPane.add(btnSair);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 386, 212);
		contentPane.add(scrollPane);
		
		table = new JTable();
		this.tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new Empresa().getCamposDeTabela() 
			); 
		table.setModel(this.tableModel);
		
	table.getColumnModel().getColumn(0).setPreferredWidth(100);
	table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);

		// Exemplo de como tratar eventos através de TableModelListener
		this.tableModel.addTableModelListener(new TableModelListener() {
		    public void tableChanged(TableModelEvent e) {
		    	tratarModificacaoNaTabela(e);
		    }
		});
		
		scrollPane.setViewportView(table);
		this.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpresas#exibirObjetos(java.util.List)
	 */
	@Override
	public void exibirObjetos(List<IDadosParaTabela> objetos) {
		this.limpar();
		this.objetos = objetos;		
		for(IDadosParaTabela d : objetos)
			this.tableModel.addRow(d.getDadosParaTabela());
	}
		
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpresas#limpar()
	 */
	@Override
	public void limpar() {
		while(this.tableModel.getRowCount() > 0)
			this.tableModel.removeRow(0);
	}

	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpresas#solicitarExecucaoDeIncluirEmpresa()
	 */
	@Override
	public void solicitarExecucaoDeIncluirEmpresa() {
		try {
			this.ctrl.iniciarCasoDeUsoIncluirEmpresa();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpresas#solicitarExecucaoDeExcluirEmpresa()
	 */
	@Override
	public void solicitarExecucaoDeExcluirEmpresa() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de exclusão
		try {
			ctrl.iniciarCasoDeUsoExcluirEmpresa(this.objetos.get(pos));
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
	 * @see viewer.UICadastroEmpresas#solicitarExecucaoDeAlterarEmpresa()
	 */
	@Override
	public void solicitarExecucaoDeAlterarEmpresa() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de alteração
		try {
			ctrl.iniciarCasoDeUsoAlterarEmpresa(this.objetos.get(pos));
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
	 * @see viewer.UICadastroEmpresas#solicitarTerminoDeManterEmpresas()
	 */
	@Override
	public void solicitarTerminoDeManterEmpresas() {
		try {
			ctrl.terminar();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpresas#fechar()
	 */
	@Override
	public void exibir() {
		this.setVisible(true);
	}
		
	/* (non-Javadoc)
	 * @see viewer.UICadastroEmpresas#fechar()
	 */
	@Override
	public void fechar() {
		this.setVisible(false);
	}
		
	/**
	 * Exemplo de manipulação de alterações no JTable
	 * @param e
	 */
	public void tratarModificacaoNaTabela(TableModelEvent e) {
        if(e.getType() != TableModelEvent.UPDATE)
        	return;
		int linha = e.getFirstRow();
        int coluna = e.getColumn();
 
        TableModel model = (TableModel) e.getSource();
 
        System.out.println("Você alterou a linha " + linha + ", coluna " + coluna);
        System.out.println("Valor da célula alterada: " + model.getValueAt(linha, coluna));
	}
}


// Obtendo à referencia para a 5 coluna da tabela
// TableColumn column = minhaTabela.getColumnModel().getColumn(4);
// Criando o ComboBox
// JComboBox comboSexo = new JComboBox();
// Definindo os valores para o ComboBox
// DefaultComboBoxModel comboModel = new DefaultComboBoxModel(new String[] { "Masculino", "Feminino" });
// comboSexo.setModel(comboModel);
// Associando o ComboBox para a coluna
// column.setCellEditor(new DefaultCellEditor(comboSexo));
