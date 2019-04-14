package dominio.dao;

import javax.persistence.EntityManager;

import controle.util.JpaDAO;
import dominio.Grupo;

public class GrupoDAO extends JpaDAO<Grupo>
{

	public GrupoDAO()
	{
		super();
	}

	public GrupoDAO(EntityManager manager)
	{
		super(manager);
	}

}
