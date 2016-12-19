package tictactoe;

import java.util.Arrays;
import java.util.logging.Logger;

import static tictactoe.PlayState.*;

public class Board implements Comparable<Board> {
	
	Logger LOGGER = Logger.getLogger("board");

	static final char BLANK_SPACE = ' ';
	static final char NO_ONE = BLANK_SPACE;
	char[][] positions;
	final Player player;
	PlayState playState = null; 

	public Board(char[][] currentState, Player player) {
		positions = currentState;
		this.player = player;
	}

	char[][] copyOfMovesOnBoard() {
		return Arrays.stream(positions).map(el -> el.clone()).toArray($ -> positions.clone());
	}

	public Board getPlayersResponse() {
		return player.getResponse(this);
	}

	PlayState determineFinalOutcome() {
		PlayState finalOutcome = playState;
		if (playState == null) { //no win/loss/draw call has been made yet
			playState = findCurrentPlayState();
			finalOutcome = playState;
		}
		if (playState == IN_PLAY) { //call was made, board is in play
			finalOutcome = getPlayersResponse().determineFinalOutcome();
		}
		return finalOutcome;
	}

	private boolean isDraw() {
		for (char[] row : positions) {
			for (char c : row) {
				if (c == BLANK_SPACE)
					return false;
			}
		}
		return true;
	}

	private char findVictor() {
		char winner = NO_ONE;
		for (int i = 0; i < 3; i++) {
			char rowResult = didSomeoneWin(positions[i][0], positions[i][1], positions[i][2]);
			winner = validateWin(winner, rowResult);
		}
		for (int i = 0; i < 3; i++) { 
			char columnResult = didSomeoneWin(positions[0][i], positions[1][i], positions[2][i]);
			winner = validateWin(winner, columnResult);
		}
		char descendingDiagonalResult = didSomeoneWin(positions[0][0], positions[1][1], positions[2][2]);
		winner = validateWin(winner, descendingDiagonalResult);
		
		char ascendingDiagonalResult = didSomeoneWin(positions[2][0], positions[1][1], positions[0][2]);
		winner = validateWin(winner, ascendingDiagonalResult);
		return winner;
	}
	
	private char didSomeoneWin(char first, char second, char third) {
		char victor = NO_ONE;
		if (first == second && first == third) {
			victor = first;
		}
		return victor;
	}

	private char validateWin(char previousWinner, char newWinner) {
		if (newWinner == NO_ONE) { //no one won on this check
			return previousWinner; 
		}
		if (previousWinner == NO_ONE)  { //no one had won previously, but a player won this time
			return newWinner;
		}
		if (previousWinner == newWinner) { //winner had already qualified for a win, and qualified again (this can happen legally)
			return newWinner;
		}
		else {
			throw new IllegalArgumentException("board invalid--had two winners.");
		}
	}
	
	
	public PlayState getCurrentPlayState() {
		if (playState == null) {
			playState = findCurrentPlayState();
		}
		return playState;
	}

	private PlayState findCurrentPlayState() {
		char winner = findVictor();
		PlayState state = null;
		if (winner == NO_ONE) {
			if (isDraw()) {
				state = DRAW;
			} else {
				state = IN_PLAY;
			}
		} else {
			state = Player.getPlayerFor(winner).getResultForO();
		}
		return state;
	}
	

	public int compareTo(Board b) {
		System.out.println("Comparing " + this + " to " + b);
		return getCurrentPlayState().getFinalOutcome(this) - b.getCurrentPlayState().getFinalOutcome(b);
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
		System.out.println("Current play state:" + findCurrentPlayState());
		System.out.println("------");
		for (char[] row : positions) {
			System.out.println(row[0] + "|" + row[1] + "|" + row[2]);
			System.out.println("------");
		}
	}
	

}
