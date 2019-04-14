package model.util;

/**
 * O conceito de interface em Java tem as seguintes características:
 * 	- Se assemelha a uma classe abstrata
 *  - Que não tem atributos
 *  - E todos os seus métodos são abstratos (ou seja não tem implementação)
 * Uma interface é um CONTRATO DE PRESTAÇÃO DE SERVIÇOS que
 * pode ser REALIZADO por uma ou mais classes.
 * 
 * As classes que implementam esta interface serão aquelas cujo estado 
 * pode ser apresentado em uma tabela
 * @author Alessandro Cerqueira
 */
public interface IDadosParaTabela {
	/**
	 * Retorna um array de Strings com os nomes dos campos para apresentação numa
	 * tabela de interface 
	 * dos objetos
	 * @return array de string com os campos da tabela de interface
	 */
	public String[] getCamposDeTabela();
	/**
	 * Retorna um array de Objects com os estados dos campos do IDadosParaTabela para 
	 * apresentação numa tabela 
	 * @return array de objects com valores para tabela
	 */
	public Object[] getDadosParaTabela();
}
