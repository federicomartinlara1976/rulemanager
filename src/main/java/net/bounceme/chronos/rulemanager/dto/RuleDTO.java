package net.bounceme.chronos.rulemanager.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RuleDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1253176492036388728L;
	
	private Long id;
	
	private String path;
	
	private MethodDTO method;
	
	private AccessDTO access;
	
	private Date creationTime;
	
	private Date modifyTime;
	
	private Integer active;
	
	private RoleDTO role;
	
	private List<RoleDTO> roles;
}
