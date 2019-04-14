package controller;

import java.util.List;

import model.Departamento;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.IDadosParaTabela;
import viewer.UICadastroDepartamentos;
import viewer.ViewerManager;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlManterDepartamentos implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		DISPONIVEL, INCLUINDO, EXCLUINDO, ALTERANDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == DISPONIVEL || 
			   anterior == DISPONIVEL && novo == INCLUINDO  || 
			   anterior == DISPONIVEL && novo == EXCLUINDO  ||
			   anterior == DISPONIVEL && novo == ALTERANDO  ||
			   anterior == DISPONIVEL && novo == ENCERRADO  ||
			   anterior == INCLUINDO  && novo == DISPONIVEL ||
			   anterior == EXCLUINDO  && novo == DISPONIVEL ||
			   anterior == ALTERANDO  && novo == DISPONIVEL)
				return;
			throw new ControleException(new ErroDeControle("Não se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Referência para o controlador do programa.
	 */
	private final CtrlSessaoUsuario ctrlPrg;

	/**
	 * Referência para o controlador do caso de uso Incluir Departamento.
	 */
	private CtrlIncluirDepartamento ctrlIncluirDepartamento;

	/**
	 * Referência para o controlador do caso de uso Alterar Departamento.
	 */
	private CtrlAlterarDepartamento ctrlAlterarDepartamento;

	/**
	 * Referência para o controlador do caso de uso Alterar Departamento.
	 */
	private CtrlExcluirDepartamento ctrlExcluirDepartamento;

	/**
	 * Referência para a janela do cadastro de Departamentos
	 */
	private UICadastroDepartamentos uiCadastro;

	/**
	 * Referência para o objeto DaoDepartamento
	 */
	private IDAO<Departamento> dao = (IDAO<Departamento>)DAO.getDAO(Departamento.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterDepartamentos
	 */
	public CtrlManterDepartamentos(CtrlSessaoUsuario p) throws ControleException, DadosException {
		// Guardo a referência para o controlador do programa
		this.ctrlPrg = p;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * Inicia o caso de uso "Manter Departamentos"
	 */
	public void iniciar() throws ControleException, DadosException {
		// Recupero os objetos Departamento do DAO
		this.dao = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Crio e abro a janela de cadastro
		this.uiCadastro = (UICadastroDepartamentos)ViewerManager.obterViewer(this, UICadastroDepartamentos.class);
		// Solicito à interface que carregue os objetos
		this.uiCadastro.exibirObjetos(deptos);
		// Solicito à interface que carregue os objetos
		this.uiCadastro.exibir();
		// Informo que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
	}

	/** 
	 * 
	 */
	public void terminar() throws ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Fecho a janela
		this.uiCadastro.fechar();
		// Informo que o controlador está encerrado
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		this.ctrlPrg.terminarCasoDeUsoManterDepartamentos();
	}

	/** 
	 * 
	 */
	public void iniciarCasoDeUsoIncluirDepartamento() throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.INCLUINDO);
		// Abro a janela de departamento
		this.ctrlIncluirDepartamento = new CtrlIncluirDepartamento(this);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoIncluirDepartamento() throws DadosException, ControleException {
		if(this.ctrlIncluirDepartamento != null)
			this.ctrlIncluirDepartamento.terminar();
		this.ctrlIncluirDepartamento = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Departamento do DAO
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(deptos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoAlterarDepartamento(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.ALTERANDO);
		// Promovo o casting
		Departamento d = (Departamento)selecionado;
		// Abro a janela de departamento
		this.ctrlAlterarDepartamento = new CtrlAlterarDepartamento(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoAlterarDepartamento() throws DadosException, ControleException{
		if(this.ctrlAlterarDepartamento != null)
			this.ctrlAlterarDepartamento.terminar();
		this.ctrlAlterarDepartamento = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Departamento do DAO
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(deptos);
		this.uiCadastro.exibir();
	}
	
	/** 
	 * 
	 */
	public void iniciarCasoDeUsoExcluirDepartamento(IDadosParaTabela selecionado) throws DadosException, ControleException {
		// Indico que o controlador de caso de uso está incluindo
		this.setStatus(Status.EXCLUINDO);
		// Promovo o casting
		Departamento d = (Departamento)selecionado;
		// Abro a janela de departamento
		this.ctrlExcluirDepartamento = new CtrlExcluirDepartamento(this, d);
	}

	/**
	 *  
	 */
	public void terminarCasoDeUsoExcluirDepartamento() throws DadosException, ControleException{
		if(this.ctrlExcluirDepartamento != null)
			this.ctrlExcluirDepartamento.terminar();
		this.ctrlExcluirDepartamento = null;
		// Indico que o controlador de caso de uso está disponível
		this.setStatus(Status.DISPONIVEL);
		// Recupero os objetos Departamento do DAO
		List<IDadosParaTabela> deptos = dao.getListaObjs();
		// Solicito a atualização da interface após as ações de inclusão
		this.uiCadastro.exibirObjetos(deptos);
		this.uiCadastro.exibir();
	}	

	/**
	 * 
	 * @return
	 */
	public Status getStatus() {
		return this.status;
	}
	
	/**
	 * 
	 * @param novo
	 * @throws ControleException
	 */
	public void setStatus(Status novo) throws ControleException {
		Status.validarTransicaoStatus(this.status,novo);
		this.status = novo;
	}
}
