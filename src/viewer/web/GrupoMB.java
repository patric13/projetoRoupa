package viewer.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import viewer.web.util.JSFUtil;
import dominio.Grupo;
import dominio.dao.GrupoDAO;

@ManagedBean(name = "grupoMB")
@RequestScoped
public class GrupoMB
{
	private Grupo grupo = new Grupo();
	private GrupoDAO dao = new GrupoDAO();

	private List<Grupo> grupos = null;

	public List<Grupo> getGrupos()
	{
		if (this.grupos == null)
			this.grupos = this.dao.lerTodos();

		return this.grupos;
	}

	public Grupo getGrupo()
	{
		return grupo;
	}

	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}

	/**
	 * 
	 */
	public String acaoListar()
	{
		return "grupoListar";
	}

	/**
	 * 
	 */
	public String acaoAbrirInclusao()
	{
		// limpar o objeto da página
		this.setGrupo(new Grupo());

		return "grupoEditar";
	}

	/**
	 * 
	 */
	public String acaoAbrirAlteracao()
	{
		// pega o ID escolhido que veio no parâmetro
		Long id = JSFUtil.getParametroLong("itemId");
		Grupo objetoDoBanco = this.dao.lerPorId(id);
		this.setGrupo(objetoDoBanco);

		return "grupoEditar";
	}

	/**
	 * 
	 */
	public String acaoSalvar()
	{
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getGrupo().getId() != null) && (this.getGrupo().getId().longValue() == 0))
			this.getGrupo().setId(null);

		this.dao.salvar(this.getGrupo());
		// limpa a lista
		this.grupos = null;

		// limpar o objeto da página
		this.setGrupo(new Grupo());

		return "grupoListar";
	}

	/**
	 * 
	 */
	public String acaoCancelar()
	{
		// limpar o objeto da página
		this.setGrupo(new Grupo());

		return "grupoListar";
	}

	/**
	 * 
	 */
	public String acaoExcluir()
	{
		Long id = JSFUtil.getParametroLong("itemId");
		Grupo objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		// limpar o objeto da página
		this.setGrupo(new Grupo());
		// limpa a lista
		this.grupos = null;

		return "grupoListar";
	}

}
