package controle.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import dominio.Empresa;
import dominio.dao.EmpresaDAO;

@FacesConverter(value ="empresa-converter", forClass = Empresa.class)
public class EmpresaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent componente, String valor) {

		if (valor == null || valor.length() == 0)
			return null;

		EmpresaDAO dao = new EmpresaDAO();
		Empresa empresa = dao.lerPorcnpj(new String(valor));

		return empresa;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent componente, Object objeto) {

		if (objeto instanceof Empresa)
			return ((Empresa) objeto).getCnpj().toString();

		return null;
	}

}
