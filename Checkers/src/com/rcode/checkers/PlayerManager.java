package com.rcode.checkers;
import java.awt.Graphics2D;
import java.util.Random;


public class PlayerManager {

	/** Player 1 : can be HumanPlayer or ComputerPlayer */
	public static Player player1;
	/** Player 2 : can be HumanPlayer or ComputerPlayer */
	public static Player player2;
	/** Number of moves in the game - used to determine current turn */
	public static int totalMoves = 1;
	
	/**
	 * Initialize the static fields in the class
	 */
	public PlayerManager() {
		player1 = new HumanPlayer("Player 1", Faction.BLACK);
		player2 = new HumanPlayer("Player 2", Faction.WHITE);
		totalMoves = new Random().nextInt(2) + 1;
	}
	
	/**
	 * Updates the player whose turn it is
	 */
	public static void update() {
		getPlayerReady().update();
	}
	
	/**
	 * Paint's the players
	 * @param g : the graphics object used for rendering
	 */
	public static void paint(Graphics2D g) {
		player1.paint(g);
		player2.paint(g);
	}
	
	/**
	 * Gets the player whose turn it is
	 * @return the player whose turn it is
	 */
	public static Player getPlayerReady() {
		return totalMoves % 2 == 0 ? player2 : player1;
	}

	/**
	 * Gets the winner of the game
	 * @return null if no winner detected, else, return a player
	 */
	public static Player getWinner() {
		return player1.pieces.size() == 0 ? player1 :
			   player2.pieces.size() == 0 ? player2 :
				   null;
	}
}
