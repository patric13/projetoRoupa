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

import viewer.UIEmpresa;
import model.util.DadosException;
import controller.CtrlAlterarEmpresa;
import controller.CtrlIncluirEmpresa;
import controller.util.ControleException;
import controller.util.ICtrlCasoDeUso;

public class JanelaEmpresa extends JFrame implements UIEmpresa {
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para o controlador do caso de uso
	 */
	private final ICtrlCasoDeUso ctrl;
	/**
	 * Indica se estou fazendo uma operação de inclusão ou alteração
	 */
	private boolean ehAlteração;
	/**
	 * Content Pane da Janela
	 */
	private JPanel contentPane;
	/**
	 * TextField para a sigla do empresa
	 */
	private JTextField tfCnpj;
	/**
	 * TextField para o nome do empresa
	 */
	private JTextField tfNome;
	private JLabel lblUrlsite;
	private JLabel lblEstado;
	private JTextField tfUrlsite;
	private JTextField tfEstado;
	private JLabel lblCidade;
	private JTextField tfCidade;
	private JLabel lblBairro;
	private JTextField tfBairro;
	private JLabel lblLogradouro;
	private JTextField tfLogradouro;
	private JLabel lblComplemento;
	private JTextField tfComplemento;
	private JLabel lblCep;
	private JTextField tfCep;
	private JLabel lblNumero;
	private JTextField tfNumero;

	/**
	 * Criação da UI
	 */
	public JanelaEmpresa(ICtrlCasoDeUso ct) throws DadosException,
			ControleException {
		this.ctrl = ct;
		setTitle("Empresa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setBounds(20, 11, 46, 14);
		contentPane.add(lblCnpj);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 48, 46, 14);
		contentPane.add(lblNome);

		tfCnpj = new JTextField();
		tfCnpj.setBounds(76, 8, 86, 20);
		contentPane.add(tfCnpj);
		tfCnpj.setColumns(10);

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
		btnOk.setBounds(76, 382, 143, 23);
		contentPane.add(btnOk);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solicitarCancelamentoDeEfetivacao();
			}
		});
		btnCancelar.setBounds(256, 382, 154, 23);
		contentPane.add(btnCancelar);

		lblUrlsite = new JLabel("Url Site");
		lblUrlsite.setBounds(20, 73, 64, 50);
		contentPane.add(lblUrlsite);

		lblEstado = new JLabel("Estado");
		lblEstado.setBounds(20, 119, 46, 23);
		contentPane.add(lblEstado);

		tfUrlsite = new JTextField();
		tfUrlsite.setColumns(10);
		tfUrlsite.setBounds(76, 86, 170, 20);
		contentPane.add(tfUrlsite);

		tfEstado = new JTextField();
		tfEstado.setColumns(10);
		tfEstado.setBounds(76, 122, 334, 20);
		contentPane.add(tfEstado);

		lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(20, 159, 46, 23);
		contentPane.add(lblCidade);

		tfCidade = new JTextField();
		tfCidade.setColumns(10);
		tfCidade.setBounds(76, 162, 334, 20);
		contentPane.add(tfCidade);

		lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(20, 204, 46, 23);
		contentPane.add(lblBairro);

		tfBairro = new JTextField();
		tfBairro.setColumns(10);
		tfBairro.setBounds(76, 207, 334, 20);
		contentPane.add(tfBairro);

		lblLogradouro = new JLabel("Logradouro");
		lblLogradouro.setBounds(10, 252, 56, 23);
		contentPane.add(lblLogradouro);

		tfLogradouro = new JTextField();
		tfLogradouro.setColumns(10);
		tfLogradouro.setBounds(76, 252, 334, 20);
		contentPane.add(tfLogradouro);

		lblComplemento = new JLabel("Complemento");
		lblComplemento.setBounds(0, 291, 66, 23);
		contentPane.add(lblComplemento);

		tfComplemento = new JTextField();
		tfComplemento.setColumns(10);
		tfComplemento.setBounds(76, 294, 334, 20);
		contentPane.add(tfComplemento);

		lblCep = new JLabel("Cep");
		lblCep.setBounds(20, 323, 66, 23);
		contentPane.add(lblCep);

		tfCep = new JTextField();
		tfCep.setColumns(10);
		tfCep.setBounds(76, 326, 334, 20);
		contentPane.add(tfCep);

		lblNumero = new JLabel("Numero");
		lblNumero.setBounds(18, 353, 66, 23);
		contentPane.add(lblNumero);

		tfNumero = new JTextField();
		tfNumero.setColumns(10);
		tfNumero.setBounds(76, 356, 334, 20);
		contentPane.add(tfNumero);

		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see viewer.UIEmpresa#solicitarExecucaoDeEfetivacao()
	 */
	@Override
	public void solicitarExecucaoDeEfetivacao() {
		try {
			// Recupero os valores digitados nos textfields
			String cnpj = tfCnpj.getText();
			String nome = tfNome.getText();
			String urlsite = tfUrlsite.getText();
			String estado = tfEstado.getText();
			String cidade = tfEstado.getText();
			String logradouro = tfLogradouro.getText();
			String complemento = tfComplemento.getText();
			String bairro = tfBairro.getText();
			int cep = Integer.parseInt(tfCep.getText());
			int numero = Integer.parseInt(tfNumero.getText());
			//TO DO
			boolean registroValidado = false;
			// Verifico qual é a operação que estou fazendo
			// e notifico ao controlador
			if (!ehAlteração)
				((CtrlIncluirEmpresa) ctrl).incluir(cnpj, nome, urlsite,
						estado, cidade, logradouro, cep, complemento, numero,
						bairro, registroValidado);
			else
				((CtrlAlterarEmpresa) ctrl).alterar(cnpj, nome, urlsite,
						estado, cidade, logradouro, cep, complemento, numero,
						bairro, registroValidado);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see viewer.UIEmpresa#solicitarCancelamentoDeEfetivacao()
	 */
	@Override
	public void solicitarCancelamentoDeEfetivacao() {
		try {
			if (!ehAlteração)
				((CtrlIncluirEmpresa) ctrl).cancelarIncluir();
			else
				((CtrlAlterarEmpresa) ctrl).cancelarAlterar();
		} catch (DadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ControleException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see viewer.UIEmpresa#fechar()
	 */
	
	@Override
	public void fechar() {
		this.setVisible(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see viewer.UIEmpresa#atualizarCampos(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void atualizarCampos(String cnpj, String nome, String urlsite, String estado,
			String cidade, String logradouro, int cep, String complemento,
			int numero, String bairro, boolean registroValidado) {
		this.tfCnpj.setText(cnpj);
		this.tfNome.setText(nome);
		this.tfUrlsite.setText(urlsite);
		this.tfEstado.setText(estado);
		this.tfCidade.setText(cidade);
		this.tfBairro.setText(bairro);
		this.tfLogradouro.setText(logradouro);
		this.tfComplemento.setText(complemento);
		this.tfCep.setText(Integer.toString(cep));
		this.tfNumero.setText(Integer.toString(numero));
		
		
		this.ehAlteração = true;
	}
}
