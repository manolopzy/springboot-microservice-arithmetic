package com.worldexplorer.arithmetic.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class User {
	
	private final String alias;
	// Empty constructor for JSON (de)serialization
	public User() {
		alias = null;
	}
}
