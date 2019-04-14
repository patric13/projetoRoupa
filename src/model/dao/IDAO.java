package model.dao;

import java.util.List;

import model.util.DadosException;

public interface IDAO<T> {
	//
	// CONSTANTES
	//
	/**
	 * Define o tamanho máximo de objetos que podem ser armazenados
	 */
	public static final int TAMANHO_MAXIMO = 20;

	/**
	 * Salva um objeto 
	 * @param novo
	 * @return
	 */
	public abstract void salvar(T novo) throws DadosException;

	/**
	 * Remove um objeto
	 * @param obj
	 * @return
	 */
	public abstract void remover(T obj) throws DadosException;

	/**
	 * Promove a atualização de um objeto
	 * @param obj
	 * @return
	 */
	public abstract void atualizar(T obj) throws DadosException;

	/**
	 * Recupera um objeto pela posição
	 * @param posicao
	 * @return
	 */
	public abstract T recuperar(int posicao) throws DadosException;

	/**
	 * Recupera um objeto pela chave
	 * @param sigla
	 * @return
	 */
	public abstract T recuperarPelaChave(Object chave) throws DadosException;

	/**
	 * Retorna o número de objetos sendo gerenciados pelo DAO
	 * @return
	 */
	public abstract int getNumObjs() throws DadosException;

	/**
	 * Retorna uma cópia da lista de objetos
	 * @return
	 */
	public abstract List getListaObjs() throws DadosException;
}