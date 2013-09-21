package com.rcode.checkers;
import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumanPlayer extends Player {

	/**
	 * Creates a human player
	 * @param name : name of the player
	 * @param faction : faction of the player
	 */
	public HumanPlayer(String name, Faction faction) {
		super(name, faction);
	}

	@Override
	public void update() {
		// Flag that lets the loop keep asking for a 'from' point
		boolean validMove = false;

		// Piece to move
		Point from;

		// Location to move to
		Point to;

		// The player's input
		String response = "";

		// The matched string when player's input is compared to the regex
		String matched = "";

		// The syntax that the player must enter input into
		Pattern regex = Pattern.compile("[1-8][,(\\s)][1-8]");

		// Keep repeating until the player gives valid input
		do {
			System.out.println(getName() + " from : ");

			// -----------------------------------------------
			// Try to match the player's response to the regex
			// -----------------------------------------------
			try {

				// -------------------------------------------
				// Get's the player's piece to move
				// -------------------------------------------
				response = Game.in.nextLine();

				Matcher reMatcher = regex.matcher(response);

				// ------------------------------------------
				// If the player's syntax mactches the regex
				// ------------------------------------------
				if (reMatcher.find()) {
					matched = reMatcher.group();
					from = new Point(
							Integer.parseInt(matched.trim().split(",")[0]),
							Integer.parseInt(matched.trim().split(",")[1]));
				} else {
					// -------------------------------------
					// Check for other commands that the
					// player may have inputted
					// -------------------------------------
					if (response.equals("-h") || response.equals("--help")) {
						printHelp();
					} else if (response.equals("-s")) {
						showAllPieces();
					} else {
						printRegexError();
					}
					continue;
				}

				System.out.println(getName() + " to : ");

				// -------------------------------------------
				// Gets where the player moves the selected
				// piece to
				// -------------------------------------------
				response = Game.in.nextLine();

				reMatcher = regex.matcher(response);

				if (reMatcher.find()) {
					matched = reMatcher.group();
					to = new Point(
							Integer.parseInt(matched.trim().split(",")[0]),
							Integer.parseInt(matched.trim().split(",")[1]));
				} else {
					if (response.equals("-h") || response.equals("--help")) {
						printHelp();
					} else if (response.equals("-s")) {
						showAllMoves(from);
					} else {
						printRegexError();
					}
					continue;
				}

				// -------------------------------------
				// If the piece is successfully moved...
				// (i.e.) if all the tests for moving
				// pass
				// -------------------------------------
				if (movePiece(from, to)) {
					System.out.println("Successfully moved " + from.toString()
							+ " to " + to.toString());
					PlayerManager.totalMoves++;
					validMove = true;
				} else {
					System.out.println("Failed to move " + from.toString()
							+ " to " + to.toString());
				}

			} catch (Exception e) {
				printRegexError();
			}

		} while (!validMove);
	}

	/**
	 * Prints out when an error in the regex occurs
	 */
	private void printRegexError() {
		System.out.println("Error in response.  Follow the syntax:");
		System.out.println("from : \"a, b\"");
		System.out.println("\"a\" - x coordinate of piece to move");
		System.out.println("\"b\" - y coordinate of piece to move");
		System.out.println("to : \"a, b\"");
		System.out.println("\"a\" - x coordinate of new location");
		System.out.println("\"b\" - y coordinate of new location");
		System.out.println("Type \"-h\" or \"--help\" for assistance");
	}

	/**
	 * Prints the list of commands for the user
	 */
	private void printHelp() {
		System.out.println("Commands:");
		System.out.println("\"a,b\" - syntax for coordinate input");
		System.out.println("\"-h\" or \"--help\" - show help");
		System.out
				.println("\"-s\" - show all available pieces to move (if \"from : \")");
		System.out.println("\"-s\" - show all available moves (if \"to : \")");
	}

	/**
	 * Shows all the pieces the player can move
	 */
	private void showAllPieces() {
		for (Piece p : pieces) {
			System.out.println("[" + (p.getX() + 1) + ", " + (p.getY() + 1)
					+ "]");
		}
	}

	/**
	 * Shows all the possible moves the player can make with a specific piece
	 * selected
	 */
	private void showAllMoves(Point from) {

	}
}
