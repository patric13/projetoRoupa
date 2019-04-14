package controller;

import java.util.List;

import model.Departamento;
import model.Empregado;
import model.dao.DAO;
import model.dao.IDAO;
import model.util.DadosException;
import viewer.UIDepartamento;
import viewer.UIEmpregado;
import viewer.ViewerManager;
import viewer.desktop.JanelaEmpregado;
import controller.util.ControleException;
import controller.util.ErroDeControle;
import controller.util.ICtrlCasoDeUso;

public class CtrlAlterarEmpregado implements ICtrlCasoDeUso {
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
	private final CtrlManterEmpregados ctrlManterEmpregados;

	/**
	 * Referência para o objeto a ser atualizado 
	 */
	private Empregado atual;
	
	/**
	 * Referência para a janela Empregado que permitirá a inclusão e
	 * alteração do Empregado
	 */
	private UIEmpregado uiEmpregado;

	/**
	 * Referência para o objeto DaoEmpregado
	 */
	private IDAO<Empregado> dao = (IDAO<Empregado>)DAO.getDAO(Empregado.class);

	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Status status;

	//
	// MÉTODOS
	//

	/**
	 * Construtor da classe CtrlManterEmpregados
	 */
	public CtrlAlterarEmpregado(CtrlManterEmpregados ctrl, Empregado d) throws ControleException, DadosException {
		// Guardo a referência para o controlador do programa
		this.ctrlManterEmpregados = ctrl;
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
		// Recupero os objetos Departamento
		IDAO<Departamento> daoDepartamento = (IDAO<Departamento>)DAO.getDAO(Departamento.class);
		List<Object> deptos = daoDepartamento.getListaObjs();		
		// Crio e abro a janela de cadastro
		this.uiEmpregado = (UIEmpregado)ViewerManager.obterViewer(this, UIEmpregado.class);
		// Solicito à interface que atualize os campos 
		// Informo os valores para a janela
		this.uiEmpregado.atualizarCampos(this.atual.getCpf(),this.atual.getMatrFuncional(),
                this.atual.getNome(),this.atual.getDepto(), deptos);
		// Solicito à interface que carregue os objetos
		this.uiEmpregado.exibir();
	}

	/** 
	 * 
	 */
	public void terminar() throws DadosException, ControleException {
		if(this.status == Status.ENCERRADO)
			return;
		// Não há Empregado em manipulação
		this.atual = null;
		// Fecho a janela
		this.uiEmpregado.fechar();
		// Informo que o controlador está disponível
		this.setStatus(Status.ENCERRADO);
		// Notifico ao controlador do programa o término deste caso de uso
		ctrlManterEmpregados.terminarCasoDeUsoAlterarEmpregado();
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
	public void alterar(String cpf, int matrFuncional, String nome, Object depto) throws DadosException, ControleException {
		// Se o controlador não tinha ativado uma inclusao, não faço nada!
		if(this.status != Status.ALTERANDO)
			throw new ControleException(new ErroDeControle("Não é possível concluir uma operação de alteração"));
		// Atualizo os campos
		this.atual.setCpf(cpf);
		this.atual.setMatrFuncional(matrFuncional);
		this.atual.setNome(nome);
		this.atual.setDepto((Departamento)depto);
		// Salvo o objeto Empregado usando o DAO
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
