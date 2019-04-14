package controle.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dominio.Grupo;
import dominio.dao.GrupoDAO;

@FacesConverter(value="grupo-converter", forClass=Grupo.class)
public class GrupoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent componente,
			String valor) {

		if (valor == null || valor.length() == 0)
			return null;

		GrupoDAO dao = new GrupoDAO();
		Grupo grupo = dao.lerPorId(new Long(valor));

		return grupo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent componente,
			Object objeto) {

		if (objeto instanceof Grupo)
			return ((Grupo) objeto).getId().toString();

		return null;
	}

}
