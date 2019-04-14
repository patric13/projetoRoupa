package viewer;

import java.util.List;

import model.util.IDadosParaTabela;

public interface UICadastroEmpresas {

	/**
	 * Exibe os objetos na UI
	 * @param objetos
	 */
	public abstract void exibirObjetos(List<IDadosParaTabela> objetos);

	/**
	 * Limpa todos os dados presentes na UI
	 */
	public abstract void limpar();

	/**
	 * Solicita a execu��o do caso de uso Incluir
	 */
	public abstract void solicitarExecucaoDeIncluirEmpresa();

	/**
	 * Solicita a execu��o do caso de uso Excluir
	 */
	public abstract void solicitarExecucaoDeExcluirEmpresa();

	/**
	 * Solicita a execu��o do caso de uso Alterar
	 */
	public abstract void solicitarExecucaoDeAlterarEmpresa();

	/**
	 * Solicita o t�rmino da execu��o do caso de uso Manter
	 */
	public abstract void solicitarTerminoDeManterEmpresas();

	/**
	 * Solicita a apresenta��o da UI
	 */
	public abstract void exibir();
	
	/**
	 * Solicita o fechamento da UI
	 */
	public abstract void fechar();
}