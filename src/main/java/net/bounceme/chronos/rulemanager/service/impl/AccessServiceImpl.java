package net.bounceme.chronos.rulemanager.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.AccessDTO;
import net.bounceme.chronos.app.usuarios.commons.repository.AccessRepository;
import net.bounceme.chronos.rulemanager.service.AccessService;

@Service
@Slf4j
public class AccessServiceImpl implements AccessService {

	private AccessRepository accessRepository;
	
	private ModelMapper modelMapper;
	
	public AccessServiceImpl(AccessRepository accessRepository, ModelMapper modelMapper) {
		this.accessRepository = accessRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessDTO> listAll() {
		log.info("get accesses");
		
		return accessRepository.findAll().stream()
				.map(access -> modelMapper.map(access, AccessDTO.class)).toList();
	}
}
