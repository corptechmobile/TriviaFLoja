package br.com.coletor.exceptions;

public class SincColetorException extends Exception {

	private static final long serialVersionUID = -4536467807207853342L;
	
	public SincColetorException() {}

	public SincColetorException(String arg0) {
		super(arg0);
	}

	public SincColetorException(Throwable arg0) {
		super(arg0);
	}

	public SincColetorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
