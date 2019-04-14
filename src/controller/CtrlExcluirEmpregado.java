package controller;

import java.util.List;

import model.Departamento;
import model.Empregado;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIExcluirEmpregado;
import viewer.ViewerManager;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlExcluirEmpregado implements ICtrlCasoDeUso {
	//
	// ATRIBUTOS
	//
	public enum Status {
		EXCLUINDO, ENCERRADO;

		public static void validarTransicaoStatus(Status anterior, Status novo) throws ControleException {
			if(anterior == null && novo == EXCLUINDO || 
			   anterior == EXCLUINDO && novo == ENCERRADO)
				return;
			throw new ControleException(new ErroDeControle("N�o se pode sair do estado " + (anterior==null?"NULO":anterior) + " e ir para o estado " + novo));
		}
	}
	
	/**
	 * Refer�ncia para o controlador do caso de uso manter.
	 */
	private final CtrlManterEmpregados ctrlManterEmpregados;

	/**
	 * Refer�ncia para o objeto a ser atualizado 
	 */
	private Empregado atual;
	
	/**
	 * Refer�ncia para a janela Empregado que permitir� a exclus�o do Empregado
	 */
	private UIExcluirEmpregado uiExcluirEmpregado;

	/**
	 * Refer�ncia para o objeto DaoEmpregado
	 */
	private IDAO<Empregado> dao = (IDAO<Empregado>)DAO.getDAO(Empregado.class);

	/**
	 * Atributo que indica qual opera��o est� em curso
	 */
	private Status status;

	//
	// M�TODOS
	//

	/**
	 * Construtor da classe CtrlManterEmpregados
	 */
	public CtrlExcluirEmpregado(CtrlManterEmpregados ctrl, Empregado d) throws ControleException, DadosException {
		// Guardo a refer�ncia para o controlador do programa
		this.ctrlManterEmpregados = ctrl;
		// Guardo a refer�ncia para o objeto a ser alterado
		this.atual = d;
		// Iniciando o caso de uso
		this.iniciar();
	}

	/** 
	 * 
	 */
	public void iniciar() throws ControleException, DadosException {
		// Informo que o controlador de caso de uso est� excluindo
		this.setStatus(Status.EXCLUINDO);
		// Recupero os objetos Departamento
		IDAO<Departamento> daoDepartamento = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<Object> deptos = daoDepartamento.getListaObjs();		
		// Crio e abro a janela de cadastro
		this.uiExcluirEmpregado = (UIExcluirEmpregado)ViewerManager.obterViewer(this, UIExcluirEmpregado.class);
		// Solicito � interface que atualize os campos 
		// Informo os valores para a janela
		this.uiExcluirEmpregado.atualizarCampos(this.atual.getCpf(),this.atual.getMatrFuncional(),
                this.atual.getNome(),this.atual.getDepto(), deptos);
		// Solicito � interface que carregue os objetos
		this.uiExcluirEmpregado.exibir();
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// N�o h� Empregado em manipula��o
		this.atual = null;
		// Solicito o fechamento da janela
		this.uiExcluirEmpregado.fechar();		
		// Informo que o controlador est� dispon�vel
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o t�rmino deste caso de uso
		ctrlManterEmpregados.terminarCasoDeUsoExcluirEmpregado();
	}

	/** 
	 * 
	 */
	public void cancelarExcluir() throws DadosException, ControleException {
		// Se o controlador n�o tinha ativado uma inclusao, n�o fa�o nada!
		if(this.status != Status.EXCLUINDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel cancelar uma opera��o de exclus�o"));
		// Termino o caso de uso
		this.terminar();
	}

	/** 
	 * 
	 */
	public void excluir() throws ControleException,
			DadosException, ControleException {
		// Se o controlador n�o tinha ativado uma exclus�o, n�o fa�o nada!
		if(this.status != Status.EXCLUINDO)
			throw new ControleException(new ErroDeControle("N�o � poss�vel concluir uma opera��o de exclus�o"));
		// Informo ao objeto que est� desempregado
		this.atual.setStatus(Empregado.Status.DESEMPREGADO);
		// Salvo o objeto Empregado usando o DAO
		dao.remover(this.atual);
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
