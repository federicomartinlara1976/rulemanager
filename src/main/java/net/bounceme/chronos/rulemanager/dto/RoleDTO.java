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
public class RoleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 584383174648149427L;
	
	private Long id;

	private String nombre;
	
	private String nombreCorto;
}
