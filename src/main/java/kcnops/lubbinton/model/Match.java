package kcnops.lubbinton.model;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match {

	private Side sideOne;
	private Side sideTwo;

	public Match (
			@Nonnull final Side sideOne,
			@Nonnull final Side sideTwo) {
		this.sideOne = sideOne;
		this.sideTwo = sideTwo;
	}

	@Nonnull
	public Side getSideOne() {
		return sideOne;
	}

	@Nonnull
	public Side getSideTwo() {
		return sideTwo;
	}

	@Nonnull
	public Set<Player> getPlayers() {
		return Stream.of(sideOne.getPlayers(), sideTwo.getPlayers()).flatMap(Collection::stream).collect(Collectors.toSet());
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
