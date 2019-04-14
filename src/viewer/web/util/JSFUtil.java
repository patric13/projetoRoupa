package viewer.web.util;

import java.io.IOException;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JSFUtil
{
	public static void redirecionar(String pagina) {
		try {
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.sendRedirect(pagina);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void navigation(String pagina){
		FacesContext fc = FacesContext.getCurrentInstance();
		ConfigurableNavigationHandler nav
		   = (ConfigurableNavigationHandler)
			fc.getApplication().getNavigationHandler();

		nav.performNavigation(pagina);
		
	}
	
	public static void retornarMensagemErro(String mensagem, String detalhe, String idComponentePagina)
	{
		retornarMensagem(FacesMessage.SEVERITY_ERROR, mensagem, detalhe, idComponentePagina);
	}

	public static void retornarMensagemFatal(String mensagem, String detalhe, String idComponentePagina)
	{
		retornarMensagem(FacesMessage.SEVERITY_FATAL, mensagem, detalhe, idComponentePagina);
	}

	public static void retornarMensagemAviso(String mensagem, String detalhe, String idComponentePagina)
	{
		retornarMensagem(FacesMessage.SEVERITY_WARN, mensagem, detalhe, idComponentePagina);
	}

	public static void retornarMensagemInfo(String mensagem, String detalhe, String idComponentePagina)
	{
		retornarMensagem(FacesMessage.SEVERITY_INFO, mensagem, detalhe, idComponentePagina);
	}

	private static void retornarMensagem(Severity status, String mensagem, String detalhe, String idComponentePagina)
	{
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(idComponentePagina, new FacesMessage(status, mensagem, detalhe));
	}

	/**
	 * Método que lê um parâmetro de um link ou botão passado através da TAG
	 * <code>&lt;f:param&gt;</code>.
	 * 
	 * @param nomeDoParametro
	 *           valor usado no atributo name da TAG "param"
	 * @return o valor passado no envio da requisição como uma String
	 */
	public static String getParametro(String nomeDoParametro)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext().getRequestParameterMap();
		String valor = map.get(nomeDoParametro);

		return valor;
	}

	/**
	 * Método que lê um parâmetro de um link ou botão passado através da TAG
	 * <code>&lt;f:param&gt;</code>, convertendo o valor para Long.
	 * 
	 * @param nomeDoParametro
	 *           valor usado no atributo name da TAG "param"
	 * @return o valor passado no envio da requisição como um Long
	 */
	public static Long getParametroLong(String nomeDoParametro)
	{
		String valor = getParametro(nomeDoParametro);
		Long valorLong;
		try
		{
			valorLong = new Long(valor);
		}
		catch (Exception e)
		{
			valorLong = null;
		}

		return valorLong;
	}

	/**
	 * Método que lê um parâmetro de um link ou botão passado através da TAG
	 * <code>&lt;f:param&gt;</code>, convertendo o valor para Integer.
	 * 
	 * @param nomeDoParametro
	 *           valor usado no atributo name da TAG "param"
	 * @return o valor passado no envio da requisição como um Integer
	 */
	public static Integer getParametroInteger(String nomeDoParametro)
	{
		String valor = getParametro(nomeDoParametro);
		Integer valorInt;
		try
		{
			valorInt = new Integer(valor);
		}
		catch (Exception e)
		{
			valorInt = null;
		}

		return valorInt;
	}

	/**
	 * Método que pega a instância de um objeto pelo nome da variável usada na EL
	 * (Expression Language).
	 * 
	 * <br />
	 * <br />
	 * Exemplo: para pegar um Managed Bean chamado loginMB, deve-se usar o método
	 * da seguinte forma:
	 * 
	 * <br />
	 * <code>LoginMB login = (LoginMB) JSFUtil.getVariavelApplication("loginMB");</code>
	 */
	public static Object getVariavelApplication(String nomeDaVariavel)
	{
		ELContext elContexto = FacesContext.getCurrentInstance().getELContext();
		Object obj = FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContexto, null, nomeDaVariavel);

		return obj;
	}

	/**
	 * Método que pega o valor de um expressão EL (Expression Language).
	 * 
	 * <br />
	 * <br />
	 * Exemplo: para pegar o valor de #{usuario.nome}, deve-se usar o método da
	 * seguinte forma:
	 * 
	 * <br />
	 * <code>String nome = (String) JSFUtil.getValorExpressao("usuario.nome");</code>
	 */
	public static Object getValorExpressao(String expressao)
	{
		FacesContext contexto = FacesContext.getCurrentInstance();
		Application app = contexto.getApplication();

		ValueExpression expression = app.getExpressionFactory().createValueExpression(contexto.getELContext(), String.format("#{%s}", expressao), Object.class);

		Object obj = expression.getValue(contexto.getELContext());

		return obj;
	}

	/**
	 * Método que pega o objeto HttpSession associado com a requisição atual.
	 */
	public static HttpSession getHttpSession()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

		return session;
	}

	/**
	 * Método que pega o objeto HttpServletRequest associado com a requisição
	 * atual.
	 */
	public static HttpServletRequest getServletRequest()
	{
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * Método que pega o objeto HttpServletResponse associado com a requisição
	 * atual.
	 */
	public static HttpServletResponse getServletResponse()
	{
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	/**
	 * Método que pega o objeto ExternalContext do contexto do Faces.
	 */
	public static ExternalContext getExternalContext()
	{
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Método que pega o objeto Map do Application do contexto do Faces.
	 */
	public static Map<String, Object> getApplicationMap()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
	}

	/**
	 * Método que pega o objeto Map do Session do contexto do Faces.
	 */
	public static Map<String, Object> getSessionMap()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	/**
	 * Método que pega o objeto Map do Request do contexto do Faces.
	 */
	public static Map<String, Object> getRequestMap()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
	}

	/**
	 * Método que pega o objeto Map com os Parâmetros da requisição.
	 */
	public static Map<String, String> getRequestParameterMap()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	}

	/**
	 * Método que pega o objeto ServletContext do contexto do Faces.
	 */
	public static ServletContext getServletContext()
	{
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

}
