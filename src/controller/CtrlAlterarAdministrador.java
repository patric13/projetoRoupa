package controller;

import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIAdministrador;
import viewer.web.AdministradorMB;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;
import dominio.Administrador;

public class CtrlAlterarAdministrador implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		ALTERANDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == ALTERANDO || 
			   anterior == ALTERANDO  && novo == ENCERRADO)
				return;
			throw new ControleException(new ErroDeControle("N�o se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Refer�ncia para o controlador do caso de uso manter.
	 */
	private final CtrlManterAdministradores ctrlManterAdministradores;

	/**
	 * Refer�ncia para o objeto a ser atualizado 
	 */
	private Administrador atual;
	
	/**
	 * Refer�ncia para a janela Departamento que permitir� a inclus�o e
	 * altera��o do Departamento
	 */
	private UIAdministrador uiAdministrador;

	/**
	 * Refer�ncia para o objeto DaoDepartamento
	 */
	private IDAO<Administrador> dao = (IDAO<Administrador>)DAO.getDAO(Administrador.class);

	/**
	 * Atributo que indica qual opera��o est� em curso
	 */
	private Status status;

	//
	// M�TODOS
	//

	/**
	 * Construtor da classe CtrlManterDepartamentos
	 */
	public CtrlAlterarAdministrador(CtrlManterAdministradores ctrl, Administrador d) throws ControleException, DadosException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlManterAdministradores = ctrl;
		// Guardo a refer�ncia para o objeto a ser alterado
		this.atual = d;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Informo que o controlador de caso de uso est� dispon�vel
		this.setStatus(Status.ALTERANDO);
		// Crio e abro a janela de cadastro
		this.uiAdministrador = new AdministradorMB(this);
		this.uiAdministrador.atualizarCampos(atual);
	
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// N�o h� departamento em manipula��o
		this.atual = null;
		// Fecho a janela
		this.uiAdministrador.fechar();
		// Informo que o controlador est� dispon�vel
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o t�rmino deste caso de uso
		ctrlManterAdministradores.terminarCasoDeUsoAlterarAdministrador();
	}

	/** 
	 * 
	 */
	public void cancelarAlterar() throws DadosException, ControleException {
		// Se o controlador n�o tinha ativado uma inclusao, n�o fa�o nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel cancelar uma opera��o de altera��o"));
		// Termino o caso de uso
		this.terminar();
	}

	/**
	 * @throws model.DadosException  
	 * 
	 */
	public void alterar(Administrador administrador) throws DadosException, ControleException, model.DadosException {
		// Se o controlador n�o tinha ativado uma inclusao, n�o fa�o nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel concluir uma opera��o de altera��o"));
		// Atualizo os campos
		this.atual = administrador;
	
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
