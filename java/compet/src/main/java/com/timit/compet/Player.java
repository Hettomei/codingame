package com.timit.compet;
import java.util.*;
import java.io.*;
import java.math.*;

// Tools
class T {
	// debug
	static void d(Object o) {
		System.err.println("" + o);
	}
}

enum Level {
	VIDE,
	MUR,
	ENERGIE,
	TETE,
	CORPS,
}

class Board {

	Level[][] level;

	Board(Level[][] level) {
		this.level = level;
	}

	static Board build(Scanner in) {
		int width = in.nextInt();
		int height = in.nextInt();
		Level[][] l = new Level[height][width];
		in.nextLine(); // utile pour passer à la suite
		for (int row = 0; row < height; row++) {
			int j;
			String rawLine = in.nextLine();
			T.d(rawLine);
			String[] line = rawLine.split("");
			int col=0;
			for (String s : line) {
				if (s.equals(".")) {
					l[row][col] = Level.VIDE;
				} else if (s.equals("#")) {
					l[row][col] = Level.MUR;
				}
				col++;
			}
		}
		return new Board(l);
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < level.length; row++) {
			for (int col = 0; col < level[row].length; col++) {
				sb.append(col);
				sb.append(",");
				sb.append(row);
				sb.append(level[row][col]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

class Snake {
	int id;
	Snake(int id) {
		this.id = id;
	}
	public String toString() {
		return "Snake:" + id;
	}

	static Snake[] builds(int n, Scanner in) {
		Snake[] snakes = new Snake[n];
		for (int i = 0; i < n; i++) {
			snakes[i] = new Snake(in.nextInt());
		}
		return snakes;
	}
}

class PowerUp {
	int x;
	int y;
	PowerUp(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public String toString() {
		return "Power:" + x + "," + y;
	}

	static PowerUp[] builds(Scanner in) {
		int powerSourceCount = in.nextInt();
		PowerUp[] powerups = new PowerUp[powerSourceCount];
		for (int i = 0; i < powerSourceCount; i++) {
			powerups[i] = new PowerUp(in.nextInt(), in.nextInt());
		}
		return powerups;
	}
}

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int myId = in.nextInt();
		Board board = Board.build(in);
		T.d(board);

		int snakePerPlayer = in.nextInt();
		Snake[] mySnakes;
		Snake[] opponentSnakes;

		if (myId == 0) {
			mySnakes = Snake.builds(snakePerPlayer, in);
			opponentSnakes = Snake.builds(snakePerPlayer, in);
		} else {
			opponentSnakes = Snake.builds(snakePerPlayer, in);
			mySnakes = Snake.builds(snakePerPlayer, in);
		}

		for (Snake a : mySnakes) {
			T.d(a);
		}

		int loop = 0;
		while (loop<300) {
			PowerUp[] powerups = PowerUp.builds(in);
			int snakebotCount = in.nextInt();
			for (int i = 0; i < snakebotCount; i++) {
				int snakebotId = in.nextInt();
				String body = in.next();
				T.d("snake: " + snakebotId + ", " + body);
			}

			System.out.println("WAIT");
			loop++;
		}
		in.close();
	}
}
