import java.util.*;
import java.io.*;
import java.math.*;

class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int myId = in.nextInt();
		int width = in.nextInt();
		int height = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		for (int i = 0; i < height; i++) {
			String row = in.nextLine();
		}
		int snakebotsPerPlayer = in.nextInt();
		for (int i = 0; i < snakebotsPerPlayer; i++) {
			int mySnakebotId = in.nextInt();
		}
		for (int i = 0; i < snakebotsPerPlayer; i++) {
			int oppSnakebotId = in.nextInt();
		}

		// game loop
		while (true) {
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

			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			System.out.println("WAIT");
		}
	}
}
