package net.bounceme.chronos.rulemanager.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;
import net.bounceme.chronos.rulemanager.domain.UpdateRules;
import net.bounceme.chronos.rulemanager.service.RuleService;
import net.bounceme.chronos.rulemanager.support.RuleManagerHelper;

@RestController
@RequestMapping("/rulemanager/rules")
@Slf4j
public class RuleController {
	
	private static final String TOTAL = "total";

	private static final String RULE = "rule";

	private static final String MENSAJE = "mensaje";

	private static final String RULES = "rules";

	@Autowired
	private RuleService ruleService;

	@Autowired
	private UpdateRules updateRules;

	@GetMapping("")
	public ResponseEntity<Map<String, Object>> list() {
		Map<String, Object> response = new HashMap<>();

		List<RuleDTO> rules = ruleService.listAll();

		response.put(RULES, rules);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/countLast")
	public ResponseEntity<Map<String, Object>> countLast() {
		Map<String, Object> response = new HashMap<>();

		LocalDateTime to = LocalDateTime.now();
		LocalDateTime from = to.minusDays(1);

		List<RuleDTO> rules = ruleService.listByDate(RuleManagerHelper.asDate(from), RuleManagerHelper.asDate(to));

		response.put(TOTAL, rules.size());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/byMethod")
	public ResponseEntity<Map<String, Object>> listByMethod(@RequestParam("method") String method) {
		Map<String, Object> response = new HashMap<>();

		List<RuleDTO> rules = ruleService.listByMethod(method);

		response.put(RULES, rules);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/byAccess")
	public ResponseEntity<Map<String, Object>> listByAccess(@RequestParam("access") String access) {
		Map<String, Object> response = new HashMap<>();

		List<RuleDTO> rules = ruleService.listByAccess(access);

		response.put(RULES, rules);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{idRule}")
	public ResponseEntity<Map<String, Object>> getRule(@PathVariable Long idRule) {
		Map<String, Object> response = new HashMap<>();

		RuleDTO ruleDTO = ruleService.getRule(idRule);

		if (Objects.isNull(ruleDTO)) {
			response.put(MENSAJE, String.format("La regla con ID %d no existe", idRule));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		response.put(RULE, ruleDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> addRule(@RequestBody RuleDTO ruleDTO) {
		Map<String, Object> response = new HashMap<>();

		RuleDTO addedRule = updateRules.insertRule(ruleDTO);

		response.put(MENSAJE, "Regla creada");
		response.put(RULE, addedRule);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{idRule}")
	public ResponseEntity<Map<String, Object>> updateRule(@PathVariable Long idRule, @RequestBody RuleDTO newDataDTO) {
		Map<String, Object> response = new HashMap<>();

		log.info("Update {}, new data: {} ", idRule, newDataDTO);
		RuleDTO updatedRule = updateRules.updateRule(idRule, newDataDTO);

		response.put(MENSAJE, "Regla actualizada");
		response.put(RULE, updatedRule);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{idRule}")
	public ResponseEntity<Map<String, Object>> deleteRule(@PathVariable Long idRule) {
		Map<String, Object> response = new HashMap<>();

		ruleService.deleteRule(idRule);

		response.put(MENSAJE, "Regla borrada");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
