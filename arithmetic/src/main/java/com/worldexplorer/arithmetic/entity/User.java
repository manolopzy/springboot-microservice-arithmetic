package com.worldexplorer.arithmetic.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@NoArgsConstructor
@Data
@Document("arithmeticUsers")
public final class User {
	private String id;
	@NonNull
	private String alias;
	
}
