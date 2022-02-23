package es.fpdual.tutorialCrud.service.exception;

import java.util.Map;

public class ValidateExceptionTutorial extends Exception {

	private static final long serialVersionUID = 3432421918014089640L;
	
	private Map<Integer, String> errors;

	public ValidateExceptionTutorial(Map<Integer, String> errors) {
		this.errors = errors;
	}

	public Map<Integer, String> getErrors() {
		return errors;
	}

}
