package viewer;

import java.util.List;

import model.util.IDadosParaTabela;

public interface UICadastroProjetos extends UI {
	/**
	 * Exibe os objetos na UI
	 * @param objetos
	 */
	public abstract void exibirObjetos(List<IDadosParaTabela> objetos);
	/**
	 * Solicita a execução do caso de uso Incluir
	 */
	public abstract void solicitarExecucaoDeIncluirProjeto();

	/**
	 * Solicita a execução do caso de uso Excluir
	 */
	public abstract void solicitarExecucaoDeExcluirProjeto();

	/**
	 * Solicita a execução do caso de uso Alterar
	 */
	public abstract void solicitarExecucaoDeAlterarProjeto();

	/**
	 * Solicita o término da execução do caso de uso Manter
	 */
	public abstract void solicitarTerminoDeManterProjetos();
}