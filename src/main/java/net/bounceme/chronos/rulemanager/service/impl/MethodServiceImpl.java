package net.bounceme.chronos.rulemanager.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.MethodDTO;
import net.bounceme.chronos.app.usuarios.commons.repository.MethodRepository;
import net.bounceme.chronos.rulemanager.service.MethodService;

@Service
@Slf4j
public class MethodServiceImpl implements MethodService {

	private MethodRepository methodRepository;
	
	private ModelMapper modelMapper;
	
	public MethodServiceImpl(MethodRepository methodRepository, ModelMapper modelMapper) {
		this.methodRepository = methodRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MethodDTO> listAll() {
		log.info("get methods");
		
		return methodRepository.findAll().stream()
				.map(method -> modelMapper.map(method, MethodDTO.class)).toList();
	}
}
