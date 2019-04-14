package dominio.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import controle.util.JpaDAO;
import dominio.Roupa;

public class RoupaDAO extends JpaDAO<Roupa>
{

	public RoupaDAO()
	{
		super();
	}

	public RoupaDAO(EntityManager manager)
	{
		super(manager);
	}

	public Roupa lerPorLogin(String descricao)
	{
		Roupa resultado;

		Query consulta = this.getEntityManager().createQuery("from Roupa r where r.descricao = :descricao");
		consulta.setParameter("descricao", descricao);

		try
		{
			resultado = (Roupa) consulta.getSingleResult();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Roupa> lerTodos() {
		List<Roupa> resultado;

		Query consulta = this.getEntityManager().createQuery("from Roupa r order by r.descricao");

		try
		{
			resultado = (List<Roupa>) consulta.getResultList();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;

	}
	

	
	

}
