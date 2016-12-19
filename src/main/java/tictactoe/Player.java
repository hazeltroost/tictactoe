package tictactoe;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import static tictactoe.PlayState.*;

public enum Player {
	PLAYER_X('x', list -> Collections.min(list), LOSS),
	PLAYER_O('o', list -> Collections.max(list), WIN);

	Function<List<Board>, Board> moveChooser;
	char representation;
	PlayState resultForOIfIWin;
	
	Player(char representation, Function<List<Board>, Board> moveChooser, PlayState resultForO) {
		this.moveChooser = moveChooser;
		this.representation = representation;
		resultForOIfIWin = resultForO;
	}
	
	public Board getResponse(Board currentBoard) {
		List<Board> possibleMoves = findPossibleResponses(currentBoard);
		return moveChooser.apply(possibleMoves);
	}
	
	
	//Gets the opposite player. 
	public Player getOpposingPlayer() {
		return Player.values()[(ordinal() +1)%2];
	}
	
	
	private List<Board> findPossibleResponses(Board b) {
		List<Board> possibleResponses = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (b.positions[i][j] == Board.BLANK_SPACE) {
					char[][] newPositions = b.copyOfMovesOnBoard();
					newPositions[i][j] = representation;
					possibleResponses.add(new Board(newPositions, getOpposingPlayer()));
				}
			}
		}
		return possibleResponses;
	}
	
	public static Player getPlayerFor(char c) {
		for (Player p : Player.values()) {
			if (c == p.representation) return p;
		}
		throw new IllegalArgumentException("does not represent a player");
	}
	
	public PlayState getResultForO() {
		return resultForOIfIWin;
	}

}
