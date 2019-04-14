package controle.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dominio.Grupo;
import dominio.ResponsavelEmpresa;
import dominio.dao.ResponsavelEmpresaDAO;

@FacesConverter(value="responsavelEmpresa-converter", forClass=Grupo.class)
public class ResonsavelEmpresaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent componente,
			String valor) {

		if (valor == null || valor.length() == 0)
			return null;

		ResponsavelEmpresaDAO dao = new ResponsavelEmpresaDAO();
		ResponsavelEmpresa responsavelEmpresa = dao.lerPorcpf(new String(valor));

		return responsavelEmpresa;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent componente,
			Object objeto) {

		if (objeto instanceof Grupo)
			return ((ResponsavelEmpresa) objeto).getCpf().toString();

		return null;
	}

}
