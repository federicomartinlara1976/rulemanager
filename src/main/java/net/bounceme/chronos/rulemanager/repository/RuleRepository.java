package net.bounceme.chronos.rulemanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.app.usuarios.commons.model.Access;
import net.bounceme.chronos.app.usuarios.commons.model.Method;
import net.bounceme.chronos.app.usuarios.commons.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

	@Query("select r from Rule r where r.creationTime >= ?1 and r.creationTime <= ?2")
	List<Rule> getByCreationDate(Date from, Date to);
	
	@Query("select r from Rule r where r.method.name = ?1")
	List<Rule> getByMethod(String value);
	
	@Query("select r from Rule r where r.access.name = ?1")
	List<Rule> getByAccess(String value);
	
	@Query("select r from Rule r where r.path = ?1 and r.method = ?2 and r.access = ?3")
	Rule findRule(String path, Method method, Access access);	
}
