package net.bounceme.chronos.rulemanager.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.MethodDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Method;
import net.bounceme.chronos.rulemanager.repository.MethodRepository;
import net.bounceme.chronos.rulemanager.service.MethodService;

@Service
@Slf4j
public class MethodServiceImpl implements MethodService {

	@Autowired
	private MethodRepository methodRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	@Cacheable("methods")
	public List<MethodDTO> listAll() {
		log.info("get methods");
		
		List<Method> lstMethods = methodRepository.findAll();
		return CollectionUtils.isNotEmpty(lstMethods) ? lstMethods.stream()
				.map(method -> modelMapper.map(method, MethodDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}
}
