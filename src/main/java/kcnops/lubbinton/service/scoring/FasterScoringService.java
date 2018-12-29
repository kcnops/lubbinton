package kcnops.lubbinton.service.scoring;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FasterScoringService extends ScoringService {

	private static final int PENALTY_REST = 99;
	private static final int PENALTY_WITH = 4;
	private static final int PENALTY_AGAINST = 3;

	@Override
	public int score(@Nonnull final Setup setup, @Nonnull final List<Player> players) {
		int penalty = 0;
		final int minTimesRest = setup.getRounds().size() / players.size();
		for (final Player player : players) {
			final List<Player> otherPlayers = new ArrayList<>(players);
			otherPlayers.remove(player);
			for (final Player otherPlayer : otherPlayers) {
				int playerWithOtherPlayerCount = 0;
				int playerAgainstOtherPlayerCount = 0;
				for (final Match match : setup.getRounds().stream().map(Round::getMatches).flatMap(Collection::stream).collect(Collectors.toList())) {
					if (match.doesPlayWith(player, otherPlayer)) {
						playerWithOtherPlayerCount++;
					}
					if (match.doesPlayAgainst(player, otherPlayer)) {
						playerAgainstOtherPlayerCount++;
					}
				}
				// with penalty
				final int playerWithOtherPlayerPenalty = playerWithOtherPlayerCount < 2 ? 0 : (int) Math.pow(PENALTY_WITH, playerWithOtherPlayerCount - 1d);
				penalty += playerWithOtherPlayerPenalty;
				// against penalty
				final int playerAgainstOtherPlayerPenalty = playerAgainstOtherPlayerCount < 2 ? 0 : (int) Math.pow(PENALTY_AGAINST, playerAgainstOtherPlayerCount - 1d);
				penalty += playerAgainstOtherPlayerPenalty;
			}
			// rest penalty
			final int timesRest = (int) setup.getRounds().stream().map(Round::getRest).filter(rest -> rest.contains(player)).count();
			final int moreRest = timesRest - minTimesRest;
			penalty += moreRest == 0 ? 0 : Math.pow(PENALTY_REST, moreRest);
		}
		return penalty;
	}

}