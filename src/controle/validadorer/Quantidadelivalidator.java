package controle.validadorer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="quantidade-validator")
public class Quantidadelivalidator {
	
	public void validate(FacesContext contexto, UIComponent campo, Object valor)
			throws ValidatorException {
		
		String valorS = (String) valor;
		if (valorS.indexOf("1") <= 0)
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "quantidade é inválido", null));
		
	}

}
