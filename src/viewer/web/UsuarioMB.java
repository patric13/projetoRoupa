package viewer.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import viewer.web.util.JSFUtil;
import dominio.Pessoa;
import dominio.Usuario;
import dominio.dao.PessoaDAO;
import dominio.dao.UsuarioDAO;

@ManagedBean(name = "userMB")
@RequestScoped
public class UsuarioMB
{
	private Usuario usuario = new Usuario();
	private UsuarioDAO dao = new UsuarioDAO();
	private Pessoa pessoa;
	private List<Usuario> usuarios = null;
	private List<Pessoa> pessoas = null;

	public List<Usuario> getUsuarios()
	{
		if (this.usuarios == null)
			this.usuarios = this.dao.lerTodos();

		return this.usuarios;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}
	
	public List<Pessoa> getPessoas(){
		if (this.pessoas == null)
			this.pessoas = new PessoaDAO().lerTodos();

		return this.pessoas;

		
	}

	public Pessoa getPessoa(){
		return this.pessoa;
	}
	

	
	
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	/**
	 * 
	 */
	public String acaoListar()
	{
		return "usuarioListar";
	}

	/**
	 * 
	 */
	public String acaoAbrirInclusao()
	{
		// limpar o objeto da página
		this.setUsuario(new Usuario());

		return "usuarioEditar";
	}

	/**
	 * 
	 */
	public String acaoAbrirAlteracao()
	{
		Long id = JSFUtil.getParametroLong("itemId");
		Usuario objetoDoBanco = this.dao.lerPorId(id);
		this.setUsuario(objetoDoBanco);

		return "usuarioEditar";
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
		if ((this.getUsuario().getId() != null) && (this.getUsuario().getId().longValue() == 0))
			this.getUsuario().setId(null);

		/**
		 * Se o usuário não tiver ID, deve testar se existe o mesmo no banco
		 */
		if (this.getUsuario().getId() == null)
		{
			Usuario objetoDoBanco = this.dao.lerPorLogin(this.getUsuario().getLogin());

			if (objetoDoBanco != null)
			{
				JSFUtil.retornarMensagemErro("Outro usuário com o mesmo login já existe no sistema.", null, null);
				return null; // volta p/mesma página
			}
		}

		this.dao.salvar(this.getUsuario());
		// limpa a lista
		this.usuarios = null;

		// limpar o objeto da página
		this.setUsuario(new Usuario());

		return "usuarioListar";
	}

	/**
	 * 
	 */
	public String acaoCancelar()
	{
		// limpar o objeto da página
		this.setUsuario(new Usuario());

		return "usuarioListar";
	}

	/**
	 * 
	 */
	public String acaoExcluir()
	{
		Long id = JSFUtil.getParametroLong("itemId");
		Usuario objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		// limpar o objeto da página
		this.setUsuario(new Usuario());
		// limpa a lista
		this.usuarios = null;

		return "usuarioListar";
	}

}
