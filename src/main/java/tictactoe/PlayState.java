package tictactoe;

import java.util.function.Function;

public enum PlayState {

	WIN(b -> 1),
	DRAW(b -> 0),
	LOSS(b -> -1),
	IN_PLAY(b -> b.getPlayersResponse().determineFinalOutcome().getFinalOutcome(b));
	
	Function<Board, Integer> finalOutcomeFinder;
	
	PlayState(Function<Board, Integer> outcomeFinder) {
		this.finalOutcomeFinder = outcomeFinder;
	}
	
	public int getFinalOutcome(Board b) {
		return finalOutcomeFinder.apply(b);
	}
}
