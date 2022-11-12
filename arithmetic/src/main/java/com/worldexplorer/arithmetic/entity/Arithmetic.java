package com.worldexplorer.arithmetic.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Alt+Shift+S 
 * for getters and setters generation
 * 
 * Alt+Shift+R
 * Place your cursor on the field name then
 * press Alt+Shift+R to rename not only the field name
 * but also its corresponding getters 
 * and setters.
 * 
 * The other way of generating getters and setters:
 * Right click the mouse button, choose "Source/Generate ..."
 * 
 * @author tanku
 *
 */
@Getter
@Setter
@Document("arithmeticUsers")
public final class Arithmetic {

	private String id;
	private int factorA;
	private  int factorB;
	private String operator;
	public Arithmetic() {
	}
	public Arithmetic(int factorA, int factorB, String operator) {
		this.operator = operator;
		this.factorA = factorA;
		this.factorB = factorB;
		
	}
	
	@Override
	public String toString() {
		return "arithmetic [factorA=" + factorA + ", operator=" +  operator + ", factorB=" + factorB  + "]";
	}
	
	public boolean isCorrect(int attemptResult) {
		switch (operator) {
		case "+": {
			if(attemptResult == factorA + factorB) {
				return true;
			}
			else {
				return false;
			}
		}
		case "-": {
			if(attemptResult == factorA - factorB) {
				return true;
			}
			else {
				return false;
			}
		}
		case "×": {
			if(attemptResult == factorA * factorB) {
				return true;
			}
			else {
				return false;
			}
		}
		case "÷": {
			if(attemptResult == factorA / factorB) {
				return true;
			}
			else {
				return false;
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operator);
		}
	}
	
}
