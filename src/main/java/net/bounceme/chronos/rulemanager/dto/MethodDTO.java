package net.bounceme.chronos.rulemanager.dto;

import java.io.Serializable;

import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoName;

@ProtoName("MethodDTO")
public class MethodDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3523810111805421848L;

	private Long id;

	private String name;

	public MethodDTO() {
		super();
	}

	public MethodDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@ProtoField(number = 1, required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ProtoField(number = 2, required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
