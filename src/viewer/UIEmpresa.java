package viewer;

import dominio.Empresa;

public interface UIEmpresa {

	/**
	 * Solicita a efetivação da ação de inclusão ou alteração
	 */
	public abstract void solicitarExecucaoDeEfetivacao();

	/**
	 * Solicita o cancelamento da ação de inclusão ou alteração
	 */
	public abstract void solicitarCancelamentoDeEfetivacao();

	/**
	 * Solicita o fechamento da janela
	 */
	public abstract void fechar();

	/**
	 * Atualiza os campos na UI
	 * @param sigla
	 * @param nome
	 */
	public abstract void atualizarCampos(Empresa empresa);

}