package kcnops.lubbinton.model;

import javax.annotation.Nonnull;

public class Match {

	private Side sideOne;
	private Side sideTwo;

	public Match (
			@Nonnull final Side sideOne,
			@Nonnull final Side sideTwo) {
		this.sideOne = sideOne;
		this.sideTwo = sideTwo;
	}

	public boolean doesPlayWith(@Nonnull final Player playerOne, @Nonnull final Player playerTwo) {
		return sideOne.doesPlayWith(playerOne, playerTwo) || sideTwo.doesPlayWith(playerOne, playerTwo);
	}

	public boolean doesPlayAgainst(@Nonnull final Player playerOne, @Nonnull final Player playerTwo) {
		return (sideOne.containsPlayer(playerOne) && sideTwo.containsPlayer(playerTwo))
				|| (sideOne.containsPlayer(playerTwo) && sideTwo.containsPlayer(playerOne));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Match match = (Match) o;

		return (sideOne.equals(match.sideOne) && sideTwo.equals(match.sideTwo)) || (sideOne.equals(match.sideTwo) && sideTwo.equals(match.sideOne));
	}

	@Override
	public int hashCode() {
		return sideOne.hashCode() + sideTwo.hashCode();
	}

	@Override
	public String toString() {
		return sideOne + " vs " + sideTwo;
	}
}
