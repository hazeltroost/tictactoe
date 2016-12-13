package tictactoe;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public enum Player {
	PLAYER_X('x', list -> Collections.min(list)),
	PLAYER_O('o', list -> Collections.max(list));

	Function<List<Board>, Board> moveChooser;
	char representation;
	
	Player(char representation, Function<List<Board>, Board> moveChooser) {
		this.moveChooser = moveChooser;
		this.representation = representation;
	}
	
	public Board bestPlay(List<Board> possibleMoves) {
		return moveChooser.apply(possibleMoves);
	}
	
	
	//this method is ugly I know
	public Player getOpposingPlayer() {
		return Player.values()[(ordinal() +1)%2];
	}
	
	

}
