package com.worldexplorer.arithmetic.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Document("arithmeticUsers")
public final class User {
	private String id;
	@NonNull
	private String alias;
	
}
