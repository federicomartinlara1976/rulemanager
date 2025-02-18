package net.bounceme.chronos.rulemanager.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3523810111805421848L;

	private Long id;

	private String name;
}
