package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// lista de erros, com os nomes dos campos que dispararam a exception e suas
	// mensagens de erros
	private Map<String, String> erros = new HashMap<String, String>();

	public ValidacaoException(String msg) {
		super(msg);
	}

	public Map<String, String> getErros() {
		return erros;
	}

	public void addErro(String nomeCampo, String erroMensagem) {
		erros.put(nomeCampo, erroMensagem);
	}
}
