package dominio.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import controle.util.JpaDAO;
import dominio.ResponsavelEmpresa;

public class ResponsavelEmpresaDAO extends JpaDAO<ResponsavelEmpresa>
{

	public ResponsavelEmpresaDAO()
	{
		super();
	}

	public ResponsavelEmpresaDAO(EntityManager manager)
	{
		super(manager);
	}

	public ResponsavelEmpresa lerPorcpf(String cpf)
	{
		ResponsavelEmpresa resultado;

		Query consulta = this.getEntityManager().createQuery("from ResponsavelEmpresa r where r.cpf = :cpf");
		consulta.setParameter("cpf", cpf);

		try
		{
			resultado = (ResponsavelEmpresa) consulta.getSingleResult();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResponsavelEmpresa> lerTodos() {
		List<ResponsavelEmpresa> resultado;

		Query consulta = this.getEntityManager().createQuery("from ResponsavelEmpresa r order by r.cpf");

		try
		{
			resultado = (List<ResponsavelEmpresa>) consulta.getResultList();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;

	}
	
	
	

}
