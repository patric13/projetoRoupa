package viewer;

import java.util.List;

import model.util.IDadosParaTabela;

public interface UICadastroResponsavelEmpresas {

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
	public abstract void solicitarExecucaoDeIncluirResponsavelEmpresa();

	/**
	 * Solicita a execu��o do caso de uso Excluir
	 */
	public abstract void solicitarExecucaoDeExcluirResponsavelEmpresa();

	/**
	 * Solicita a execu��o do caso de uso Alterar
	 */
	public abstract void solicitarExecucaoDeAlterarResponsavelEmpresa();

	/**
	 * Solicita o t�rmino da execu��o do caso de uso Manter
	 */
	public abstract void solicitarTerminoDeManterResponsavelEmpresas();

	/**
	 * Solicita a apresenta��o da UI
	 */
	public abstract void exibir();
	
	/**
	 * Solicita o fechamento da UI
	 */
	public abstract void fechar();
}