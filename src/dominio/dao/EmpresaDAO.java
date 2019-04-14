package dominio.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import controle.util.JpaDAO;
import dominio.Empresa;

public class EmpresaDAO extends JpaDAO<Empresa>
{

	public EmpresaDAO()
	{
		super();
	}

	public EmpresaDAO(EntityManager manager)
	{
		super(manager);
	}

	public Empresa lerPorcnpj(String cnpj)
	{
		Empresa resultado;

		Query consulta = this.getEntityManager().createQuery("from Empresa r where r.cnpj = :cnpj");
		consulta.setParameter("cnpj", cnpj);

		try
		{
			resultado = (Empresa) consulta.getSingleResult();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> lerTodos() {
		List<Empresa> resultado;

		Query consulta = this.getEntityManager().createQuery("from Empresa r order by r.cnpj");

		try
		{
			resultado = (List<Empresa>) consulta.getResultList();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;

	}
	
	
	

}
