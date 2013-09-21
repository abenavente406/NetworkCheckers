package com.rcode.checkers;
import java.awt.Color;
import java.awt.Graphics2D;


public class BoardSquare {

	/** Color of the dark squares */
	public static final Color DARK_COLOR  = new Color(0xBD9746);
	
	/** Color of the light squares */
	public static final Color LIGHT_COLOR = new Color(0xF0D66E);

	/** The square has no piece in it */
	public static final int EMPTY = 0;
	
	/** The square has a white piece */
	public static final int WHITE_PIECE = 1;
	
	/** The square has a white king */
	public static final int WHITE_KING = 2;
	
	/** The square has a black piece */
	public static final int BLACK_PIECE = 3;
	
	/** The square has a black king */
	public static final int BLACK_KING = 4;
	
	/** Status of the board [0-4] */
	private int status = 0;
	
	/** Width of the square (in pixels) */
	private int width;
	/** Height of the square (in pixels) */
	private int height;
	/** Color of the square */
	private Color color;
	/** X position of the square */
	private int x;
	/** Y position of the square */
	private int y;
	
	/** Whether the square is selected or not
	 * @unused
	 */
	private boolean selected = false;
	
	/** The color of the piece when it is selected
	 * @unused
	 */
	private Color selectedColor;
	
	/**
	 * Creates a tile on a checker board
	 * @param parent : the checker board
	 * @param x : the x position of the square
	 * @param y : the y position of the square
	 */
	public BoardSquare(Board parent, int x, int y) {
		this.width = parent.getTileWidth();
		this.height = parent.getTileHeight();
		this.x = x;
		this.y = y;
		
		// Determines what color the square will be
		color = y % 2 == 0 ? x % 2 == 0 ? LIGHT_COLOR : DARK_COLOR 
						   : x % 2 == 0 ? DARK_COLOR  : LIGHT_COLOR;
		
		selectedColor = new Color(0x9CBA23);
	}
	
	/**
	 * Draw the board square
	 * @param g - 2D graphics object
	 */
	public void paint(Graphics2D g) {
		
		// Set the color of the square to it's designated color
		g.setColor(selected ? selectedColor : color);
		
		// Fill the square
		g.fillRect(x * width, y * height, width, height);
	}
	
	/**
	 * Gets the color of the square
	 * @return the color of the square
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Toggles whether the squre is selected or not
	 * @param value : true or false
	 */
	public void setSelected(boolean value) {
		selected = value;
	}
	
	/**
	 * Gets the status of the board square
	 * @return the status of the square
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Set the status of the game board
	 * @param status : the new status of the square [0-4]
	 */
	public void setStatus(int status) {
		this.status = status <= 4 ? status : 0;
	}
}
