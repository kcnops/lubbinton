package kcnops.lubbinton.service.incrementalDistributor;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IncrementalDistributor implements IIncrementalDistributor {

	@Nonnull
	@Override
	public Round getNextRound(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds) {
		final List<List<Player>> permutePlayers = permutePlayers(players);

		Set<Round> rounds = permutePlayers.stream().map(this::buildRound).collect(Collectors.toSet());

		final Setup bestSetup = getBestSetup(players, previousRounds, rounds);

		final List<Round> bestSetupRounds = bestSetup.getRounds();
		return bestSetupRounds.get(bestSetupRounds.size()-1);
	}

}
