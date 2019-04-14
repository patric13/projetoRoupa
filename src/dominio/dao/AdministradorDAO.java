package dominio.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import controle.util.JpaDAO;
import dominio.Administrador;

public class AdministradorDAO extends JpaDAO<Administrador>
{

	public AdministradorDAO()
	{
		super();
	}

	public AdministradorDAO(EntityManager manager)
	{
		super(manager);
	}

	public Administrador lerPorcpf(String cpf)
	{
		Administrador resultado;

		Query consulta = this.getEntityManager().createQuery("from Administrador r where r.cpf = :cpf");
		consulta.setParameter("cpf", cpf);

		try
		{
			resultado = (Administrador) consulta.getSingleResult();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Administrador> lerTodos() {
		List<Administrador> resultado;

		Query consulta = this.getEntityManager().createQuery("from Administrador r order by r.cpf");

		try
		{
			resultado = (List<Administrador>) consulta.getResultList();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;

	}
	
	
	

}
