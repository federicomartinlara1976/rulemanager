package net.bounceme.chronos.rulemanager.support;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import net.bounceme.chronos.rulemanager.exception.IncorrectRuleException;

public class RuleManagerHelper {
	
	private RuleManagerHelper() {}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static void failWhenTrue(boolean condition, String message) throws IncorrectRuleException {
		if (condition) {
			throw new IncorrectRuleException(message);
		}
	}
}
