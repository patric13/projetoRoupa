package dominio.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import controle.util.JpaDAO;
import dominio.Vaga;


public class VagaDAO extends JpaDAO<Vaga>
{

	public VagaDAO()
	{
		super();
	}

	public VagaDAO(EntityManager manager)
	{
		super(manager);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Vaga> lerTodos() {
		List<Vaga> resultado;

		Query consulta = this.getEntityManager().createQuery("from Vaga");

		try
		{
			resultado = (List<Vaga>) consulta.getResultList();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;

	}
	

	
	

}
