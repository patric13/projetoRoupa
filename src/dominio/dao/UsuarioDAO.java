package dominio.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import controle.util.JpaDAO;
import dominio.Usuario;

public class UsuarioDAO extends JpaDAO<Usuario>
{

	public UsuarioDAO()
	{
		super();
	}

	public UsuarioDAO(EntityManager manager)
	{
		super(manager);
	}

	public Usuario lerPorLogin(String login)
	{
		Usuario resultado;

		Query consulta = this.getEntityManager().createQuery("from Usuario u where u.login = :login");
		consulta.setParameter("login", login);

		try
		{
			resultado = (Usuario) consulta.getSingleResult();
		}
		catch (NoResultException e)
		{
			resultado = null;
		}

		return resultado;
	}

}
