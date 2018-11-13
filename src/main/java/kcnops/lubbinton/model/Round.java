package kcnops.lubbinton.model;

import javax.annotation.Nonnull;
import java.util.List;

public class Round {

	private List<Match> matches;
	private List<Player> rest;

	public Round(
			@Nonnull final List<Match> matches,
			@Nonnull final List<Player> rest) {
		this.matches = matches;
		this.rest = rest;
	}

	@Nonnull
	public List<Match> getMatches() {
		return matches;
	}

	@Nonnull
	public List<Player> getRest() {
		return rest;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Round round = (Round) o;

		return matches.containsAll(round.matches) && round.matches.containsAll(matches)
				&& rest.containsAll(round.rest) && round.rest.containsAll(rest);
	}

	@Override
	public int hashCode() {
		final int matchHash = matches.stream().mapToInt(Match::hashCode).sum();
		final int restHash = rest.stream().mapToInt(Player::hashCode).sum();
		return matchHash + 3197 * restHash;
	}

	@Override
	public String toString() {
		return "Round{" +
				"matches=" + matches +
				", rest=" + rest +
				'}';
	}
}
