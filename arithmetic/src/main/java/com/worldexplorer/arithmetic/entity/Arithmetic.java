package com.worldexplorer.arithmetic.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

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
//Generates implementations for the {@code equals} and {@code hashCode} methods inherited by all objects, based on relevant fields.
@EqualsAndHashCode
public final class Arithmetic {

	private int factorA;
	private  int factorB;
	private String operator;
	private  int result;
	public Arithmetic() {
	}
	public Arithmetic(int factorA, int factorB, String operator) {
		this.operator = operator;
		this.factorA = factorA;
		this.factorB = factorB;
		switch (operator) {
		case "+": {
			this.result = factorA + factorB;
			break;
		}
		case "-": {
			this.result = factorA - factorB;
			break;
		}
		case "×": {
			this.result = factorA * factorB;
			break;
		}
		case "÷": {
			this.result = factorA / factorB;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operator);
		}
	}
	
	@Override
	public String toString() {
		return "Multiplication [factorA=" + factorA + ", factorB=" + factorB + ", result=" + result + "]";
	}
	
	
	
}
