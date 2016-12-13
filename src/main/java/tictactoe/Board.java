package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Board implements Comparable<Board> {
	
	Logger LOGGER = Logger.getLogger("board");

	private static final char BLANK_VALUE = ' ';
	char[][] positions;
	List<Board> possibleMoves;
	final Player player;
	int outcome = -3;

	public Board(char[][] currentState, Player player) {
		positions = currentState;
		this.player = player;
	}

	char[][] deepCopy(char[][] currentState) {
		return java.util.Arrays.stream(currentState).map(el -> el.clone()).toArray($ -> currentState.clone());
	}

	public Board getResponse() {
		findPossibleMoves();
		return player.bestPlay(possibleMoves);
	}

	int determineOutcome() {
		if (outcome == -3) {
			outcome = getDecision();
		}
		if (outcome == -2) {
			outcome = getResponse().determineOutcome();
			return outcome;
		}
		return outcome;
	}

	private boolean isDraw() {
		for (char[] row : positions) {
			for (char c : row) {
				if (c == ' ' || c == '+')
					return false;
			}
		}
		return true;
	}

	private char findVictor(char winner) {
		for (int i = 0; i < 3; i++) {
			boolean rowWin = positions[i][0] == positions[i][1] && positions[i][0] == positions[i][2];
			boolean columnWin = positions[0][i] == positions[1][i] && positions[0][i] == positions[2][i];
			if (positions[i][0] != ' ' && rowWin) {
				System.out.println("row " + i + " win");
				char winQualifyingPlayer = positions[i][0];
				winner = win(winner, winQualifyingPlayer);
			}
			if (positions[0][i] != ' ' && columnWin) {
				System.out.println("column " + i + " win");
				char winQualifyingPlayer = positions[0][i];
				winner = win(winner, winQualifyingPlayer);
			}
		}
		boolean descendingDiagonalWin = positions[0][0] == positions[1][1] && positions[0][0] == positions[2][2];
		boolean ascendingDiagonalWin = positions[2][0] == positions[1][1] && positions[2][0] == positions[0][2];
		if (positions[0][0] != ' ' && descendingDiagonalWin) {
			System.out.println("desc diagonal win");
			winner = win(winner, positions[1][1]);
		}
		if (positions[2][0] != ' ' && ascendingDiagonalWin) {
			System.out.println("asc diagonal win");
			winner = win(winner, positions[1][1]);
		}
		return winner;
	}

	private char win(char winner, char winQualifyingPlayer) {
		if (winner == winQualifyingPlayer || winner == BLANK_VALUE) {
			winner = winQualifyingPlayer;
		} else
			throw new IllegalArgumentException("board invalid--had two winners.");
		return winner;
	}



	public void findPossibleMoves() {
		possibleMoves = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (positions[i][j] == BLANK_VALUE) {
					char[][] newPositions = deepCopy(positions);
					newPositions[i][j] = player.representation;
					possibleMoves.add(new Board(newPositions, player.getOpposingPlayer()));
				}
			}
		}
	}

	public int getDecision() {
		char winner = BLANK_VALUE;
		winner = findVictor(winner);
		if (winner == BLANK_VALUE) {
			if (isDraw()) {
				outcome = 0;
			} else {
				outcome = -2;
			}
		} else {
			outcome = winner == 'o' ? 1 : -1;
		}
		return outcome;
	}
	

	public int compareTo(Board b) {
		System.out.println("Comparing " + this + " to " + b);
		if (outcome <= -2)
			determineOutcome();
		if (b.outcome <= -2)
			b.determineOutcome();
		return outcome - b.outcome;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Board)) return false;
		Board otherBoard = (Board) o;
		if (player != otherBoard.player) return false;
		
		return Arrays.deepEquals(positions, otherBoard.positions);
	}
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (char[] row : positions) {
			for (char c : row) {
				b.append(c);
			}
		}
		return b.toString();
	}
	
	public void prettyPrint() {
		System.out.println("DECISION:" + getDecision());
		System.out.println("------");
		for (char[] row : positions) {
			System.out.println(row[0] + "|" + row[1] + "|" + row[2]);
			System.out.println("------");
		}
	}
	

}
