package tictactoe;

import java.util.function.Function;

public enum PlayState {

	WIN(b -> 1),
	DRAW(b -> 0),
	LOSS(b -> -1),
	IN_PLAY(b -> b.getPlayersResponse().determineFinalOutcome().toInt()); //final outcome will never return in play so no board applied
	
	Function<Board, Integer> finalOutcomeFinder;
	
	PlayState(Function<Board, Integer> outcomeFinder) {
		this.finalOutcomeFinder = outcomeFinder;
	}
	
	public int getFinalOutcome(Board b) {
		return finalOutcomeFinder.apply(b);
	}
	
	private int toInt() {
		if (this == IN_PLAY) throw new UnsupportedOperationException("this method is only supported on finished boards.");
		return finalOutcomeFinder.apply(null);
	}
}
