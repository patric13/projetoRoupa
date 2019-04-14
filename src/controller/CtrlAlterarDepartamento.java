package controller;

import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import model.Departamento;
import model.Empregado.Status;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import model.util.ErroDeDominio;
import viewer.UICadastroDepartamentos;
import viewer.UIDepartamento;
import viewer.ViewerManager;
import viewer.desktop.DialogExcluirDepartamento;
import viewer.desktop.JanelaCadastroDepartamentos;
import viewer.desktop.JanelaDepartamento;

public class CtrlAlterarDepartamento implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		ALTERANDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == ALTERANDO || 
			   anterior == ALTERANDO  && novo == ENCERRADO)
				return;
			throw new ControleException(new ErroDeControle("Não se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Referência para o controlador do caso de uso manter.
	 */
	private final CtrlManterDepartamentos ctrlManterDepartamentos;

	/**
	 * Referência para o objeto a ser atualizado 
	 */
	private Departamento atual;
	
	/**
	 * Referência para a janela Departamento que permitirá a inclusão e
	 * alteração do Departamento
	 */
	private UIDepartamento uiDepartamento;

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
	public CtrlAlterarDepartamento(CtrlManterDepartamentos ctrl, Departamento d) throws ControleException, DadosException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterDepartamentos = ctrl;
		// Guardo a referência para o objeto a ser alterado
		this.atual = d;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Informo que o controlador de caso de uso está disponível
		this.setStatus(Status.ALTERANDO);
		// Crio e abro a janela de cadastro
		this.uiDepartamento = (UIDepartamento)ViewerManager.obterViewer(this, UIDepartamento.class);
		// Solicito à interface que atualize os campos 
		this.uiDepartamento.atualizarCampos(this.atual.getSigla(),this.atual.getNome());
		// Solicito à interface que carregue os objetos
		this.uiDepartamento.exibir();
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Não há departamento em manipulação
		this.atual = null;
		// Fecho a janela
		this.uiDepartamento.fechar();
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterDepartamentos.terminarCasoDeUsoAlterarDepartamento();
	}

	/** 
	 * 
	 */
	public void cancelarAlterar() throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("Não é possível cancelar uma operação de alteração"));
		// Termino o caso de uso
		this.terminar();
	}

	/** 
	 * 
	 */
	public void alterar(String sigla, String nome) throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("Não é possível concluir uma operação de alteração"));
		// Atualizo os campos
		this.atual.setSigla(sigla);
		this.atual.setNome(nome);
		// Salvo o objeto Departamento usando o DAO
		dao.atualizar(this.atual);
		// Termino o caso de uso
		this.terminar();
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
