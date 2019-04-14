package viewer.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import viewer.web.util.JSFUtil;
import dominio.Grupo;
import dominio.Roupa;
import dominio.dao.GrupoDAO;
import dominio.dao.RoupaDAO;

@ManagedBean(name = "roupaMB")
@RequestScoped
public class RoupaMB {
	private Grupo filtroGrupo = null;

	private Roupa roupa = new Roupa();
	private RoupaDAO dao = new RoupaDAO();

	private List<Roupa> roupas = null;
	private List<Grupo> grupos = null;

	public List<Roupa> getRoupas() {
		//if (this.roupas == null)
			this.roupas = this.dao.filtrarPorGrupo(this.filtroGrupo);

		return this.roupas;
	}

	public List<Grupo> getGrupos() {
		if (this.grupos == null)
			this.grupos = new GrupoDAO().lerTodos();

		return this.grupos;
	}


	public Grupo getFiltroGrupo() {
		return filtroGrupo;
	}

	public void setFiltroGrupo(Grupo filtroGrupo) {
		this.filtroGrupo = filtroGrupo;
	}

	public Roupa getRoupa() {
		return roupa;
	}

	public void setRoupa(Roupa roupa) {
		this.roupa = roupa;
	}

	/**
	 * 
	 */
	public String acaoListar() {
		return "roupaListar";
	}

	/**
	 * 
	 */
	public String acaoAbrirInclusao() {
		// limpar o objeto da página
		this.setRoupa(new Roupa());

		return "roupaEditar";
	}

	/**
	 * 
	 */
	public String acaoAbrirAlteracao() {
		// pega o ID escolhido que veio no parâmetro
		Long id = JSFUtil.getParametroLong("itemId");
		Roupa objetoDoBanco = this.dao.lerPorId(id);
		this.setRoupa(objetoDoBanco);

		return "roupaEditar";
	}

	/**
	 * 
	 */
	public String acaoSalvar() {
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getRoupa().getId() != null)
				&& (this.getRoupa().getId().longValue() == 0))
			this.getRoupa().setId(null);

		this.dao.salvar(this.getRoupa());
		// limpa a lista
		this.roupas = null;

		// limpar o objeto da página
		this.setRoupa(new Roupa());

		return "roupaListar";
	}

	/**
	 * 
	 */
	public String acaoCancelar() {
		// limpar o objeto da página
		this.setRoupa(new Roupa());

		return "roupaListar";
	}

	/**
	 * 
	 */
	public String acaoExcluir() {
		Long id = JSFUtil.getParametroLong("itemId");
		Roupa objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		// limpar o objeto da página
		this.setRoupa(new Roupa());
		// limpa a lista
		this.roupas = null;

		return "roupaListar";
	}

}
