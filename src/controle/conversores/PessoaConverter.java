package controle.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dominio.Pessoa;
import dominio.dao.PessoaDAO;

@FacesConverter(value="pessoa-converter", forClass=Pessoa.class)
public class PessoaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent componente,
			String valor) {

		if (valor == null || valor.length() == 0)
			return null;

		PessoaDAO dao = new PessoaDAO();
		Pessoa pessoa = dao.lerPorcpf(new String(valor));

		return pessoa;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent componente,
			Object objeto) {

		if (objeto instanceof Pessoa)
			return ((Pessoa) objeto).getCpf().toString();

		return null;
	}

}
