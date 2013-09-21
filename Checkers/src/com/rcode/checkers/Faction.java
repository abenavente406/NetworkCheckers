package com.rcode.checkers;
import java.awt.Color;


public enum Faction {
	
	WHITE(Color.white),
	BLACK(Color.darkGray);
	
	/** Color stored for the piece for the faction */
	Color color;
	
	/**
	 * Create a faction based on a color
	 * @param color : color to color the faction's pieces
	 */
	Faction(Color color) {
		this.color = color;
	}
	
	/**
	 * Gets the color corresponding to the faction
	 * @return the color of the faction
	 */
	public Color getColor() {
		return color;
	}
}
