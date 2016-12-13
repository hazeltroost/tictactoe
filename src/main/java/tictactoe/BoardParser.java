package tictactoe;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class BoardParser {

	
	public static Board parse(String input) {
		performLengthAndNumberValidations(input);
		char[][] board = new char[3][3];
		List<Character> allowedChars = Arrays.asList(new Character[]{'x', 'o', ' '});
		char[] inputChars = input.toCharArray();
		for (int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				char c = inputChars[i*3 + j];
				if (!allowedChars.contains(c)) {
					throw new IllegalArgumentException();
				}
				board[i][j] = c;
			}
		}
		return new Board(board, Player.PLAYER_O);
	}

	private static void performLengthAndNumberValidations(String input) {
		if (input.length() != 9) {
			throw new IllegalArgumentException("Incorrect length");
		}
		int numOs = StringUtils.countMatches(input, "o");
		int numXs = StringUtils.countMatches(input, "x");
		int difference = numXs - numOs;
		if (difference != 1 && difference != 0) throw new IllegalArgumentException("turns were not taken sequentially");
	}
}
