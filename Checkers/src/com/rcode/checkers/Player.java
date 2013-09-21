package com.rcode.checkers;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public abstract class Player {

	/** The name of the player */
	private String name;
	
	/** The faction of the player */
	private Faction faction;
	
	/** The pieces owned by the player */
	protected List<Piece> pieces = new ArrayList<Piece>();

	/**
	 * Creates a player
	 * @param name : name of the player
	 * @param faction : faction of the player
	 */
	public Player(String name, Faction faction) {
		this.name = name;
		this.faction = faction;
		
		pieces = new ArrayList<Piece>();
		
		if (faction == Faction.BLACK) {
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < Game.board.getWidthInTiles(); x++) {
					if (Game.board.getSquare(x, y).getColor() == BoardSquare.DARK_COLOR) {
						pieces.add(new Piece(this, x, y));
						Game.board.getSquare(x, y).setStatus(BoardSquare.BLACK_PIECE);
					}
				}
			}
		} else {
			for (int y = 5; y < Game.board.getHeightInTiles(); y++) {
				for (int x = 0; x < Game.board.getWidthInTiles(); x++) {
					if (Game.board.getSquare(x, y).getColor() == BoardSquare.DARK_COLOR) {
						pieces.add(new Piece(this, x, y));
						Game.board.getSquare(x, y).setStatus(BoardSquare.WHITE_PIECE);
					}
				}
			}
		}
	}
	
	/**
	 * Updates the player
	 */
	public abstract void update();
	
	/**
	 * Moves a player piece
	 * @param from: piece to move
	 * @param to: location to move to
	 */
	public boolean movePiece(Point from, Point to) {

		boolean result = true;

		if (getFaction() == Faction.WHITE) {
			result &= getPiece(from.x, from.y).getIsKing() ? to.y != from.y : to.y < from.y;
		} else {
			result &= getPiece(from.x, from.y).getIsKing() ? to.y != from.y : to.y > from.y;
		}
		
		result &= Game.board.getSquare(to.x - 1, to.y - 1).getColor() == BoardSquare.DARK_COLOR;
		result &= Game.board.getSquare(to.x - 1, to.y - 1).getStatus() == BoardSquare.EMPTY;
		
		if (result) {
			for (Piece t : pieces) {
				if (t.getX() == from.x - 1&& t.getY() == from.y - 1){
					t.setX(to.x - 1);
					t.setY(to.y - 1);
					Game.board.getSquare(to.x - 1, to.y - 1).setStatus(getFaction() == Faction.BLACK ? 
							BoardSquare.BLACK_PIECE : BoardSquare.WHITE_PIECE);
					Game.board.getSquare(from.x - 1, from.y - 1).setStatus(BoardSquare.EMPTY);
					
					checkForJumps(from, to);
				}
			}
		}
		
		return result;
	}

	/**
	 * Checks if the player jumped over a piece by 
	 * averaging the original and new locations of 
	 * the piece
	 * @param from : the original location of the piece
	 * @param to : the new location of the piece
	 */
	private void checkForJumps(Point from, Point to) {
		
		Point hoppedPoint = new Point(((from.x + to.x) / 2) - 1, ((from.y + to.y) / 2) - 1);
		
		int hoppedStatus = Game.board.getSquare(hoppedPoint.x, hoppedPoint.y).getStatus();
		
		if (hoppedStatus != BoardSquare.EMPTY) {
			
			// -------------------------------------------------
			// Run this code if a piece was detected in the jump
			// -------------------------------------------------
			if (getFaction() == Faction.BLACK) {
				for (Piece t : PlayerManager.player2.pieces) {
					if (t.getX() == hoppedPoint.x && t.getY() == hoppedPoint.y) {
						PlayerManager.player2.pieces.remove(t);
						Game.board.getSquare(hoppedPoint.x, hoppedPoint.y).setStatus(BoardSquare.EMPTY);
						break;
					}
				}
			} else {
				for (Piece t : PlayerManager.player1.pieces) {
					if (t.getX() == hoppedPoint.x && t.getY() == hoppedPoint.y) {
						PlayerManager.player1.pieces.remove(t);
						Game.board.getSquare(hoppedPoint.x, hoppedPoint.y).setStatus(BoardSquare.EMPTY);
						break;
					}
				}
			}
			
		}
	}
	
	/**
	 * Paints the player's pieces
	 * @param g : the Grahpics2D object used for rendering
	 */
	public void paint(Graphics2D g) {
		for (Piece t : pieces) {
			t.paint(g);
		}
	}
	
	/**
	 * Gets the name of the player
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the faction of the player
	 * @return either BLACK or WHITE
	 */
	public Faction getFaction() {
		return faction;
	}
	
	/**
	 * Gets the pieces of the player
	 * @return all the piece owned by the player
	 */
	public List<Piece> getPieces() {
		return pieces;
	}
	
	/**
	 * Gets a Piece at a point
	 * @param x : x coordinate of the piece
	 * @param y : y coordinate of the piece
	 * @return the piece at the given point
	 */
	public Piece getPiece(int x, int y) {
		for (Piece t : pieces) {
			if (t.getX() == x - 1 && t.getY() == y - 1) {
				return t;
			}
		}
		
		return null;
	}
	
}
