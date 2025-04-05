package net.bounceme.chronos.rulemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.app.usuarios.commons.model.Method;

@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {

}
