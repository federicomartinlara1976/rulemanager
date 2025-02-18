package net.bounceme.chronos.rulemanager.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.RoleDTO;
import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Access;
import net.bounceme.chronos.app.usuarios.commons.model.Method;
import net.bounceme.chronos.app.usuarios.commons.model.Role;
import net.bounceme.chronos.app.usuarios.commons.model.Rule;
import net.bounceme.chronos.rulemanager.exception.IncorrectRuleException;
import net.bounceme.chronos.rulemanager.repository.AccessRepository;
import net.bounceme.chronos.rulemanager.repository.MethodRepository;
import net.bounceme.chronos.rulemanager.repository.RoleRepository;
import net.bounceme.chronos.rulemanager.repository.RuleRepository;
import net.bounceme.chronos.rulemanager.support.RuleManagerHelper;

@Component
@Slf4j
public class UpdateRules {

	@Autowired
	private RuleRepository ruleRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AccessRepository accessRepository;

	@Autowired
	private MethodRepository methodRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@SneakyThrows(IncorrectRuleException.class)
	//@CachePut(value = "rules", key = "#rule.id")
	public RuleDTO insertRule(RuleDTO ruleDTO) {
		checkRule(ruleDTO, Boolean.FALSE);

		ruleDTO.setCreationTime(new Date());
		Rule rule = modelMapper.map(ruleDTO, Rule.class);

		if (CollectionUtils.isNotEmpty(ruleDTO.getRoles())) {
			List<Role> roles = new ArrayList<>();

			for (RoleDTO role : ruleDTO.getRoles()) {
				Optional<Role> oRole = roleRepository.findById(role.getId());

                oRole.ifPresent(roles::add);
			}

			rule.setRoles(roles);
		}

		RoleDTO roleDTO = ruleDTO.getRole();
		if (!Objects.isNull(roleDTO)) {
			Optional<Role> oRole = roleRepository.findById(roleDTO.getId());

            oRole.ifPresent(rule::setRole);
		}

		log.info("Save: {}", rule.toString());
		ruleRepository.save(rule);
		return modelMapper.map(rule, RuleDTO.class);
	}

	@Transactional
	@SneakyThrows(IncorrectRuleException.class)
	//@CachePut(value = "rules", key = "#rule.id")
	public RuleDTO updateRule(Long idRule, RuleDTO newDataDTO) {
		checkRule(newDataDTO, Boolean.TRUE);

		Optional<Rule> oRule = ruleRepository.findById(idRule);

		if (oRule.isPresent()) {
			Rule rule = oRule.get();
			rule.setPath(newDataDTO.getPath());
			
			Optional<Method> oMethod = methodRepository.findById(newDataDTO.getMethod().getId());
            oMethod.ifPresent(rule::setMethod);

			Optional<Access> oAccess = accessRepository.findById(newDataDTO.getAccess().getId());
            oAccess.ifPresent(rule::setAccess);
			
			rule.setActive(newDataDTO.getActive());

			updateRoles(rule, newDataDTO);
			rule.setModifyTime(new Date());
			
			log.info("Update: {}", rule.toString());
			ruleRepository.save(rule);

			return modelMapper.map(rule, RuleDTO.class);
		}

		return null;
	}

	private void checkRule(RuleDTO ruleDTO, Boolean isUpdate) throws IncorrectRuleException {
		if (Boolean.FALSE.equals(isUpdate)) {
			Optional<Method> oMethod = methodRepository.findById(ruleDTO.getMethod().getId());
			Optional<Access> oAccess = accessRepository.findById(ruleDTO.getAccess().getId());
			
			if (oMethod.isPresent() && oAccess.isPresent()) {
				Rule r = ruleRepository.findRule(ruleDTO.getPath(), oMethod.get(), oAccess.get());
			
				if (!Objects.isNull(r)) {
					throw new IncorrectRuleException("Ya existe una regla que cumple las condiciones de ruta, método y acceso");
				}
			}
		}
		
		String nameAccess = ruleDTO.getAccess().getName();
		
		List<Access> accesses = accessRepository.findAll();
		Optional<Access> fAccess = accesses.stream().filter(access -> access.getName().equals(nameAccess)).findFirst();
		
		String sMessage = String.format("Acceso incorrecto (elegir entre %s)", accesses.toString());
		RuleManagerHelper.failWhenTrue(fAccess.isEmpty(), sMessage);
		RuleManagerHelper.failWhenTrue("ROLE".equals(nameAccess) && Objects.isNull(ruleDTO.getRole()), "El rol debe estar informado");
		RuleManagerHelper.failWhenTrue("ROLE".equals(nameAccess) && CollectionUtils.isNotEmpty(ruleDTO.getRoles()), "La lista de roles no debe ser informada");
		RuleManagerHelper.failWhenTrue("ROLES".equals(nameAccess) && CollectionUtils.isEmpty(ruleDTO.getRoles()), "La lista de roles debe ser informada");
		RuleManagerHelper.failWhenTrue("ROLES".equals(nameAccess) && !Objects.isNull(ruleDTO.getRole()), "El rol no debe estar informado");
		RuleManagerHelper.failWhenTrue("ALL".equals(nameAccess) && (!Objects.isNull(ruleDTO.getRole())
				|| CollectionUtils.isNotEmpty(ruleDTO.getRoles())), "Para acceso ALL no deben estar informados los roles");
		RuleManagerHelper.failWhenTrue("AUTHENTICATED".equals(nameAccess) && (!Objects.isNull(ruleDTO.getRole())
				|| CollectionUtils.isNotEmpty(ruleDTO.getRoles())), "Para acceso AUTHENTICATED no deben estar informados los roles");
	}

	private void updateRoles(Rule rule, RuleDTO newDataDTO) {
		List<RoleDTO> rolesDTO = newDataDTO.getRoles();

		if (CollectionUtils.isNotEmpty(rolesDTO)) {
			List<Role> roles = new ArrayList<>();

			for (RoleDTO role : rolesDTO) {
				Optional<Role> oRole = roleRepository.findById(role.getId());

                oRole.ifPresent(roles::add);
			}

			rule.setRoles(roles);
			rule.setRole(null);
		}

		RoleDTO roleDTO = newDataDTO.getRole();
		if (!Objects.isNull(roleDTO)) {
			Optional<Role> oRole = roleRepository.findById(roleDTO.getId());

            oRole.ifPresent(rule::setRole);

			if (CollectionUtils.isNotEmpty(rule.getRoles())) {
				rule.getRoles().clear();
			}
		} else {
			rule.setRole(null);
		}
	}

}
