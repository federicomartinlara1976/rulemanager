package net.bounceme.chronos.rulemanager.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.RoleDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Role;
import net.bounceme.chronos.rulemanager.repository.RoleRepository;
import net.bounceme.chronos.rulemanager.service.RoleService;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	@Cacheable("roles")
	public List<RoleDTO> listAll() {
		log.info("get roles");
		
		List<Role> roles = roleRepository.findAll();
		return CollectionUtils.isNotEmpty(roles) ? roles.stream()
				.map(role -> modelMapper.map(role, RoleDTO.class)).toList()
				: Collections.emptyList();
	}
}
