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
import net.bounceme.chronos.app.usuarios.commons.dto.AccessDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Access;
import net.bounceme.chronos.rulemanager.repository.AccessRepository;
import net.bounceme.chronos.rulemanager.service.AccessService;

@Service
@Slf4j
public class AccessServiceImpl implements AccessService {

	@Autowired
	private AccessRepository accessRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	@Cacheable("accesses")
	public List<AccessDTO> listAll() {
		log.info("get accesses");
		
		List<Access> lstAccess = accessRepository.findAll();
		return CollectionUtils.isNotEmpty(lstAccess) ? lstAccess.stream()
				.map(access -> modelMapper.map(access, AccessDTO.class)).toList()
				: Collections.emptyList();
	}
}
