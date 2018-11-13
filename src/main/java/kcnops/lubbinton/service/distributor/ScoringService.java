package kcnops.lubbinton.service.distributor;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScoringService {

	private static final int PENALTY_REST = 99;
	private static final int PENALTY_WITH = 4;
	private static final int PENALTY_AGAINST = 3;

	public int score(@Nonnull final Setup setup, @Nonnull final List<Player> players) {
		return restPenalty(setup, players)
				+ withPenalty(setup, players)
				+ againstPenalty(setup, players)
				;
	}

	private int restPenalty(@Nonnull final Setup setup, @Nonnull final List<Player> players) {
		final int minTimesRest = setup.getRounds().size() / players.size();
		int restPenalty = 0;
		for (final Player player : players) {
			final int timesRest = (int) setup.getRounds().stream().map(Round::getRest).filter(rest -> rest.contains(player)).count();
			final int moreRest = timesRest - minTimesRest;
			restPenalty += moreRest == 0 ? 0 : Math.pow(PENALTY_REST, moreRest);
		}
		return restPenalty;
	}

	private int withPenalty(@Nonnull final Setup setup, @Nonnull final List<Player> players) {
		int withPenalty = 0;
		for (final Player player : players) {
			int playerWithPenalty = 0;
			final List<Player> otherPlayers = new ArrayList<>(players);
			otherPlayers.remove(player);
			for (final Player otherPlayer : otherPlayers) {
				final long playerWithOtherPlayerCount = setup.getRounds().stream()
						.map(Round::getMatches)
						.flatMap(Collection::stream)
						.filter(match -> match.doesPlayWith(player, otherPlayer))
						.count();
				final int playerWithOtherPlayerPenalty =
						playerWithOtherPlayerCount < 2 ? 0 : (int) Math.pow(PENALTY_WITH, playerWithOtherPlayerCount - 1d);
				playerWithPenalty += playerWithOtherPlayerPenalty;
			}
			withPenalty += playerWithPenalty;
		}
		return withPenalty;
	}

	private int againstPenalty(@Nonnull final Setup setup, @Nonnull final List<Player> players) {
		int againstPenalty = 0;
		for (final Player player : players) {
			int playerAgainstPenalty = 0;
			final List<Player> otherPlayers = new ArrayList<>(players);
			otherPlayers.remove(player);
			for (final Player otherPlayer : otherPlayers) {
				final long playerAgainstOtherPlayerCount = setup.getRounds().stream()
						.map(Round::getMatches)
						.flatMap(Collection::stream)
						.filter(match -> match.doesPlayAgainst(player, otherPlayer)).count();
				final int playerAgainstOtherPlayerPenalty = playerAgainstOtherPlayerCount < 2 ? 0 : (int) Math.pow(PENALTY_AGAINST, playerAgainstOtherPlayerCount - 1d);
				playerAgainstPenalty += playerAgainstOtherPlayerPenalty;
			}
			againstPenalty += playerAgainstPenalty;
		}
		return againstPenalty;
	}

}