package net.bounceme.chronos.rulemanager.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9081753587919646618L;

	private Long id;

	private String name;
}
