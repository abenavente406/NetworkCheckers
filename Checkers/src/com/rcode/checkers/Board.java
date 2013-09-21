package com.rcode.checkers;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

public class Board {
	
	/** Width of each board square */
	private int tileWidth = 50;
	/** Height of each board square */
	private int tileHeight = 50;
	/** Width of the board in tiles */
	private int widthInTiles;
	/** Height of the board in tiles */
	private int heightInTiles;
	
	/** The checker board stored in a 2D array*/
	private BoardSquare[][] grid;
	
	/** The currently selected square x
	 *  @unused */
	private int selectedSquareX = 0;
	/** The currently selected square y
	 *  @unused */
	private int selectedSquareY = 0;

	/** Main font the graphics object uses for rendering strings */
	private Font boardFont;

	/**
	 * Creates a board by specifying the dimensions of the board
	 * @param widthInTiles : How many squares wide
	 * @param heightInTiles : How many squares tall
	 */
	public Board(int widthInTiles, int heightInTiles) {
		this.widthInTiles = widthInTiles;
		this.heightInTiles = heightInTiles;
		grid = new BoardSquare[heightInTiles][widthInTiles];

		for (int y = 0; y < heightInTiles; y++) {
			for (int x = 0; x < widthInTiles; x++) {
				grid[y][x] = new BoardSquare(this, x, y);
			}
		}

		boardFont = new Font("Arial", Font.BOLD, 14);
	}

	/**
	 * Paints the board
	 * 
	 * @param g 
	 * 		  : Graphics2D object used to render things to the screen
	 */
	public void paint(Graphics2D g) {

		// Store the original font of the graphics object
		Font normalFont = g.getFont();

		for (int y = 0; y < heightInTiles; y++) {
			for (int x = 0; x < widthInTiles; x++) {

				// Draw each square using the BoardSquare's "paint" method
				grid[y][x].paint(g);

				// Temporarily change the font of the board =
				g.setFont(boardFont);

				g.setColor(Color.black);

				// Only draw the numbers if the loop is on its last iteration
				// to avoid creating a new loop for drawing the numbers
				if (y == heightInTiles - 1) {

					// Draw the top numbers above the board
					g.drawString("" + (x + 1),
							(x * tileWidth) + (tileWidth / 2)
									- (g.getFontMetrics().charWidth('O') / 2),
							y - 20);

					// Rotate the matrix to make the numbers rotate --> 90
					// degrees
					g.rotate(Math.PI / 2);

					// Draw the numbers similarly as we did with the top numbers
					// because
					// the rotation will adjust it's y and x values
					g.drawString("" + (x + 1),
							(x * tileWidth) + (tileWidth / 2)
									- (g.getFontMetrics().charWidth('O') / 2),
							y + 20);

					// Rotate the matrix back to normal --> -90 degrees
					g.rotate(-Math.PI / 2);
				}
			}

		}

		// Set the stroke width to be 3
		g.setStroke(new BasicStroke(3f));

		g.setColor(Color.black);

		// Draws the border of the game board
		g.drawLine(-2, -2, 401, -2);
		g.drawLine(-2, -2, -2, 401);
		g.drawLine(-2, 401, 401, 401);
		g.drawLine(401, -2, 401, 401);

		// Set the font back to normal
		g.setFont(normalFont);
	}

	/**
	 * Gets the width of the squares on the board
	 * 
	 * @return the width of each square on the board (in pixels)
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * Gets the height of the squares on the board
	 * 
	 * @return the height of each square on the board (in pixels)
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * Gets the width of the board in tiles
	 * 
	 * @return the width of the board in squares
	 */
	public int getWidthInTiles() {
		return widthInTiles;
	}

	/**
	 * Gets the height of the board in tiles
	 * 
	 * @return the height of the board in squares
	 */
	public int getHeightInTiles() {
		return heightInTiles;
	}

	/**
	 * Gets the width of the board in pixels
	 * 
	 * @return the widh of the board in pixels
	 */
	public int getWidthInPixels() {
		return widthInTiles * tileWidth;
	}

	/**
	 * Gets the height of the board in pixels
	 * 
	 * @return the height of the board in pixels
	 */
	public int getHeightInPixels() {
		return heightInTiles * tileHeight;
	}

	/**
	 * Gets the square at a given coordinate
	 * 
	 * @param x : the x position of the square
	 * @param y : the y position of the square
	 * @return the square at the given coordinate
	 */
	public BoardSquare getSquare(int x, int y) {
		if (x < 0 || x >= widthInTiles || y < 0 || y >= heightInTiles) {
			return null;
		}

		return grid[y][x];
	}
	
	/**
	 * Selects a square on the board
	 * @param x : x position of the square
	 * @param y : y position of the square
	 * @unused
	 */
	public void selectSquare(int x, int y) {
		getSquare(selectedSquareX, selectedSquareY).setSelected(false);
		
		selectedSquareX = x;
		selectedSquareY = y;
		
		getSquare(selectedSquareX, selectedSquareY).setSelected(true);
	}

	/**
	 * Returns the point converted to tile
	 * 
	 * @param point : the point to convert to a tile
	 * @return an array containing the converted point int[0] = x, int[1] = y
	 */
	public int[] pointToTile(int[] point) {
		return new int[] {(point[0] - Game.getWindows()[0].getLocation().x - 35) / tileWidth,
				(point[1] - Game.BOARD_START_Y - 35) / tileHeight };
	}

	/**
	 * Gets the mouse location translated
	 * 
	 * @return an array containing the converted point int[0] = x, int[1] = y
	 */
	public int[] getTranslatedMousePoint() {
		return pointToTile(new int[] {
				(int) MouseInfo.getPointerInfo().getLocation().getX(),
				(int) MouseInfo.getPointerInfo().getLocation().getY() });
	}
}
