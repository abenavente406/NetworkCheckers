package com.rcode.checkers;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Piece {

	/** Width of the piece */
	private final int WIDTH = 40;
	/** Height of the piece */
	private final int HEIGHT = 40;
	/** Faction of the piece */
	private Faction faction;
	/** Color of the piece */
	private Color color;
	/** X position of the piece */
	private int x;
	/** Y position of the piece */
	private int y;
	/** If the piece is a king piece */
	private boolean isKing = false;

	/**
	 * Creates a piece
	 * @param owner : owner of the piece
	 * @param x : x position of the piece
	 * @param y : y position of the piece
	 */
	public Piece(Player owner, int x, int y) {
		faction = owner.getFaction();
		color = faction.getColor();
		this.x = x;
		this.y = y;
	}

	/**
	 * Renders the piece to the screen
	 * @param g : Grahpics2D object used for rendering
	 */
	public void paint(Graphics2D g) {
		
		// --------------------------------------
		// Fill the piece with its color
		// --------------------------------------
		g.setColor(color);
		g.fillOval(x * Game.board.getTileWidth()
				+ ((Game.board.getTileWidth() - WIDTH) / 2),
				y * Game.board.getTileHeight()
						+ ((Game.board.getTileHeight() - HEIGHT) / 2), WIDTH,
				HEIGHT);

		// --------------------------------------
		// Draw the black stroke around the piece
		// --------------------------------------
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(2.2f));
		g.drawOval(x * Game.board.getTileWidth()
				+ ((Game.board.getTileWidth() - WIDTH) / 2),
				y * Game.board.getTileHeight()
						+ ((Game.board.getTileHeight() - HEIGHT) / 2), WIDTH,
				HEIGHT);

	
		// -------------------------------------
		// If the piece is a king, draw a yellow
		// oval to mark that it is a king
		// -------------------------------------
		if (isKing) {
			g.setColor(Color.yellow);
			g.fillOval(
					x * Game.board.getTileWidth()
							+ ((Game.board.getTileWidth() - 10) / 2),
					y * Game.board.getTileHeight()
							+ ((Game.board.getTileHeight() - 10) / 2), 10, 10);
		}
	}

	/**
	 * Gets the piece's x location
	 * @return the x position of the piece
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x position of the piece
	 * @param newX : the new x position of the piece
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * Gets the piece's y location
	 * @return the y position of the piece
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the piece's y position
	 * @param newY : the new y position of the piece
	 */
	public void setY(int newY) {
		y = newY;

		if (!isKing) {
			isKing = faction == Faction.BLACK ? y == 0 : y == Game.board
					.getHeightInTiles() - 1;
		}
	}

	/**
	 * Gets wheter the piece is a king piece
	 * @return if a piece is a king or not
	 */
	public boolean getIsKing() {
		return isKing;
	}
}
