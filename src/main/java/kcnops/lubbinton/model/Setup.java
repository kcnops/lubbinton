package kcnops.lubbinton.model;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Setup {

	private List<Round> rounds;

	public Setup(@Nonnull final List<Round> rounds) {
		this.rounds = new ArrayList<>(rounds);
	}

	public List<Round> getRounds() {
		return new ArrayList<>(rounds);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		return compareRounds(this.getRounds(), ((Setup) o).getRounds());
	}

	private boolean compareRounds(@Nonnull final List<Round> rounds, @Nonnull final List<Round> otherRounds) {
		if (rounds.size() != otherRounds.size()) {
			return false;
		}
		if (rounds.isEmpty()) {
			return true;
		}
		final Round round = rounds.get(0);
		for (final Round otherRound : otherRounds) {
			if (round.equals(otherRound)) {
				final List<Round> newRounds = new ArrayList<>(rounds);
				newRounds.remove(round);
				final List<Round> newOtherRounds = new ArrayList<>(otherRounds);
				newOtherRounds.remove(otherRound);
				return compareRounds(newRounds, newOtherRounds);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return rounds.stream().mapToInt(Round::hashCode).sum();
	}

	@Override
	public String toString() {
		StringBuilder result =  new StringBuilder("Setup{" + "rounds=");
		for(Round round : rounds) {
			result.append(System.lineSeparator()).append(round);
		}
		result.append(System.lineSeparator()).append('}');
		return result.toString();
	}
}
