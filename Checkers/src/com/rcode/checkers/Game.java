package com.rcode.checkers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame {

	// Default serial version id
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name of the game
	 */
	public static String NAME;
	
	/**
	 * Centralized scanner for the program
	 */
	public static Scanner in;	
	
	/**
	 * Width of the game's window
	 */
	public static final int WINDOW_WIDTH = 600;
	
	/**
	 * Height of the game's window
	 */
	public static final int WINDOW_HEIGHT = 600;
	
	/**
	 * Flag to let the game loop keep updating
	 */
	public static boolean running = false;
	
	/**
	 * Start x location of the game board
	 */
	public static int BOARD_START_X;
	
	/**
	 * Start y location of the game board
	 */
	public static int BOARD_START_Y;
	
	/**
	 * The board of the game
	 */
	public static Board board;
	
	/** 
	 * The players of the game
	 */
	public static PlayerManager playerManager;
	
	/**
	 * A message that displays whose turn it is
	 */
	private String currentPlayerTurn;
	
	/**
	 * Create an instance of the game 
	 * @param name
	 * : name of the game
	 */
	public Game(String name) {
		NAME = name;
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setName(NAME);
		this.setResizable(false);
		this.setTitle(NAME);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		
		// -----------------------------------------------------
		// Add a custom method called when the window is closing
		// -----------------------------------------------------
		this.addWindowListener(new WindowAdapter() {
			/**
			 * Called when the 'close' button is pressed on the window
			 * @param e : Event owner of the event
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showConfirmDialog(e.getComponent(),
						"Are you sure you want to quit?", 
						"Exit Confirmation",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (confirm == 0) {
					System.exit(0);
				}
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		// --------------------------
		// Create an all-use scanner 
		// --------------------------
		in = new Scanner(System.in);
	}
	
	/**
	 * Starts the game
	 */
	public void start() {
		this.setVisible(true);

		// --------------------
		// Initialize the board
		// --------------------
		board = new Board(8, 8);
		
		BOARD_START_X = (Game.WINDOW_WIDTH / 2) - (board.getWidthInPixels() / 2);
		BOARD_START_Y = (Game.WINDOW_HEIGHT / 2) - (board.getHeightInPixels() / 2);
		
		// -------------------------------------------------
		// Initialize the static fields of the PlayerManager
		// -------------------------------------------------
		playerManager = new PlayerManager();
		
		currentPlayerTurn = PlayerManager.getPlayerReady().getName() + "'s turn";
		
		// Set the game to be running
		running = true;
		
		// The game loop
		run();
		
		// After the game loop ends
		unload();
	}
	
	/**
	 * Called before the program exits
	 */
	public void unload() {
		in.close();
	}
	
	/**
	 * Main game loop
	 */
	public void run() {
		while (running) {
			update();
			repaint();
			
			if (PlayerManager.getWinner() != null) {
				System.out.println(PlayerManager.getWinner().getName() + " is the winner!");
				running = false;
			}
		}
	}
	
	/**
	 * Updates all game logic
	 */
	public void update() {
		PlayerManager.update();
	}
	
	/**
	 * Main rendering of the game
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		// Create a Graphics2D object
		Graphics2D g2d = (Graphics2D) g;
		
		// Set the color to white for the background
		g2d.setColor(Color.white);
		// Draw the background
		g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Transform the board to the middle of the screen
		g2d.translate((Game.WINDOW_WIDTH / 2) - (board.getWidthInPixels() / 2), 
				(Game.WINDOW_HEIGHT / 2) - (board.getHeightInPixels() / 2));
		
		// Paint the board
		board.paint(g2d);
		
		// Paint the players
		PlayerManager.paint(g2d);
		
		// Transform the matrix back to its original position
		g2d.translate(-((Game.WINDOW_WIDTH / 2) - (board.getWidthInPixels() / 2)), 
				-((Game.WINDOW_HEIGHT / 2) - (board.getHeightInPixels() / 2)));
		
		// ------------------------------------------------
		// Get the player's turn and faction and display it
		// to the screen
		// ------------------------------------------------
		currentPlayerTurn = PlayerManager.getPlayerReady().getName() + "'s turn";
		currentPlayerTurn += " (" + PlayerManager.getPlayerReady().getFaction().toString() + ")";
		g2d.setFont(new Font("Arial", Font.PLAIN, 18));
		g2d.drawString(currentPlayerTurn, 10, 50);
		
		Graphics2D g2dComponent = (Graphics2D) g;
		g2dComponent.drawImage(image, null, 0, 0);
	}
	
	/**
	 * The entry point to the program
	 * @param args : command line arguments
	 */
	public static void main(String[] args) {
		Game game = new Game("Checkers");
		game.start();
	}
}
