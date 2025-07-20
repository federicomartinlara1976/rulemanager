package net.bounceme.chronos.rulemanager.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.RoleDTO;
import net.bounceme.chronos.app.usuarios.commons.repository.RoleRepository;
import net.bounceme.chronos.rulemanager.service.RoleService;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;
	
	private ModelMapper modelMapper;
	
	public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleDTO> listAll() {
		log.info("get roles");
		
		return roleRepository.findAll().stream()
				.map(role -> modelMapper.map(role, RoleDTO.class)).toList();
	}
}
