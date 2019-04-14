package viewer.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import controller.CtrlSessaoUsuario;
import controller.util.ICtrlCasoDeUso;
import dominio.Administrador;
import dominio.DadosException;
import dominio.Empresa;
import dominio.Pessoa;
import dominio.ResponsavelEmpresa;
import dominio.TipoArea;
import dominio.TipoVaga;
import dominio.Vaga;
import dominio.dao.AdministradorDAO;
import dominio.dao.EmpresaDAO;
import dominio.dao.PessoaDAO;
import dominio.dao.ResponsavelEmpresaDAO;
import dominio.dao.VagaDAO;
import viewer.UIVaga;
import viewer.web.util.JSFUtil;

@ManagedBean(name = "vagaMB")
@SessionScoped
public class VagaMB extends TemplateMB implements Serializable, UIVaga {
	private ICtrlCasoDeUso ctrl = null;

	private Vaga vaga = new Vaga();
	private VagaDAO dao = new VagaDAO();
	private Empresa empresa;
	private ResponsavelEmpresa responsavelEmpresa;
	private List<Vaga> vagas = null;
	private List<Empresa> empresas = null;
	private List<ResponsavelEmpresa> responsavelEmpresas = null;
	private List<TipoVaga> tipos = null;
	private TipoVaga tipo;
	
	
	
	public List<Empresa> getEmpresas() {
		if (this.empresas == null)
			this.empresas = new EmpresaDAO().lerTodos();

		return this.empresas;

	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public TipoVaga[] getTipoVaga(){
		return tipo.values();
	}
	


	public ResponsavelEmpresa getResponsavelEmpresa() {
		return this.responsavelEmpresa;
	}

	public VagaMB() {

	}

	public VagaMB(ICtrlCasoDeUso c) {
		this.ctrl = c;
		this.exibir();

	}

	public List<Vaga> getVagas() {
		if (this.vagas == null)
			this.vagas = new VagaDAO().lerTodos();

		return this.vagas;
	}

	public Vaga getVaga() {
		if ((Vaga) JSFUtil.getSessionMap().get("vaga") != null)
			this.vaga = (Vaga) JSFUtil.getSessionMap().get("vaga");
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;

	}

	/**
	 * 
	 */
	public String acaoListar() {
		return "vaga";
	}

	/**
	 * 
	 */

	/**
	 * 
	 */
	public String acaoAbrirAlteracao() {
		// pega o ID escolhido que veio no parâmetro
		Long id = JSFUtil.getParametroLong("itemId");
		Vaga objetoDoBanco = this.dao.lerPorId(id);
		this.setVaga(objetoDoBanco);

		return "vagaEditar";
	}

	/**
	 * @throws DadosException
	 * 
	 */
	public String acaoSalvar() throws DadosException {
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getVaga().getId() != null) && (this.getVaga().getId().longValue() == 0))
			this.getVaga().setId(null);
		
		
		CtrlSessaoUsuario ctrlSessao = (CtrlSessaoUsuario) JSFUtil.getSessionMap().get(CTRL_SESSAO);
		responsavelEmpresa = ctrlSessao.getResponsavel();
		if(responsavelEmpresa !=null){
		this.getVaga().setResponsavel(responsavelEmpresa);
		this.getVaga().setEmpresa(responsavelEmpresa.getEmpresa());
		}
	
		this.dao.salvar(this.getVaga());
		// limpa a lista
		this.vagas = null;
		this.setVaga(null);
		// limpar o objeto da página
		this.setVaga(new Vaga());

		return "home";
	}

	/**
	 * 
	 */
	public String acaoCancelar() {
		// limpar o objeto da página
		this.setVaga(new Vaga());

		return "vagaListar";
	}

	/**
	 * 
	 */
	public String acaoExcluir() {
		Long id = JSFUtil.getParametroLong("itemId");
		Vaga objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		// limpar o objeto da página
		this.setVaga(new Vaga());
		// limpa a lista
		this.vagas = null;

		return "vagaListar";
	}

	public void exibir() {
		JSFUtil.navigation("vagaEditar");

	}

	@Override
	public void fechar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void solicitarExecucaoDeEfetivacao() {
		// TODO Auto-generated method stub

	}

	@Override
	public void solicitarCancelamentoDeEfetivacao() {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizarCampos(Vaga vaga) {
		JSFUtil.getSessionMap().put("vaga", vaga);

	}

}
