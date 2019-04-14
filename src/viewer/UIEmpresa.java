package viewer;

import dominio.Empresa;

public interface UIEmpresa {

	/**
	 * Solicita a efetiva��o da a��o de inclus�o ou altera��o
	 */
	public abstract void solicitarExecucaoDeEfetivacao();

	/**
	 * Solicita o cancelamento da a��o de inclus�o ou altera��o
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