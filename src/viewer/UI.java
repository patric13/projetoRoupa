package viewer;

import java.util.List;

import model.util.IDadosParaTabela;

public interface UI {
	/**
	 * Limpa todos os dados presentes na UI
	 */
	public abstract void limpar();

	/**
	 * Solicita a apresentação da UI
	 */
	public abstract void exibir();
	
	/**
	 * Solicita o fechamento da UI
	 */
	public abstract void fechar();
	
	/**
	 * Monta visualmente a UI
	 */
	public abstract void criarUI();
}
