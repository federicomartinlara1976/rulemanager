package net.bounceme.chronos.rulemanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.app.usuarios.commons.dto.AccessDTO;
import net.bounceme.chronos.rulemanager.service.AccessService;

@RestController
@RequestMapping("/rulemanager/accesses")
public class AccessController {
	
	@Autowired
	private AccessService accessService;

	@GetMapping("")
	public ResponseEntity<Map<String, Object>> list() {
		Map<String, Object> response = new HashMap<>();
		
		List<AccessDTO> lstAccess = accessService.listAll();
		
		response.put("accesses", lstAccess);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
