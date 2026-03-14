package com.timit.compet;
import java.util.*;
import java.io.*;
import java.math.*;

// Tools
class T {
	// debug
	public static void d(Object o) {
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

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int myId = in.nextInt();
		Board board = Board.build(in);
		T.d(board);

		int snakebotsPerPlayer = in.nextInt();
		for (int i = 0; i < snakebotsPerPlayer; i++) {
			int mySnakebotId = in.nextInt();
		}
		for (int i = 0; i < snakebotsPerPlayer; i++) {
			int oppSnakebotId = in.nextInt();
		}

		int loop = 0;
		while (loop<300) {
			int powerSourceCount = in.nextInt();
			for (int i = 0; i < powerSourceCount; i++) {
				int x = in.nextInt();
				int y = in.nextInt();
			}
			int snakebotCount = in.nextInt();
			for (int i = 0; i < snakebotCount; i++) {
				int snakebotId = in.nextInt();
				String body = in.next();
			}

			System.out.println("WAIT");
			loop++;
		}
		in.close();
	}
}
