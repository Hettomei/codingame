package com.timit.compet;

public class Main {
	private static int n = 0;

	public static void debug(Object... o) {
		n++;
		System.err.print("" + n + ": ");
		for (Object i : o) {
			System.err.print(i + " "); 
		}
		System.err.println();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			debug(in.nextLine());
		}
		System.out.println("WAIT");
	}
}
