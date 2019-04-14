package viewer;

import java.util.Collection;
import java.util.List;

import model.Departamento;
import model.Empregado;

public interface UIProjeto extends UI {
	/**
	 * Solicita a efetivação da ação de inclusão ou alteração
	 */
	public abstract void solicitarExecucaoDeEfetivacao();
	/**
	 * Solicita o cancelamento da ação de inclusão ou alteração
	 */
	public abstract void solicitarCancelamentoDeEfetivacao();
	/**
	 * Atualiza os campos na UI
	 */
	public abstract void atualizarCampos(List<Object> todosDeptos, List<Object> todosEmps);
	/**
	 * Atualiza os campos na UI
	 */
	public abstract void atualizarCampos(String nome, List<Object> todosDeptos, List<Object> todosEmps, Object depto, List<Object> empsSelecionados);
}