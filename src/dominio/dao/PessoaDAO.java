package dominio.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import controle.util.JpaDAO;
import dominio.Pessoa;

public class PessoaDAO extends JpaDAO<Pessoa>
{

	public PessoaDAO()
	{
		super();
	}

	public PessoaDAO(EntityManager manager)
	{
		super(manager);
	}

	public Pessoa lerPorcpf(String cpf)
	{
		Pessoa resultado;

		Query consulta = this.getEntityManager().createQuery("from Pessoa r where r.cpf = :cpf");
		consulta.setParameter("cpf", cpf);

		try
		{
			resultado = (Pessoa) consulta.getSingleResult();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> lerTodos() {
		List<Pessoa> resultado;

		Query consulta = this.getEntityManager().createQuery("from Pessoa r order by r.cpf");

		try
		{
			resultado = (List<Pessoa>) consulta.getResultList();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;

	}
	
	
	

}
