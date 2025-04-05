package net.bounceme.chronos.rulemanager.exception;

import java.io.Serial;
	
public class IncorrectRuleException extends Exception {
	
	@Serial
	private static final long serialVersionUID = -6372841130712813725L;

	public IncorrectRuleException(String message) {
		super(message);
	}

}
