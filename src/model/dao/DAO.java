package model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import model.Departamento;
import model.Empregado;
import model.Empresa;
import model.Projeto;
import model.ResponsavelEmpresa;
import model.util.DadosException;
import model.util.ErroDeDominio;

public class DAO<T extends IDados> implements IDAO<T>, IDAOSerializavel {
	//
	// CONSTANTES
	//
	/**
	 * Define o tamanho máximo de objetos que podem ser armazenados
	 */
	public static final int TAMANHO_MAXIMO = 20;
	
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para os DAOs existentes. O Map vincula um DAO para cada classe (representada por seu nome)
	 */
	private static Map<Class,DAO> conjDaos = new HashMap<Class,DAO>();
	/**
	 * Referência para o Set que apontará para todos os objetos 
	 * guardados pelo DAO
	 */
	private Set<T> listaObjs;
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAO(Class classeObjetos) {		
		// Aloco memória para o Set
		this.listaObjs = new TreeSet<T>();		
		conjDaos.put(classeObjetos, this);
	}
	
	/**
	 * Inicializa o acesso aos dados
	 */
	public static void inicializarDAOs() {
		// Recupera os DAOs do sistema
		IDAOSerializavel daoDepartamento = (IDAOSerializavel)DAO.getDAO(Departamento.class);
		IDAOSerializavel daoEmpregado = (IDAOSerializavel)DAO.getDAO(Empregado.class);
		IDAOSerializavel daoProjeto = (IDAOSerializavel)DAO.getDAO(Projeto.class);
		IDAOSerializavel daoEmpresa = (IDAOSerializavel)DAO.getDAO(Empresa.class);
		IDAOSerializavel daoResponsavelEmpresa = (IDAOSerializavel)DAO.getDAO(ResponsavelEmpresa.class);

		//
		// Recuperação dos objetos serializados no arquivo c:/dados.dat
		//
		try {
			// Abrindo o arquivo para leitura binária
			FileInputStream fis = new FileInputStream("dados.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			// Solicitação para os DAOs gerenciarem os objetos recuperados do arquivo
			daoDepartamento.recuperarObjetos(ois);
			daoEmpregado.recuperarObjetos(ois);
			daoProjeto.recuperarObjetos(ois);
			daoEmpresa.recuperarObjetos(ois);
			daoResponsavelEmpresa.recuperarObjetos(ois);
			// Fechando o arquivo 
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo dados.dat não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * Fecha o acesso aos dados
	 */
	public static void fecharDAOs() {
		// Recuperando os DAOs do sistema
		IDAOSerializavel daoDepartamento = (IDAOSerializavel)DAO.getDAO(Departamento.class);
		IDAOSerializavel daoEmpregado = (IDAOSerializavel)DAO.getDAO(Empregado.class);
		IDAOSerializavel daoProjeto = (IDAOSerializavel)DAO.getDAO(Projeto.class);
		IDAOSerializavel daoEmpresa = (IDAOSerializavel)DAO.getDAO(Empresa.class);
		IDAOSerializavel daoResponsavelEmpresa = (IDAOSerializavel)DAO.getDAO(ResponsavelEmpresa.class);

		try {
			// Abrindo o arquivo c:/dados.dat para escrita
			FileOutputStream fos = new FileOutputStream("dados.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// Salvando os objetos gerenciados pelos DAOs
			daoDepartamento.salvarObjetos(oos);
			daoEmpregado.salvarObjetos(oos);
			daoProjeto.salvarObjetos(oos);
			daoEmpresa.salvarObjetos(oos);
			daoResponsavelEmpresa.salvarObjetos(oos);
			// Fechando e salvando o arquivo
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}
	
	/**
	 * Método para retornar a única instância existente do DAO
	 * @return
	 */
	public static IDAO getDAO(Class classeObjetos) {
		IDAO dao = conjDaos.get(classeObjetos);
		if(dao == null) 
			dao = new DAO(classeObjetos);
		return dao;
	}
	
	/**
	 * Salva um objeto 
	 * @param novo
	 * @return
	 */
	public synchronized void salvar(T novo) throws DadosException {
		if(!this.listaObjs.add(novo))
			throw new DadosException(new ErroDeDominio("Não foi possível salvar os dados: " + novo));
	}
	
	/**
	 * Remove um objeto
	 * @param obj
	 * @return
	 */
	public synchronized void remover(T obj) throws DadosException {
		if(!this.listaObjs.remove(obj))
			throw new DadosException(new ErroDeDominio("Não foi possível remover os dados: " + obj));
	}
	
	/**
	 * Promove a atualização de um objeto
	 * @param obj
	 * @return
	 */
	public synchronized void atualizar(T obj) throws DadosException {
		// Nada a ser feito
	}
	
	/**
	 * Recupera um objeto pela posição
	 * @param i
	 * @return
	 */
	public T recuperar(int pos) throws DadosException {
		if(pos < 0)
			throw new DadosException(new ErroDeDominio("Posição para recuperação de objeto inválida: " + pos));			
		int i = 0;
		for(T e: this.listaObjs)
			if(i++ == pos)
				return e;
		throw new DadosException(new ErroDeDominio("Posição para recuperação de objeto inválida: " + pos));			
	}
	
	/**
	 * Recupera um objeto pela chave que é cpf
	 * @param chave
	 * @return
	 */
	public T recuperarPelaChave(Object chave) throws DadosException{
		for(T e : this.listaObjs)
			if(e.getChave().equals(chave))
				return e;
		throw new DadosException(new ErroDeDominio("Chave para recuperação de objeto inválida: " + chave));			
	}
	
	/**
	 * Retorna o número de objetos sendo gerenciados pelo DAO
	 * @return
	 */
	public synchronized int getNumObjs(){
		return this.listaObjs.size();
	}

	/**
	 * Retorna uma cópia da lista de objetos
	 * @return
	 */
	public synchronized List getListaObjs() {
		return new ArrayList(this.listaObjs);
	}
	
	/**
	 * Recupera os objetos 
	 * @return
	 */
	public void recuperarObjetos(ObjectInputStream ois) 
			throws IOException, ClassNotFoundException {
		// Recupera o array de objetos
		this.listaObjs = (Set<T>)ois.readObject();
	}

	/**
	 * Salva os objetos 
	 * @return
	 */
	public void salvarObjetos(ObjectOutputStream oos) 
			throws IOException {
		// Salva o array de objetos
		oos.writeObject(this.listaObjs);
	}
}
