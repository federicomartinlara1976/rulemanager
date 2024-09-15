package net.bounceme.chronos.rulemanager.support;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RuleManagerHelper {
	
	private RuleManagerHelper() {}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
