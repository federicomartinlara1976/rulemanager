package net.bounceme.chronos.rulemanager.service;

import java.util.Date;
import java.util.List;

import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;

public interface RuleService {
	
	List<RuleDTO> listAll();
	
	List<RuleDTO> listByDate(Date from, Date to);
	
	List<RuleDTO> listByMethod(String method);
	
	List<RuleDTO> listByAccess(String access);
	
	RuleDTO getRule(Long id);
	
	void deleteRule(Long id);
}
