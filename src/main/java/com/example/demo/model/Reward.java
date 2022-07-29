package com.example.demo.model;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Reward {
  
	// including Jackson serialization
	@JsonInclude  
	@Transient    
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	protected Long points;

	public abstract Long getPoints();

}



