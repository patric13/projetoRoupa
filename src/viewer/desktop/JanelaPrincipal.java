package viewer.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.util.DadosException;
import viewer.UILogin;
import controller.CtrlSessaoUsuario;
import controller.util.ControleException;

/**
 * Classe que implementa a janela principal do sistema
 * @author Alessandro Cerqueira
 */
public class JanelaPrincipal extends JFrame implements UILogin {

	//
	// ATRIBUTOS
	//
	
	/**
	 * Refer�ncia para o painel de conte�do da janela
	 */
	private JPanel contentPane;
	/**
	 * Refer�ncia para o controlador do programa a quem a janela
	 * principal ir� sempre mandar a notifica��o.
	 */
	private CtrlSessaoUsuario ctrlPrg;
	
	//
	// M�TODOS
	//
	/**
	 * Create the frame.
	 */
	//public JanelaPrincipal() {
	public JanelaPrincipal(CtrlSessaoUsuario p) {
		this.ctrlPrg = p;
		this.criarUI();
	}
	
	/**
	 * Cria visualmente a Janela
	 */
	public void criarUI() {
		setTitle("Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 359, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDepartamentos = new JButton("Departamentos");
		btnDepartamentos.setIcon(new ImageIcon(JanelaPrincipal.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		btnDepartamentos.addActionListener(new ActionListener() {
			// M�todo acionado quando o bot�o "Departamentos" 
			// for pressionado (M�todo de Callback).
			public void actionPerformed(ActionEvent arg0) {
				try {
					ctrlPrg.iniciarCasoDeUsoManterDepartamentos();
				} catch(ControleException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch(DadosException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			}
		});
		btnDepartamentos.setBounds(10, 11, 142, 55);
		contentPane.add(btnDepartamentos);
		
		JButton btnEmpregados = new JButton("Empregados");
		btnEmpregados.setIcon(new ImageIcon(JanelaPrincipal.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		btnEmpregados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// M�todo acionado quando o bot�o "Empregados" 
				// for pressionado (M�todo de Callback).
				try {
					ctrlPrg.iniciarCasoDeUsoManterEmpregados();
				} catch(ControleException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch(DadosException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			}
		});
		btnEmpregados.setBounds(180, 11, 142, 55);
		contentPane.add(btnEmpregados);
		
		JButton btnProjetos = new JButton("Projetos");
		btnProjetos.setIcon(new ImageIcon(JanelaPrincipal.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		btnProjetos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// M�todo acionado quando o bot�o "Projetos" 
				// for pressionado (M�todo de Callback).
				try {
					ctrlPrg.iniciarCasoDeUsoManterProjetos();
				} catch(ControleException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch(DadosException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			}
		});
		btnProjetos.setBounds(10, 84, 142, 55);
		contentPane.add(btnProjetos);
			
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechar();
			}
		});
		btnSair.setIcon(new ImageIcon(JanelaPrincipal.class.getResource("/com/sun/java/swing/plaf/windows/icons/HardDrive.gif")));
		btnSair.setBounds(180, 84, 142, 55);
		contentPane.add(btnSair);		
		
		JButton btnEmpresa = new JButton("Empresa");
		btnEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					ctrlPrg.iniciarCasoDeUsoManterEmpresas();
				} catch(ControleException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch(DadosException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
				
				
			}
		});
		btnEmpresa.setBounds(10, 154, 142, 55);
		contentPane.add(btnEmpresa);
	}
	
	/**
	 * Fecha a janela
	 */
	public void fechar() {
		this.setVisible(false);
		ctrlPrg.terminar();		
	}

	/**
	 * Limpa a Janela
	 */
	public void limpar() {
	}

	/**
	 * Exibe a Janela
	 */
	public void exibir() {
		this.setVisible(true);
	}
}
